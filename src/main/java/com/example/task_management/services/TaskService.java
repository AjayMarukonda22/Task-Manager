package com.example.task_management.services;

import com.example.task_management.dtos.GenericTaskDto;
import com.example.task_management.exceptions.NotFoundException;
import com.example.task_management.models.Task;
import com.example.task_management.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements TaskManager {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponseEntity<GenericTaskDto> getTaskById(Long taskId) throws NotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if(optionalTask.isEmpty())
            throw new NotFoundException("The task with " + taskId + " is not found");

         GenericTaskDto genericTaskDto = convertTaskToGenericTaskDto(optionalTask.get());
         return new ResponseEntity<>(genericTaskDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GenericTaskDto>> getAllTasks() {
           List<Task> taskList = taskRepository.findAll();
           List<GenericTaskDto> genericTaskDtoList = new ArrayList<>();
           for(Task task : taskList) {
               genericTaskDtoList.add(convertTaskToGenericTaskDto(task));
           }
        return new ResponseEntity<>(genericTaskDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<GenericTaskDto>> getAllTaskAssignedToUser(Long userId) throws NotFoundException{
        Optional<List<Task>> optionalTaskList = taskRepository.findAllByAssignedUser(userId);
        if(optionalTaskList.isEmpty())
            throw new NotFoundException("The user with " + userId + " has not assigned any tasks" );

        List<GenericTaskDto> genericTaskDtoList = new ArrayList<>();
        for(Task task : optionalTaskList.get()) {
            genericTaskDtoList.add(convertTaskToGenericTaskDto(task));
        }
        return new ResponseEntity<>(genericTaskDtoList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createTask(GenericTaskDto genericTaskDto) {
        Task task = convertGenericTaskDtoToTask(genericTaskDto);
        taskRepository.save(task);
        return new ResponseEntity<>("The task has been created successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateTask(Long taskId, GenericTaskDto genericTaskDto) throws NotFoundException {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty())
            throw new NotFoundException("The task with " + taskId + " is not found");
        Task task = optionalTask.get();
        task.setTitle(genericTaskDto.getTitle());
        task.setDescription(genericTaskDto.getDescription());
        task.setAssignedUser(task.getAssignedUser());
        task.setPriority(genericTaskDto.getPriority());
        task.setStatus(genericTaskDto.getStatus());
        task.setCreatedAt(genericTaskDto.getCreatedAt());
        task.setDueDate(genericTaskDto.getDueDate());
        task.setUpdatedAt(genericTaskDto.getUpdatedAt());
        taskRepository.save(task);
        return new ResponseEntity<>("The task is updated successfuly", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
        return new ResponseEntity<>("The task have been deleted successfully", HttpStatus.OK);
    }
    public GenericTaskDto convertTaskToGenericTaskDto(Task task) {
        GenericTaskDto genericTaskDto = new GenericTaskDto();
        genericTaskDto.setTitle(task.getTitle());
        genericTaskDto.setDescription(task.getDescription());
        genericTaskDto.setPriority(task.getPriority());
        genericTaskDto.setStatus(task.getStatus());
        genericTaskDto.setCreatedAt(task.getCreatedAt());
        genericTaskDto.setAssignedUser(task.getAssignedUser());
        genericTaskDto.setDueDate(task.getDueDate());
        genericTaskDto.setUpdatedAt(task.getUpdatedAt());
        return genericTaskDto;
    }
    public Task convertGenericTaskDtoToTask(GenericTaskDto genericTaskDto) {
        Task task = new Task();
        task.setTitle(genericTaskDto.getTitle());
        task.setDescription(genericTaskDto.getDescription());
        task.setAssignedUser(task.getAssignedUser());
        task.setPriority(genericTaskDto.getPriority());
        task.setStatus(genericTaskDto.getStatus());
        task.setCreatedAt(genericTaskDto.getCreatedAt());
        task.setDueDate(genericTaskDto.getDueDate());
        task.setUpdatedAt(genericTaskDto.getUpdatedAt());
        return task;
    }
}


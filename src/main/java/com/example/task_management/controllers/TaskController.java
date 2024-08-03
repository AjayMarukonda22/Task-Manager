package com.example.task_management.controllers;

import com.example.task_management.dtos.GenericTaskDto;
import com.example.task_management.exceptions.NotFoundException;
import com.example.task_management.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericTaskDto> getTaskById(@PathVariable("id") Long taskId) throws NotFoundException {
        return taskService.getTaskById(taskId);
    }
    @GetMapping
    public ResponseEntity<List<GenericTaskDto>> getAllTasks() {
        return taskService.getAllTasks();
    }
    @GetMapping("/assignedToUser/{id}")
    public ResponseEntity<List<GenericTaskDto>> getAllTaskAssignedToUser(@PathVariable("id") Long userId) throws NotFoundException {
        return taskService.getAllTaskAssignedToUser(userId);
    }
    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody GenericTaskDto genericTaskDto) {
        return taskService.createTask(genericTaskDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateTask(@PathVariable("id") Long taskId, @RequestBody GenericTaskDto genericTaskDto) throws NotFoundException {
        return taskService.updateTask(taskId, genericTaskDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable("id") Long taskId) {
        return taskService.deleteTask(taskId);
    }

}

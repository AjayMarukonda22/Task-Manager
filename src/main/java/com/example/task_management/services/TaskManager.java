package com.example.task_management.services;

import com.example.task_management.dtos.GenericTaskDto;
import com.example.task_management.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TaskManager {
    ResponseEntity<GenericTaskDto> getTaskById(Long taskId) throws NotFoundException;
    ResponseEntity<List<GenericTaskDto>> getAllTasks();
    ResponseEntity<List<GenericTaskDto>> getAllTaskAssignedToUser(Long userId) throws NotFoundException;
    ResponseEntity<String> createTask(GenericTaskDto genericTaskDto);
    ResponseEntity<String> updateTask(Long taskId, GenericTaskDto genericTaskDto) throws NotFoundException;
    ResponseEntity<String> deleteTask(Long taskId);
}

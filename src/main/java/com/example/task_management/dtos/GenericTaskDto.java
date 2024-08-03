package com.example.task_management.dtos;

import com.example.task_management.models.TaskPriority;
import com.example.task_management.models.TaskStatus;
import com.example.task_management.models.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GenericTaskDto {
    private String title;
    private String description;

    private TaskStatus status;

    private TaskPriority priority;

    private User assignedUser;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate dueDate;
}

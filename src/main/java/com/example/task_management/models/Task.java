package com.example.task_management.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "Tasks")
public class Task extends BaseModel{
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Enumerated(EnumType.ORDINAL)
    private TaskPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    private User assignedUser;

    private LocalDate createdAt;
    private LocalDate updatedAt;
    private LocalDate dueDate;

}

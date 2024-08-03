package com.example.task_management.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@Entity
@Table(name = "Sessions")
public class Session extends BaseModel{
    private String token;
    private LocalDate expiryDate;
    @ManyToOne
    private User user;
    private SessionStatus status;
}

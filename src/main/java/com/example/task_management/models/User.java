package com.example.task_management.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity

@Table(name = "Users")
public class User extends BaseModel {
    private String userName;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
@JoinTable(name = "user_roles",
joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roleSet = new HashSet<>();
}

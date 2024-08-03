package com.example.task_management.dtos;

import com.example.task_management.models.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
@Getter
@Setter
public class SetUserRolesRequestDto {
    private List<Long> roleIds;
}

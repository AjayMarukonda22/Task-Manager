package com.example.task_management.dtos;

import com.example.task_management.models.Role;
import com.example.task_management.models.User;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDto {
    private String status;
    private String userName;
    private Set<Role> roleSet;
    private LocalDate createdAt;

    public static UserDto from(User user) {
          UserDto userDto = new UserDto();
          userDto.setUserName(user.getUserName());
          userDto.setStatus("Success");
          userDto.setRoleSet(user.getRoleSet());
        userDto.setCreatedAt(Date.valueOf(LocalDate.now()).toLocalDate());
          return userDto;
    }

}

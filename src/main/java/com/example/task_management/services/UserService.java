package com.example.task_management.services;

import com.example.task_management.dtos.UserDto;
import com.example.task_management.exceptions.NotFoundException;
import com.example.task_management.models.Role;
import com.example.task_management.models.User;
import com.example.task_management.repositories.RoleRepository;
import com.example.task_management.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public UserDto getUserDetails(Long userId) throws NotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw new NotFoundException("The user with " + userId + " is not Found");
        User user1 = user.get();
        return UserDto.from(user1);
    }
    public UserDto setUserRoles(Long userId, List<Long> roleIds) throws NotFoundException {
        Optional<User> user = userRepository.findById(userId);
        List<Role> roles = roleRepository.findAllById(roleIds);
        if(user.isEmpty()){
            throw new NotFoundException("The user with " + userId + " is not found");
        }
        User user1 = user.get();
        user1.getRoleSet().clear();
        user1.getRoleSet().addAll(roles);
        User savedUser = userRepository.save(user1);
        return UserDto.from(savedUser);
    }
}

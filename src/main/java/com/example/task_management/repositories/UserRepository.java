package com.example.task_management.repositories;

import com.example.task_management.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   @Query(value = "select u from User u where u.userName = :userName", nativeQuery = false)
    public Optional<User> findUserByUserName(String userName);
}

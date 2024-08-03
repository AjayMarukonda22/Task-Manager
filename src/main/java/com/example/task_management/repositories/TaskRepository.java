package com.example.task_management.repositories;

import com.example.task_management.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value = "select * from tasks  where tasks.assigned_user_id = :id", nativeQuery = true)
    public Optional<List<Task>> findAllByAssignedUser(Long id);
}

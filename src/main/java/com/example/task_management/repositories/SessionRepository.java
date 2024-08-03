package com.example.task_management.repositories;

import com.example.task_management.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("select s from Session s where s.token = :token and s.user.id = :userId")
    Optional<Session> findSessionByTokenAndUser(@Param("token") String token, @Param("userId") Long userId);
}

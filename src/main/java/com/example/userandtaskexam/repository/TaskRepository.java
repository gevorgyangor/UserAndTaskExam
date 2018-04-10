package com.example.userandtaskexam.repository;

import com.example.userandtaskexam.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByUserId(int id);

    List<Task> findAllByStartData(Date date);
}

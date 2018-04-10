package com.example.userandtaskexam.rest;

import com.example.userandtaskexam.model.Task;
import com.example.userandtaskexam.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping(value = "/tasks")
public class TaskRest {

    SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping
    public List<Task> tasks() {
        return taskRepository.findAll();
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int id) {
        return taskRepository.findOne(id);
    }

    @GetMapping("/{date}")
    public List<Task> startDate(@PathVariable String date) {

        List<Task> tasks = null;
        try {
            tasks = taskRepository.findAllByStartData(myFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (tasks == null) {
            ResponseEntity.badRequest().body("Tasks with " + date + " does not exist");
        }
        return tasks;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody Task task) {
        if (taskRepository.exists(task.getId())) {
            try {
                task.setStartData(String.valueOf(myFormat.parse(task.getStartData())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            taskRepository.save(task);
            return ResponseEntity.ok("Created Task");
        }
        return ResponseEntity.badRequest().body("Task with " + task.getId() + " already exist");
    }

    @GetMapping("/{userId}")
    private List<Task> getTasksByUserId(@PathVariable int userId) {
        List<Task> tasks = taskRepository.findAllByUserId(userId);
        if (tasks == null) {
            ResponseEntity.badRequest().body("Tasks with " + userId + " does not exist");
        }
        return tasks;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        try {
            taskRepository.delete(id);
            return ResponseEntity.ok("Deleted Task");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Task with " + id + " does not exist");
        }
    }
}

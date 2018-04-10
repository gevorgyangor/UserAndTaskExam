package com.example.userandtaskexam.rest;

import com.example.userandtaskexam.dto.UserDto;
import com.example.userandtaskexam.model.User;
import com.example.userandtaskexam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserTask {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> users() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<UserDto> getUser(@PathVariable int id) {
        List<User> all = userRepository.findAll();
        List<UserDto> userDtos = new LinkedList<>();
        all.forEach(e -> userDtos.add(new UserDto(e.getId(), e.getName(), e.getSurname(), e.getAge(), e.getEmail())));
        return userDtos;
    }

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user) {
        if (userRepository.findOneByEmail(user.getEmail()) == null) {
            userRepository.save(user);
            return ResponseEntity.ok("Created User");
        }
        return ResponseEntity.badRequest().body("User with " + user.getEmail() + " already exist");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        try {
            userRepository.delete(id);
            return ResponseEntity.ok("Deleted");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User with " + id + " does not exist");
        }
    }

    @PutMapping
    public ResponseEntity updateUser(@RequestBody User user) {
        if (userRepository.exists(user.getId())) {
            userRepository.save(user);
            return ResponseEntity.ok("Updated");
        }
        return ResponseEntity.badRequest().body("User with " + user.getId() + " does not exist");
    }
}

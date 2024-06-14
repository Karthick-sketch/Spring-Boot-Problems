package com.example.banktransactionsystem.controller;

import com.example.banktransactionsystem.entity.User;
import com.example.banktransactionsystem.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UsersController {
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<String> createNewUser(@RequestBody User user) {
        userService.createNewUser(user);
        return new ResponseEntity<>("User created", HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable long id, @RequestBody Map<String, Object> updatedUser) {
        return new ResponseEntity<>(userService.updateUserByFields(id, updatedUser), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

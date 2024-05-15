package com.example.bookstoresystem.service;

import com.example.bookstoresystem.entity.User;
import com.example.bookstoresystem.exception.EntityNotFoundException;
import com.example.bookstoresystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private UserRepository userRepository;

    public User getUserById(int userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("There is no user with the ID of " + userId);
        }
        return user.get();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(int userId, User updatedUser) {
        User user = getUserById(userId);
        updatedUser.setUserId(user.getUserId());
        return userRepository.save(updatedUser);
    }

    public void deleteUser(int userId) {
        userRepository.delete(getUserById(userId));
    }
}

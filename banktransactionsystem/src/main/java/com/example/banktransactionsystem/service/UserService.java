package com.example.banktransactionsystem.service;

import com.example.banktransactionsystem.entity.User;
import com.example.banktransactionsystem.exception.BadRequestException;
import com.example.banktransactionsystem.exception.EntityNotFoundException;
import com.example.banktransactionsystem.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Cacheable(value = "user", key = "#id")
    public User getUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("There is no user with the ID of '" + id + "'");
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new EntityNotFoundException("The user with the username '" + username + "' does not exist in our records");
        }
    }

    public void createNewUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("The username '" + user.getUsername() + "' is already taken");
        }
    }

    @CacheEvict(value = "user", key = "#id")
    public User updateUserByFields(long id, Map<String, Object> fields) {
        User user = getUserById(id);
        try {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(User.class, key);
                if (field != null) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, user, value);
                }
            });
            return userRepository.save(user);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
    }

    @CacheEvict(value = "user", key = "#id")
    public void deleteUserById(long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("There is no user with the ID of '" + id + "'");
        }
    }
}

package com.example.bookstoresystem;

import com.example.bookstoresystem.entity.User;
import com.example.bookstoresystem.exception.EntityNotFoundException;
import com.example.bookstoresystem.repository.UserRepository;
import com.example.bookstoresystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetUserById() {
        int userId = 1;
        User mockUser = MockObjects.getMockUser();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        User validUser = userService.getUserById(userId);
        Executable invalidId = () -> userService.getUserById(2);

        Assertions.assertEquals(mockUser, validUser);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
    }

    @Test
    public void testUpdateUser() {
        int userId = 1;
        User mockUser = MockObjects.getMockUser();
        User updatedMockUser = MockObjects.getUpdatedMockUser();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        Mockito.when(userRepository.save(updatedMockUser)).thenReturn(updatedMockUser);

        Executable invalidId = () -> userService.getUserById(2);
        User validUser = userService.updateUser(userId, updatedMockUser);

        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
        Assertions.assertEquals(updatedMockUser, validUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.any(User.class));
    }

    @Test
    public void testDeleteUser() {
        int userId = 1;
        User mockUser = MockObjects.getMockUser();
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        userService.deleteUser(userId);
        Mockito.verify(userRepository, Mockito.times(1)).delete(mockUser);
    }
}

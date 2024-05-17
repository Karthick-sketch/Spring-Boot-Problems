package com.example.assetmanagementsystem;

import com.example.assetmanagementsystem.entity.User;
import com.example.assetmanagementsystem.exception.EntityNotFoundException;
import com.example.assetmanagementsystem.repository.UserRepository;
import com.example.assetmanagementsystem.service.UserService;
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

        User validUser = userService.updateUser(userId, updatedMockUser);
        Executable invalidId = () -> userService.getUserById(2);

        Assertions.assertEquals(updatedMockUser, validUser);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
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

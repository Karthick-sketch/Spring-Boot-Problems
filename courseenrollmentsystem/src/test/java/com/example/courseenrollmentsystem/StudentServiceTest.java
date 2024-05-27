package com.example.courseenrollmentsystem;

import com.example.courseenrollmentsystem.entity.Student;
import com.example.courseenrollmentsystem.exception.EntityNotFoundException;
import com.example.courseenrollmentsystem.repository.StudentRepository;
import com.example.courseenrollmentsystem.service.StudentService;
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
public class StudentServiceTest {
    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    public void testGetStudentById() {
        int studentId = 1;
        Student mockStudent = MockObjects.getMockStudent();
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));

        Student validStudent = studentService.getStudentById(studentId);
        Executable invalidId = () -> studentService.getStudentById(2);

        Assertions.assertEquals(mockStudent, validStudent);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
    }

    @Test
    public void testUpdateStudent() {
        int studentId = 1;
        Student mockStudent = MockObjects.getMockStudent();
        Student updatedMockStudent = MockObjects.getUpdatedMockStudent();
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
        Mockito.when(studentRepository.save(updatedMockStudent)).thenReturn(updatedMockStudent);

        Executable invalidId = () -> studentService.getStudentById(2);
        Student validStudent = studentService.updateStudent(studentId, updatedMockStudent);

        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
        Assertions.assertEquals(updatedMockStudent, validStudent);
        Mockito.verify(studentRepository, Mockito.times(1)).save(Mockito.any(Student.class));
    }

    @Test
    public void testDeleteStudent() {
        int studentId = 1;
        Student mockStudent = MockObjects.getMockStudent();
        Mockito.when(studentRepository.findById(studentId)).thenReturn(Optional.of(mockStudent));
        studentService.deleteStudent(studentId);
        Mockito.verify(studentRepository, Mockito.times(1)).delete(mockStudent);
    }
}

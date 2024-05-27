package com.example.courseenrollmentsystem;

import com.example.courseenrollmentsystem.entity.Course;
import com.example.courseenrollmentsystem.exception.EntityNotFoundException;
import com.example.courseenrollmentsystem.repository.CourseRepository;
import com.example.courseenrollmentsystem.service.CourseService;
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
public class CourseServiceTest {
    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void testGetCourseById() {
        int courseId = 1;
        Course mockCourse = MockObjects.getMockCourse();
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));

        Course validCourse = courseService.getCourseById(courseId);
        Executable invalidId = () -> courseService.getCourseById(2);

        Assertions.assertEquals(mockCourse, validCourse);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
    }

    @Test
    public void testUpdateCourse() {
        int courseId = 1;
        Course mockCourse = MockObjects.getMockCourse();
        Course updatedMockCourse = MockObjects.getUpdatedMockCourse();
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        Mockito.when(courseRepository.save(updatedMockCourse)).thenReturn(updatedMockCourse);

        Executable invalidId = () -> courseService.getCourseById(2);
        Course validCourse = courseService.updateCourse(courseId, updatedMockCourse);

        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
        Assertions.assertEquals(updatedMockCourse, validCourse);
        Mockito.verify(courseRepository, Mockito.times(1)).save(Mockito.any(Course.class));
    }

    @Test
    public void testDeleteCourse() {
        int courseId = 1;
        Course mockCourse = MockObjects.getMockCourse();
        Mockito.when(courseRepository.findById(courseId)).thenReturn(Optional.of(mockCourse));
        courseService.deleteCourse(courseId);
        Mockito.verify(courseRepository, Mockito.times(1)).delete(mockCourse);
    }
}

package com.example.courseenrollmentsystem;

import com.example.courseenrollmentsystem.dto.EnrolledCourseDTO;
import com.example.courseenrollmentsystem.entity.Course;
import com.example.courseenrollmentsystem.entity.Enroll;
import com.example.courseenrollmentsystem.entity.Student;
import com.example.courseenrollmentsystem.exception.BadRequestException;
import com.example.courseenrollmentsystem.repository.EnrollRepository;
import com.example.courseenrollmentsystem.service.CourseService;
import com.example.courseenrollmentsystem.service.EnrollService;
import com.example.courseenrollmentsystem.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
public class EnrollServiceTest {
    @Mock
    private EnrollRepository enrollRepository;

    @Mock
    private StudentService studentService;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private EnrollService enrollService;

    @Test
    public void testGetLogCount() {
        int courseId = 1, enrollCount = 1;
        Course mockCourse = MockObjects.getMockCourse();
        EnrolledCourseDTO mockEnrolledCourseDTO = MockObjects.getMockEnrolledCourseDTO(enrollCount);

        Mockito.when(enrollRepository.findCourseEnrollmentCount(courseId)).thenReturn(enrollCount);
        Mockito.when(courseService.getCourseById(courseId)).thenReturn(mockCourse);

        EnrolledCourseDTO validEnrolledCourseDTO = enrollService.getStudentsEnrolledCourseCount(courseId);
        Assertions.assertEquals(mockEnrolledCourseDTO.getCount(), validEnrolledCourseDTO.getCount());
    }

    @Test
    public void testLogStudent() {
        int studentId = 1, courseId = 1;
        Student mockStudent = MockObjects.getMockStudent();
        Course mockCourse = MockObjects.getMockCourse();
        Enroll mockEnroll = MockObjects.getMockEnroll();

        Mockito.when(studentService.getStudentById(studentId)).thenReturn(mockStudent);
        Mockito.when(courseService.getCourseById(courseId)).thenReturn(mockCourse);
        Mockito.when(enrollRepository.save(Mockito.any(Enroll.class))).thenReturn(mockEnroll);
        Enroll validEnroll = enrollService.enrollCourse(studentId, courseId);

        Executable duplicateLog = () -> enrollService.enrollCourse(studentId, courseId);
        Mockito.when(enrollRepository.save(Mockito.any(Enroll.class))).thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(BadRequestException.class, duplicateLog);
        Assertions.assertEquals(mockEnroll, validEnroll);
        Mockito.verify(enrollRepository, Mockito.times(2)).save(Mockito.any(Enroll.class));
    }
}

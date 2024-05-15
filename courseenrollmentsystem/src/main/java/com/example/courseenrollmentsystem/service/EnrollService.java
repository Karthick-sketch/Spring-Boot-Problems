package com.example.courseenrollmentsystem.service;

import com.example.courseenrollmentsystem.dto.EnrolledCourseDTO;
import com.example.courseenrollmentsystem.entity.Course;
import com.example.courseenrollmentsystem.entity.Enroll;
import com.example.courseenrollmentsystem.entity.Student;
import com.example.courseenrollmentsystem.exception.BadRequestException;
import com.example.courseenrollmentsystem.repository.EnrollRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@AllArgsConstructor
@Service
public class EnrollService {
    private EnrollRepository enrollRepository;
    private StudentService studentService;
    private CourseService courseService;

    public EnrolledCourseDTO getStudentsEnrolledCourseCount(int courseId) {
        Course course = courseService.getCourseById(courseId);
        return new EnrolledCourseDTO(enrollRepository.findCourseEnrollmentCount(course.getCourseId()));
    }

    public Enroll enrollCourse(int studentId, int courseId) {
        Student student = studentService.getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);
        try {
            return enrollRepository.save(new Enroll(course.getCourseId(), student.getStudentId()));
        } catch (DataIntegrityViolationException exception) {
            throw new BadRequestException("The student '" + student.getName() + "' is already enrolled!");
        }
    }
}

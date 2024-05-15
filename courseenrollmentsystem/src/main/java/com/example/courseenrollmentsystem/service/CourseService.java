package com.example.courseenrollmentsystem.service;

import com.example.courseenrollmentsystem.entity.Course;
import com.example.courseenrollmentsystem.entity.Student;
import com.example.courseenrollmentsystem.exception.BadRequestException;
import com.example.courseenrollmentsystem.exception.EntityNotFoundException;
import com.example.courseenrollmentsystem.repository.CourseRepository;
import com.example.courseenrollmentsystem.repository.EnrollRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CourseService {
    private CourseRepository courseRepository;

    public Course getCourseById(int courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()) {
            throw new EntityNotFoundException("There is no course with the ID of " + courseId);
        }
        return course.get();
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(int courseId, Course updatedCourse) {
        Course course = getCourseById(courseId);
        updatedCourse.setCourseId(course.getCourseId());
        return courseRepository.save(updatedCourse);
    }

    public void deleteCourse(int courseId) {
        courseRepository.delete(getCourseById(courseId));
    }
}

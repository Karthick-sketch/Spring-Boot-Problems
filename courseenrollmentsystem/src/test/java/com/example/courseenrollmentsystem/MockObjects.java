package com.example.courseenrollmentsystem;

import com.example.courseenrollmentsystem.dto.EnrolledCourseDTO;
import com.example.courseenrollmentsystem.entity.Course;
import com.example.courseenrollmentsystem.entity.Enroll;
import com.example.courseenrollmentsystem.entity.Student;

public class MockObjects {
    static Student getMockStudent() {
        Student student = new Student();
        student.setStudentId(1);
        student.setName("Kratos");
        student.setDepartment("Engineering");
        return student;
    }

    static Student getUpdatedMockStudent() {
        Student student = getMockStudent();
        student.setName("Atreus");
        return student;
    }

    static Course getMockCourse() {
        Course course = new Course();
        course.setCourseId(1);
        course.setCourseName("Spring Boot");
        return course;
    }

    static Course getUpdatedMockCourse() {
        Course course = getMockCourse();
        course.setCourseName("Spring Boot course");
        return course;
    }

    static Enroll getMockEnroll() {
        Enroll enroll = new Enroll(1, 1);
        enroll.setId(1);
        return enroll;
    }

    static EnrolledCourseDTO getMockEnrolledCourseDTO(int count) {
        return new EnrolledCourseDTO(count);
    }
}

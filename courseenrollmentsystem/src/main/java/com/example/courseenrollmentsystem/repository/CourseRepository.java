package com.example.courseenrollmentsystem.repository;

import com.example.courseenrollmentsystem.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}

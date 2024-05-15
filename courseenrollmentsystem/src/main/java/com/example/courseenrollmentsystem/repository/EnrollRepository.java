package com.example.courseenrollmentsystem.repository;

import com.example.courseenrollmentsystem.entity.Enroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EnrollRepository extends JpaRepository<Enroll, Integer> {
    @Query(value = "select count(*) from enrolls where course_id = ?", nativeQuery = true)
    int findCourseEnrollmentCount(int courseId);
}

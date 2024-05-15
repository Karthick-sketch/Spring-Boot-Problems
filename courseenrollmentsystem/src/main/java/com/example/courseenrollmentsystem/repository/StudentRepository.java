package com.example.courseenrollmentsystem.repository;

import com.example.courseenrollmentsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}

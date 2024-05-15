package com.example.attendancemanagementsystem.repository;

import com.example.attendancemanagementsystem.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByDepartment(String department);
}

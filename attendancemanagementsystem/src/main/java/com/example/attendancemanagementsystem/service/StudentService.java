package com.example.attendancemanagementsystem.service;

import com.example.attendancemanagementsystem.entity.Student;
import com.example.attendancemanagementsystem.exception.EntityNotFoundException;
import com.example.attendancemanagementsystem.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentService {
    private StudentRepository studentRepository;

    public Student getStudentById(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (student.isEmpty()) {
            throw new EntityNotFoundException("There is no student with the ID of " + studentId);
        }
        return student.get();
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public List<Student> getAllStudentsByDepartment(String department) {
        return studentRepository.findByDepartment(department);
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(int studentId, Student updatedStudent) {
        Student student = getStudentById(studentId);
        updatedStudent.setStudentId(student.getStudentId());
        return studentRepository.save(updatedStudent);
    }

    public void deleteStudent(int studentId) {
        studentRepository.delete(getStudentById(studentId));
    }
}

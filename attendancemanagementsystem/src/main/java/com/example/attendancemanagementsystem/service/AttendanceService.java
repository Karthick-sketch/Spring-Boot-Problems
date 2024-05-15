package com.example.attendancemanagementsystem.service;

import com.example.attendancemanagementsystem.dto.StudentPresenceDTO;
import com.example.attendancemanagementsystem.entity.Attendance;
import com.example.attendancemanagementsystem.entity.Student;
import com.example.attendancemanagementsystem.exception.BadRequestException;
import com.example.attendancemanagementsystem.repository.AttendanceRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
public class AttendanceService {
    private AttendanceRepository attendanceRepository;
    private StudentService studentService;

    public StudentPresenceDTO getLogCount(LocalDate date) {
        return new StudentPresenceDTO(attendanceRepository.findAttendanceCountByDate(date));
    }

    public Attendance logStudent(int studentId) {
        Student student = studentService.getStudentById(studentId);
        try {
            return attendanceRepository.save(new Attendance(student.getStudentId(), LocalDate.now()));
        } catch (DataIntegrityViolationException exception) {
            throw new BadRequestException("The student '" + student.getName() + "' is already logged!");
        }
    }
}

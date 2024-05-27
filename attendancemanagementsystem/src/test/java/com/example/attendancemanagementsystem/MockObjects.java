package com.example.attendancemanagementsystem;

import com.example.attendancemanagementsystem.dto.StudentPresenceDTO;
import com.example.attendancemanagementsystem.entity.Attendance;
import com.example.attendancemanagementsystem.entity.Student;

import java.time.LocalDate;

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

    static Attendance getMockAttendance() {
        Attendance attendance = new Attendance();
        attendance.setId(1);
        attendance.setStudentId(1);
        attendance.setAttendanceDate(LocalDate.now());
        return attendance;
    }

    static StudentPresenceDTO getStudentPresenceDTO(int count) {
        return new StudentPresenceDTO(count);
    }
}

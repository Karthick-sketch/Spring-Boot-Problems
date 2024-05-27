package com.example.attendancemanagementsystem;

import com.example.attendancemanagementsystem.dto.StudentPresenceDTO;
import com.example.attendancemanagementsystem.entity.Attendance;
import com.example.attendancemanagementsystem.entity.Student;
import com.example.attendancemanagementsystem.exception.BadRequestException;
import com.example.attendancemanagementsystem.repository.AttendanceRepository;
import com.example.attendancemanagementsystem.service.AttendanceService;
import com.example.attendancemanagementsystem.service.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class AttendanceServiceTest {
    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private AttendanceService attendanceService;

    @Test
    public void testGetLogCount() {
        int logCount = 1;
        LocalDate today = LocalDate.now();
        StudentPresenceDTO mockStudentPresenceDTO = MockObjects.getStudentPresenceDTO(logCount);
        Mockito.when(attendanceRepository.findAttendanceCountByDate(today)).thenReturn(logCount);

        StudentPresenceDTO validStudentPresenceDTO = attendanceService.getLogCount(today);
        Assertions.assertEquals(mockStudentPresenceDTO.getCount(), validStudentPresenceDTO.getCount());
    }

    @Test
    public void testLogStudent() {
        int studentId = 1;
        Student mockStudent = MockObjects.getMockStudent();
        Attendance mockAttendance = MockObjects.getMockAttendance();

        Mockito.when(studentService.getStudentById(studentId)).thenReturn(mockStudent);
        Mockito.when(attendanceRepository.save(Mockito.any(Attendance.class))).thenReturn(mockAttendance);
        Attendance validAttendance = attendanceService.logStudent(studentId);

        Executable duplicateLog = () -> attendanceService.logStudent(studentId);
        Mockito.when(attendanceRepository.save(Mockito.any(Attendance.class))).thenThrow(DataIntegrityViolationException.class);

        Assertions.assertThrows(BadRequestException.class, duplicateLog);
        Assertions.assertEquals(mockAttendance, validAttendance);
        Mockito.verify(attendanceRepository, Mockito.times(2)).save(Mockito.any(Attendance.class));
    }
}

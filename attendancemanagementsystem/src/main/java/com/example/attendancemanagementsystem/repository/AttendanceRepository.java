package com.example.attendancemanagementsystem.repository;

import com.example.attendancemanagementsystem.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    @Query(value = "select count(*) from attendances where attendance_date = ?", nativeQuery = true)
    int findAttendanceCountByDate(LocalDate date);
}

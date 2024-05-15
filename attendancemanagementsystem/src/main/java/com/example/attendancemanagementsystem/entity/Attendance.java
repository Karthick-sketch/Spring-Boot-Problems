package com.example.attendancemanagementsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "attendances")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "studentId", "attendanceDate" }))
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int studentId;
    private LocalDate attendanceDate;

    public Attendance(int studentId, LocalDate attendanceDate) {
        this.studentId = studentId;
        this.attendanceDate = attendanceDate;
    }
}

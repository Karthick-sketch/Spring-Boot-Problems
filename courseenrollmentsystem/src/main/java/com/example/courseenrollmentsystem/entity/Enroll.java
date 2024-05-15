package com.example.courseenrollmentsystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "enrolls")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "studentId", "courseId" }))
public class Enroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int courseId;
    private int studentId;

    public Enroll(int courseId, int studentId) {
        this.courseId = courseId;
        this.studentId = studentId;
    }
}

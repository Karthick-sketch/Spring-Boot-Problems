package com.example.bookstoresystem.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "book_logs")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "bookId", "userId" }))
public class BookLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int bookId;
    private int userId;

    public BookLog(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
}

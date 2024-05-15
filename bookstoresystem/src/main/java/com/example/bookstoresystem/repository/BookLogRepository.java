package com.example.bookstoresystem.repository;

import com.example.bookstoresystem.entity.BookLog;
import com.example.bookstoresystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookLogRepository extends JpaRepository<BookLog, Integer> {
    Optional<BookLog> findByBookIdAndUserId(int bookId, int userId);
}

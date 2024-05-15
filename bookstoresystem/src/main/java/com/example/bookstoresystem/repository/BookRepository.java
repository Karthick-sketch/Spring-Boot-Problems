package com.example.bookstoresystem.repository;

import com.example.bookstoresystem.entity.Book;
import com.example.bookstoresystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}

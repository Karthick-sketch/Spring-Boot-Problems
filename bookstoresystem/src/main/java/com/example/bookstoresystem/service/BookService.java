package com.example.bookstoresystem.service;

import com.example.bookstoresystem.entity.Book;
import com.example.bookstoresystem.entity.BookLog;
import com.example.bookstoresystem.entity.User;
import com.example.bookstoresystem.exception.BadRequestException;
import com.example.bookstoresystem.exception.EntityNotFoundException;
import com.example.bookstoresystem.repository.BookLogRepository;
import com.example.bookstoresystem.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {
    private BookRepository bookRepository;
    private BookLogRepository bookLogRepository;
    private UserService userService;

    public Book getBookById(int bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("There is no book with the ID of " + bookId);
        }
        return book.get();
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int bookId, Book updatedBook) {
        Book book = getBookById(bookId);
        updatedBook.setBookId(book.getBookId());
        return bookRepository.save(updatedBook);
    }

    public void deleteBook(int bookId) {
        bookRepository.delete(getBookById(bookId));
    }

    public BookLog lendBook(int bookId, int userId) {
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);
        BookLog bookLog = new BookLog(book.getBookId(), user.getUserId());
        try {
            return bookLogRepository.save(bookLog);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("The book is already lend to the user");
        }
    }

    public void returnBook(int bookId, int userId) {
        Book book = getBookById(bookId);
        User user = userService.getUserById(userId);
        Optional<BookLog> bookLog = bookLogRepository.findByBookIdAndUserId(book.getBookId(), user.getUserId());
        if (bookLog.isEmpty()) {
            throw new BadRequestException("The user has not borrowed the book");
        }
        bookLogRepository.delete(bookLog.get());
    }
}

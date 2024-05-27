package com.example.bookstoresystem;

import com.example.bookstoresystem.entity.Book;
import com.example.bookstoresystem.entity.BookLog;
import com.example.bookstoresystem.entity.User;
import com.example.bookstoresystem.exception.BadRequestException;
import com.example.bookstoresystem.exception.EntityNotFoundException;
import com.example.bookstoresystem.repository.BookLogRepository;
import com.example.bookstoresystem.repository.BookRepository;
import com.example.bookstoresystem.service.BookService;
import com.example.bookstoresystem.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookLogRepository bookLogRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private BookService bookService;

    @Test
    public void testGetBookById() {
        int bookId = 1;
        Book mockBook = MockObjects.getMockBook();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));

        Book validBook = bookService.getBookById(bookId);
        Executable invalidId = () -> bookService.getBookById(2);

        Assertions.assertEquals(mockBook, validBook);
        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
    }

    @Test
    public void testUpdateBook() {
        int bookId = 1;
        Book mockBook = MockObjects.getMockBook();
        Book updatedMockBook = MockObjects.getUpdatedMockBook();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        Mockito.when(bookRepository.save(updatedMockBook)).thenReturn(updatedMockBook);

        Executable invalidId = () -> bookService.getBookById(2);
        Book validBook = bookService.updateBook(bookId, updatedMockBook);

        Assertions.assertThrows(EntityNotFoundException.class, invalidId);
        Assertions.assertEquals(updatedMockBook, validBook);
        Mockito.verify(bookRepository, Mockito.times(1)).save(Mockito.any(Book.class));
    }

    @Test
    public void testDeleteBook() {
        int bookId = 1;
        Book mockBook = MockObjects.getMockBook();
        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        bookService.deleteBook(bookId);
        Mockito.verify(bookRepository, Mockito.times(1)).delete(mockBook);
    }

    @Test
    public void testLendBook() {
        int bookId = 1, userId = 1;
        Book mockBook = MockObjects.getMockBook();
        User mockUser = MockObjects.getMockUser();
        BookLog mockBookLog = MockObjects.getMockBookLog();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);

        Mockito.when(bookLogRepository.save(Mockito.any(BookLog.class))).thenReturn(mockBookLog);
        BookLog validBookLog = bookService.lendBook(bookId, userId);

        Mockito.when(bookLogRepository.save(Mockito.any(BookLog.class))).thenThrow(DataIntegrityViolationException.class);
        Executable duplicateBookLog = () -> bookService.lendBook(bookId, userId);

        Assertions.assertThrows(BadRequestException.class, duplicateBookLog);
        Assertions.assertEquals(mockBookLog, validBookLog);
        Mockito.verify(bookLogRepository, Mockito.times(2)).save(Mockito.any(BookLog.class));
    }

    @Test
    public void testReturnBook() {
        int bookId = 1, userId = 1;
        Book mockBook = MockObjects.getMockBook();
        User mockUser = MockObjects.getMockUser();
        BookLog mockBookLog = MockObjects.getMockBookLog();

        Mockito.when(bookRepository.findById(bookId)).thenReturn(Optional.of(mockBook));
        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);

        Mockito.when(bookLogRepository.findByBookIdAndUserId(bookId, userId)).thenReturn(Optional.of(mockBookLog));
        bookService.returnBook(bookId, userId);

        Mockito.when(bookLogRepository.findByBookIdAndUserId(bookId, userId)).thenReturn(Optional.empty());
        Executable invalidBookLog = () -> bookService.returnBook(bookId, userId);

        Assertions.assertThrows(BadRequestException.class, invalidBookLog);
        Mockito.verify(bookLogRepository, Mockito.times(1)).delete(mockBookLog);
    }
}

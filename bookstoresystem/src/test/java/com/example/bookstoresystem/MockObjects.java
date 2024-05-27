package com.example.bookstoresystem;

import com.example.bookstoresystem.entity.Book;
import com.example.bookstoresystem.entity.BookLog;
import com.example.bookstoresystem.entity.User;

public class MockObjects {
    static User getMockUser() {
        User user = new User();
        user.setUserId(1);
        user.setName("Kratos");
        return user;
    }

    static User getUpdatedMockUser() {
        User user = getMockUser();
        user.setName("Atreus");
        return user;
    }

    static Book getMockBook() {
        Book book = new Book();
        book.setBookId(1);
        book.setBookName("Ponniyin Selvan");
        book.setAuthor("Kalki");
        return book;
    }

    static Book getUpdatedMockBook() {
        Book book = getMockBook();
        book.setBookName("Ponniyin Selvan Naaval");
        return book;
    }

    static BookLog getMockBookLog() {
        BookLog bookLog = new BookLog();
        bookLog.setId(1);
        bookLog.setBookId(1);
        bookLog.setUserId(1);
        return bookLog;
    }
}

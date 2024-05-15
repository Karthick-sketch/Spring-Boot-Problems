package com.example.bookstoresystem.controller;

import com.example.bookstoresystem.entity.Book;
import com.example.bookstoresystem.entity.BookLog;
import com.example.bookstoresystem.exception.ErrorResponse;
import com.example.bookstoresystem.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Tag(name = "Book Controller", description = "Manage, lend and return books.")
@RestController
@RequestMapping("/bookstore")
public class BookController {
    private BookService bookService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Book doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of book", content = @Content(schema = @Schema(implementation = Book.class))),
    })
    @Operation(summary = "Get book by ID", description = "Returns a book record based on an ID.")
    @GetMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookById(@PathVariable int id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successful retrieval of books", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Book.class))))
    @Operation(summary = "Retrieves book record", description = "Provides a list of all books.")
    @GetMapping(value = "/book/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "Successful creation of book", content = @Content(schema = @Schema(implementation = Book.class)))
    @Operation(summary = "Create book record", description = "Creates a book record from the provided payload.")
    @PostMapping(value = "/book", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.createBook(book), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Book doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of an book", content = @Content(schema = @Schema(implementation = Book.class)))
    })
    @Operation(summary = "Update a book by ID", description = "Updates an existing book record from the provided payload and ID.")
    @PutMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> updateBook(@PathVariable int id, @RequestBody Book book) {
        return new ResponseEntity<>(bookService.updateBook(id, book), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Book doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of an book")
    })
    @Operation(summary = "Delete a book by ID", description = "Deletes a book record based on an ID.")
    @DeleteMapping(value = "/book/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Book/User doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful lend of a book", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful lend of a book", content = @Content(schema = @Schema(implementation = BookLog.class)))
    })
    @Operation(summary = "Lend book to student", description = "Lend a book to the student.")
    @PutMapping(value = "/lend/{bookId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookLog> lendBook(@PathVariable int bookId, @PathVariable int userId) {
        return new ResponseEntity<>(bookService.lendBook(bookId, userId), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Book/User doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful return of a book", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful return of a book", content = @Content(schema = @Schema(implementation = BookLog.class)))
    })
    @Operation(summary = "Return the book", description = "Return the borrowed book.")
    @PutMapping(value = "/return/{bookId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> returnBook(@PathVariable int bookId, @PathVariable int userId) {
        bookService.returnBook(bookId, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.example.courseenrollmentsystem.controller;

import com.example.courseenrollmentsystem.entity.Student;
import com.example.courseenrollmentsystem.exception.ErrorResponse;
import com.example.courseenrollmentsystem.service.StudentService;
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
@Tag(name = "Student Controller", description = "Create and retrieve student.")
@RestController
@RequestMapping("/student/student")
public class StudentController {
    private StudentService studentService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of student", content = @Content(schema = @Schema(implementation = Student.class))),
    })
    @Operation(summary = "Get student by ID", description = "Returns a student record based on an ID.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> getStudentById(@PathVariable int id) {
        return new ResponseEntity<>(studentService.getStudentById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successful retrieval of students", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Student.class))))
    @Operation(summary = "Retrieves student record", description = "Provides a list of all students.")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getAllStudents() {
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "Successful creation of student", content = @Content(schema = @Schema(implementation = Student.class)))
    @Operation(summary = "Create student record", description = "Creates a student record from the provided payload.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of a student", content = @Content(schema = @Schema(implementation = Student.class)))
    })
    @Operation(summary = "Update a student by ID", description = "Updates an existing student record from the provided payload and ID.")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student student) {
        return new ResponseEntity<>(studentService.updateStudent(id, student), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of a student")
    })
    @Operation(summary = "Delete a student by ID", description = "Deletes a student record based on an ID.")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

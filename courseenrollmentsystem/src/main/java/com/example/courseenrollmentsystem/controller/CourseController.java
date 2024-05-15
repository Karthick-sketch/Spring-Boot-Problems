package com.example.courseenrollmentsystem.controller;

import com.example.courseenrollmentsystem.entity.Course;
import com.example.courseenrollmentsystem.exception.ErrorResponse;
import com.example.courseenrollmentsystem.service.CourseService;
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
@Tag(name = "Course Controller", description = "Create and retrieve course.")
@RestController
@RequestMapping("/course")
public class CourseController {
    private CourseService courseService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful retrieval of course", content = @Content(schema = @Schema(implementation = Course.class))),
    })
    @Operation(summary = "Get course by ID", description = "Returns a course record based on an ID.")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> getCourseById(@PathVariable int id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200", description = "Successful retrieval of courses", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Course.class))))
    @Operation(summary = "Retrieves course record", description = "Provides a list of all courses.")
    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @ApiResponse(responseCode = "201", description = "Successful creation of course", content = @Content(schema = @Schema(implementation = Course.class)))
    @Operation(summary = "Create course record", description = "Creates a course record from the provided payload.")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> createCourse(@RequestBody Course student) {
        return new ResponseEntity<>(courseService.createCourse(student), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful update of a course", content = @Content(schema = @Schema(implementation = Course.class)))
    })
    @Operation(summary = "Update a course by ID", description = "Updates an existing course record from the provided payload and ID.")
    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Course> updateCourse(@PathVariable int id, @RequestBody Course student) {
        return new ResponseEntity<>(courseService.updateCourse(id, student), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "204", description = "Successful deletion of a course")
    })
    @Operation(summary = "Delete a course by ID", description = "Deletes a course record based on an ID.")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

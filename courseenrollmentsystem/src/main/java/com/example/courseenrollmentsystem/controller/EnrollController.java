package com.example.courseenrollmentsystem.controller;

import com.example.courseenrollmentsystem.dto.EnrolledCourseDTO;
import com.example.courseenrollmentsystem.entity.Enroll;
import com.example.courseenrollmentsystem.exception.ErrorResponse;
import com.example.courseenrollmentsystem.service.EnrollService;
import io.swagger.v3.oas.annotations.Operation;
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

import java.util.Map;

@AllArgsConstructor
@Tag(name = "Enroll Controller", description = "Enroll student to course.")
@RestController
@RequestMapping("/course/enroll")
public class EnrollController {
    private EnrollService enrollService;

    @ApiResponse(responseCode = "200", description = "Successful retrieval of enrolled students count.", content = @Content(schema = @Schema(implementation = EnrolledCourseDTO.class)))
    @Operation(summary = "Get enrollment count by course ID", description = "Returns enrolled students count of the given course.")
    @GetMapping(value = "/count/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EnrolledCourseDTO> getStudentsEnrolledCourseCount(@PathVariable int courseId) {
        return new ResponseEntity<>(enrollService.getStudentsEnrolledCourseCount(courseId), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Course/Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful enrollment of a student to a course", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful enrollment of a student to a course"),
    })
    @Operation(summary = "Enroll a student presence", description = "Enroll a student to a course.")
    @PutMapping(value = "/{studentId}/{courseId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Enroll> enrollCourse(@PathVariable int studentId, @PathVariable int courseId) {
        return new ResponseEntity<>(enrollService.enrollCourse(studentId, courseId), HttpStatus.OK);
    }
}

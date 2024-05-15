package com.example.attendancemanagementsystem.controller;

import com.example.attendancemanagementsystem.dto.StudentPresenceDTO;
import com.example.attendancemanagementsystem.entity.Attendance;
import com.example.attendancemanagementsystem.exception.ErrorResponse;
import com.example.attendancemanagementsystem.service.AttendanceService;
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

import java.time.LocalDate;

@AllArgsConstructor
@Tag(name = "Attendance Controller", description = "Log Students' attendance.")
@RestController
@RequestMapping("/attendance")
public class AttendanceController {
    private AttendanceService attendanceService;

    @ApiResponse(responseCode = "200", description = "Successful retrieval of students' presence count.", content = @Content(schema = @Schema(implementation = StudentPresenceDTO.class)))
    @Operation(summary = "Get student count by Date", description = "Returns students logged count on the given date.")
    @GetMapping(value = "/log/count/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentPresenceDTO> getLogCount(@PathVariable LocalDate date) {
        return new ResponseEntity<>(attendanceService.getLogCount(date), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Student doesn't exist", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: unsuccessful login of a student", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "200", description = "Successful login of a student"),
    })
    @Operation(summary = "Log a student presence", description = "Log a student presence for today.")
    @PutMapping(value = "/log/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Attendance> logStudent(@PathVariable int studentId) {
        return new ResponseEntity<>(attendanceService.logStudent(studentId), HttpStatus.CREATED);
    }
}

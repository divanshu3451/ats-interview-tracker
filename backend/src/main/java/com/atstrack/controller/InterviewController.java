package com.atstrack.controller;

import com.atstrack.dto.InterviewDTO;
import com.atstrack.entity.Interview;
import com.atstrack.response.ApiResponse;
import com.atstrack.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@Tag(name = "Interviews", description = "Interview Management APIs")
public class InterviewController {

    private final InterviewService interviewService;

    @GetMapping("/{id}")
    @Operation(summary = "Get interview by ID")
    public ResponseEntity<ApiResponse<InterviewDTO>> getInterviewById(@PathVariable Long id) {
        InterviewDTO interview = interviewService.getInterviewById(id);
        return ResponseEntity.ok(ApiResponse.success(interview));
    }

    @GetMapping("/application/{applicationId}")
    @Operation(summary = "Get all interviews for an application")
    public ResponseEntity<ApiResponse<List<InterviewDTO>>> getInterviewsByApplication(
            @PathVariable Long applicationId) {
        List<InterviewDTO> interviews = interviewService.getInterviewsByApplication(applicationId);
        return ResponseEntity.ok(ApiResponse.success(interviews));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming interviews")
    public ResponseEntity<ApiResponse<List<InterviewDTO>>> getUpcomingInterviews(
            @RequestParam(defaultValue = "7") int daysAhead) {
        List<InterviewDTO> interviews = interviewService.getUpcomingInterviews(daysAhead);
        return ResponseEntity.ok(ApiResponse.success(interviews));
    }

    @PostMapping("/application/{applicationId}")
    @Operation(summary = "Add a new interview to an application")
    public ResponseEntity<ApiResponse<InterviewDTO>> createInterview(
            @PathVariable Long applicationId,
            @Valid @RequestBody Interview interview) {
        InterviewDTO createdInterview = interviewService.createInterview(applicationId, interview);
        return new ResponseEntity<>(
                ApiResponse.success(createdInterview, "Interview created successfully"),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing interview")
    public ResponseEntity<ApiResponse<InterviewDTO>> updateInterview(
            @PathVariable Long id,
            @Valid @RequestBody Interview interviewDetails) {
        InterviewDTO updatedInterview = interviewService.updateInterview(id, interviewDetails);
        return ResponseEntity.ok(ApiResponse.success(updatedInterview, "Interview updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an interview")
    public ResponseEntity<ApiResponse<Void>> deleteInterview(@PathVariable Long id) {
        interviewService.deleteInterview(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Interview deleted successfully"));
    }
}

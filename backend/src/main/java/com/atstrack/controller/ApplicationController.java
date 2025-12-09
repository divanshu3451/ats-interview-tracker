package com.atstrack.controller;

import com.atstrack.dto.ApplicationCreateDTO;
import com.atstrack.dto.ApplicationDTO;
import com.atstrack.dto.ApplicationUpdateDTO;
import com.atstrack.entity.ApplicationStatus;
import com.atstrack.response.ApiResponse;
import com.atstrack.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Applications", description = "Job Application Management APIs")
public class ApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    @Operation(summary = "Get all applications with pagination and filtering")
    public ResponseEntity<ApiResponse<Page<ApplicationDTO>>> getAllApplications(
            @RequestParam(required = false) ApplicationStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("ASC") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ApplicationDTO> applications;
        if (status != null) {
            applications = applicationService.getApplicationsByStatus(status, pageable);
        } else {
            applications = applicationService.getAllApplications(pageable);
        }

        return ResponseEntity.ok(ApiResponse.success(applications));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get application by ID")
    public ResponseEntity<ApiResponse<ApplicationDTO>> getApplicationById(@PathVariable Long id) {
        ApplicationDTO application = applicationService.getApplicationById(id);
        return ResponseEntity.ok(ApiResponse.success(application));
    }

    @PostMapping
    @Operation(summary = "Create a new application")
    public ResponseEntity<ApiResponse<ApplicationDTO>> createApplication(
            @Valid @RequestBody ApplicationCreateDTO createDTO) {
        ApplicationDTO createdApplication = applicationService.createApplication(createDTO);
        return new ResponseEntity<>(
                ApiResponse.success(createdApplication, "Application created successfully"),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing application")
    public ResponseEntity<ApiResponse<ApplicationDTO>> updateApplication(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationUpdateDTO updateDTO) {
        ApplicationDTO updatedApplication = applicationService.updateApplication(id, updateDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedApplication, "Application updated successfully"));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Update application status (for Kanban drag-and-drop)")
    public ResponseEntity<ApiResponse<ApplicationDTO>> updateStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        ApplicationDTO updatedApplication = applicationService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(updatedApplication, "Status updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an application")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Application deleted successfully"));
    }
}

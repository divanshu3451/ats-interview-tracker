package com.atstrack.controller;

import com.atstrack.dto.MetricsDTO;
import com.atstrack.response.ApiResponse;
import com.atstrack.service.MetricsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metrics")
@RequiredArgsConstructor
@Tag(name = "Metrics", description = "Analytics and Metrics APIs")
public class MetricsController {

    private final MetricsService metricsService;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard metrics and analytics")
    public ResponseEntity<ApiResponse<MetricsDTO>> getDashboardMetrics() {
        MetricsDTO metrics = metricsService.getDashboardMetrics();
        return ResponseEntity.ok(ApiResponse.success(metrics));
    }
}

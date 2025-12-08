package com.atstrack.dto;

import com.atstrack.entity.ApplicationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {

    private Long id;
    private Long companyId;
    private String companyName;
    private String jobTitle;
    private ApplicationStatus status;
    private String jobUrl;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private LocalDate appliedDate;
    private String notes;
    private Integer priority;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer interviewCount;
}

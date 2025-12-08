package com.atstrack.dto;

import com.atstrack.entity.InterviewStatus;
import com.atstrack.entity.InterviewType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterviewDTO {

    private Long id;
    private Long applicationId;
    private String roundName;
    private LocalDate scheduledDate;
    private LocalTime scheduledTime;
    private InterviewType interviewType;
    private InterviewStatus status;
    private String notes;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

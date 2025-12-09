package com.atstrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MetricsDTO {

    private long totalApplications;
    private long savedCount;
    private long appliedCount;
    private long screeningCount;
    private long interviewingCount;
    private long offerCount;
    private long rejectedCount;
    private Double averageDaysToInterview;
    private Double conversionRate; // (offers / applied) * 100
    private long upcomingInterviewsCount;
}

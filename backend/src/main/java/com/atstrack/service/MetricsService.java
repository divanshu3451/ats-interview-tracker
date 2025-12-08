package com.atstrack.service;

import com.atstrack.dto.MetricsDTO;
import com.atstrack.entity.ApplicationStatus;
import com.atstrack.repository.ApplicationRepository;
import com.atstrack.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MetricsService {

    private final ApplicationRepository applicationRepository;
    private final InterviewRepository interviewRepository;

    public MetricsDTO getDashboardMetrics() {
        MetricsDTO metrics = new MetricsDTO();

        // Count applications by status
        metrics.setTotalApplications(applicationRepository.count());
        metrics.setSavedCount(applicationRepository.countByStatus(ApplicationStatus.SAVED));
        metrics.setAppliedCount(applicationRepository.countByStatus(ApplicationStatus.APPLIED));
        metrics.setScreeningCount(applicationRepository.countByStatus(ApplicationStatus.SCREENING));
        metrics.setInterviewingCount(applicationRepository.countByStatus(ApplicationStatus.INTERVIEWING));
        metrics.setOfferCount(applicationRepository.countByStatus(ApplicationStatus.OFFER));
        metrics.setRejectedCount(applicationRepository.countByStatus(ApplicationStatus.REJECTED));

        // Calculate average days to interview
        Double averageDays = applicationRepository.getAverageDaysToInterview();
        metrics.setAverageDaysToInterview(averageDays != null ? averageDays : 0.0);

        // Calculate conversion rate (offers / applied) * 100
        long appliedCount = metrics.getAppliedCount();
        long offerCount = metrics.getOfferCount();
        if (appliedCount > 0) {
            metrics.setConversionRate((double) offerCount / appliedCount * 100);
        } else {
            metrics.setConversionRate(0.0);
        }

        // Count upcoming interviews (next 7 days)
        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);
        metrics.setUpcomingInterviewsCount(interviewRepository.countUpcomingInterviews(today, nextWeek));

        return metrics;
    }
}

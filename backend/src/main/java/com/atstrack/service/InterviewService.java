package com.atstrack.service;

import com.atstrack.dto.InterviewDTO;
import com.atstrack.entity.Application;
import com.atstrack.entity.Interview;
import com.atstrack.exception.ResourceNotFoundException;
import com.atstrack.repository.ApplicationRepository;
import com.atstrack.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public List<InterviewDTO> getInterviewsByApplication(Long applicationId) {
        return interviewRepository.findByApplicationId(applicationId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public InterviewDTO getInterviewById(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview", "id", id));
        return convertToDTO(interview);
    }

    @Transactional
    public InterviewDTO createInterview(Long applicationId, Interview interview) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", applicationId));

        interview.setApplication(application);
        Interview savedInterview = interviewRepository.save(interview);
        return convertToDTO(savedInterview);
    }

    @Transactional
    public InterviewDTO updateInterview(Long id, Interview interviewDetails) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview", "id", id));

        interview.setRoundName(interviewDetails.getRoundName());
        interview.setScheduledDate(interviewDetails.getScheduledDate());
        interview.setScheduledTime(interviewDetails.getScheduledTime());
        interview.setInterviewType(interviewDetails.getInterviewType());
        interview.setStatus(interviewDetails.getStatus());
        interview.setNotes(interviewDetails.getNotes());
        interview.setFeedback(interviewDetails.getFeedback());

        Interview updatedInterview = interviewRepository.save(interview);
        return convertToDTO(updatedInterview);
    }

    @Transactional
    public void deleteInterview(Long id) {
        Interview interview = interviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview", "id", id));
        interviewRepository.delete(interview);
    }

    public List<InterviewDTO> getUpcomingInterviews(int daysAhead) {
        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(daysAhead);
        return interviewRepository.findUpcomingInterviews(today, futureDate)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private InterviewDTO convertToDTO(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(interview.getId());
        dto.setApplicationId(interview.getApplication().getId());
        dto.setRoundName(interview.getRoundName());
        dto.setScheduledDate(interview.getScheduledDate());
        dto.setScheduledTime(interview.getScheduledTime());
        dto.setInterviewType(interview.getInterviewType());
        dto.setStatus(interview.getStatus());
        dto.setNotes(interview.getNotes());
        dto.setFeedback(interview.getFeedback());
        dto.setCreatedAt(interview.getCreatedAt());
        dto.setUpdatedAt(interview.getUpdatedAt());
        return dto;
    }
}

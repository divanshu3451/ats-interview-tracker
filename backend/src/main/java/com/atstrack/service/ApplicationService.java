package com.atstrack.service;

import com.atstrack.dto.ApplicationCreateDTO;
import com.atstrack.dto.ApplicationDTO;
import com.atstrack.dto.ApplicationUpdateDTO;
import com.atstrack.entity.Application;
import com.atstrack.entity.ApplicationStatus;
import com.atstrack.entity.Company;
import com.atstrack.exception.ResourceNotFoundException;
import com.atstrack.repository.ApplicationRepository;
import com.atstrack.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final CompanyRepository companyRepository;

    public Page<ApplicationDTO> getAllApplications(Pageable pageable) {
        return applicationRepository.findAll(pageable)
                .map(this::convertToDTO);
    }

    public Page<ApplicationDTO> getApplicationsByStatus(ApplicationStatus status, Pageable pageable) {
        return applicationRepository.findByStatus(status, pageable)
                .map(this::convertToDTO);
    }

    public ApplicationDTO getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));
        return convertToDTO(application);
    }

    @Transactional
    public ApplicationDTO createApplication(ApplicationCreateDTO createDTO) {
        Company company = companyRepository.findById(createDTO.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", createDTO.getCompanyId()));

        Application application = new Application();
        application.setCompany(company);
        application.setJobTitle(createDTO.getJobTitle());
        application.setStatus(createDTO.getStatus());
        application.setJobUrl(createDTO.getJobUrl());
        application.setSalaryMin(createDTO.getSalaryMin());
        application.setSalaryMax(createDTO.getSalaryMax());
        application.setAppliedDate(createDTO.getAppliedDate());
        application.setNotes(createDTO.getNotes());
        application.setPriority(createDTO.getPriority() != null ? createDTO.getPriority() : 0);

        Application savedApplication = applicationRepository.save(application);
        return convertToDTO(savedApplication);
    }

    @Transactional
    public ApplicationDTO updateApplication(Long id, ApplicationUpdateDTO updateDTO) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        application.setJobTitle(updateDTO.getJobTitle());
        application.setStatus(updateDTO.getStatus());
        application.setJobUrl(updateDTO.getJobUrl());
        application.setSalaryMin(updateDTO.getSalaryMin());
        application.setSalaryMax(updateDTO.getSalaryMax());
        application.setAppliedDate(updateDTO.getAppliedDate());
        application.setNotes(updateDTO.getNotes());
        application.setPriority(updateDTO.getPriority());

        Application updatedApplication = applicationRepository.save(application);
        return convertToDTO(updatedApplication);
    }

    @Transactional
    public ApplicationDTO updateStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));

        application.setStatus(status);
        Application updatedApplication = applicationRepository.save(application);
        return convertToDTO(updatedApplication);
    }

    @Transactional
    public void deleteApplication(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application", "id", id));
        applicationRepository.delete(application);
    }

    public List<ApplicationDTO> getApplicationsByCompany(Long companyId) {
        return applicationRepository.findByCompanyId(companyId, Pageable.unpaged())
                .getContent()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ApplicationDTO convertToDTO(Application application) {
        ApplicationDTO dto = new ApplicationDTO();
        dto.setId(application.getId());
        dto.setCompanyId(application.getCompany().getId());
        dto.setCompanyName(application.getCompany().getName());
        dto.setJobTitle(application.getJobTitle());
        dto.setStatus(application.getStatus());
        dto.setJobUrl(application.getJobUrl());
        dto.setSalaryMin(application.getSalaryMin());
        dto.setSalaryMax(application.getSalaryMax());
        dto.setAppliedDate(application.getAppliedDate());
        dto.setNotes(application.getNotes());
        dto.setPriority(application.getPriority());
        dto.setCreatedAt(application.getCreatedAt());
        dto.setUpdatedAt(application.getUpdatedAt());
        dto.setInterviewCount(application.getInterviews() != null ? application.getInterviews().size() : 0);
        return dto;
    }
}

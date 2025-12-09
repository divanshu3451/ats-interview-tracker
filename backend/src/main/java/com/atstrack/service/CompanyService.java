package com.atstrack.service;

import com.atstrack.dto.CompanyDTO;
import com.atstrack.entity.Company;
import com.atstrack.exception.DuplicateResourceException;
import com.atstrack.exception.ResourceNotFoundException;
import com.atstrack.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

    private final CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return convertToDTO(company);
    }

    @Transactional
    public CompanyDTO createCompany(Company company) {
        if (companyRepository.existsByNameIgnoreCase(company.getName())) {
            throw new DuplicateResourceException("Company", "name", company.getName());
        }
        Company savedCompany = companyRepository.save(company);
        return convertToDTO(savedCompany);
    }

    @Transactional
    public CompanyDTO updateCompany(Long id, Company companyDetails) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));

        // Check if name is being changed to an existing name
        if (!company.getName().equalsIgnoreCase(companyDetails.getName()) &&
                companyRepository.existsByNameIgnoreCase(companyDetails.getName())) {
            throw new DuplicateResourceException("Company", "name", companyDetails.getName());
        }

        company.setName(companyDetails.getName());
        company.setWebsite(companyDetails.getWebsite());
        company.setIndustry(companyDetails.getIndustry());
        company.setNotes(companyDetails.getNotes());

        Company updatedCompany = companyRepository.save(company);
        return convertToDTO(updatedCompany);
    }

    @Transactional
    public void deleteCompany(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        companyRepository.delete(company);
    }

    private CompanyDTO convertToDTO(Company company) {
        CompanyDTO dto = new CompanyDTO();
        dto.setId(company.getId());
        dto.setName(company.getName());
        dto.setWebsite(company.getWebsite());
        dto.setIndustry(company.getIndustry());
        dto.setNotes(company.getNotes());
        dto.setCreatedAt(company.getCreatedAt());
        dto.setUpdatedAt(company.getUpdatedAt());
        dto.setApplicationCount(company.getApplications() != null ? company.getApplications().size() : 0);
        return dto;
    }
}

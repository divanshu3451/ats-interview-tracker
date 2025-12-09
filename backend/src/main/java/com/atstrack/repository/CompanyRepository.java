package com.atstrack.repository;

import com.atstrack.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByNameIgnoreCase(String name);

    List<Company> findByIndustry(String industry);

    @Query("SELECT DISTINCT c.industry FROM Company c WHERE c.industry IS NOT NULL ORDER BY c.industry")
    List<String> findAllIndustries();

    boolean existsByNameIgnoreCase(String name);
}

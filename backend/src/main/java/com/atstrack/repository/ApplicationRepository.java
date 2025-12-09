package com.atstrack.repository;

import com.atstrack.entity.Application;
import com.atstrack.entity.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {

    Page<Application> findByStatus(ApplicationStatus status, Pageable pageable);

    Page<Application> findByCompanyId(Long companyId, Pageable pageable);

    List<Application> findByStatusOrderByAppliedDateDesc(ApplicationStatus status);

    @Query("SELECT a FROM Application a WHERE a.appliedDate BETWEEN :startDate AND :endDate")
    List<Application> findByAppliedDateBetween(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = :status")
    long countByStatus(@Param("status") ApplicationStatus status);

    @Query("SELECT a FROM Application a LEFT JOIN FETCH a.interviews WHERE a.id = :id")
    Application findByIdWithInterviews(@Param("id") Long id);

    @Query("SELECT a FROM Application a LEFT JOIN FETCH a.company WHERE a.id = :id")
    Application findByIdWithCompany(@Param("id") Long id);

    @Query("SELECT AVG(DATEDIFF(i.scheduledDate, a.appliedDate)) " +
            "FROM Application a JOIN a.interviews i " +
            "WHERE a.appliedDate IS NOT NULL AND i.scheduledDate IS NOT NULL")
    Double getAverageDaysToInterview();

    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = 'OFFER'")
    long countOffers();

    @Query("SELECT COUNT(a) FROM Application a WHERE a.status = 'APPLIED'")
    long countApplied();
}

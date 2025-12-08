package com.atstrack.repository;

import com.atstrack.entity.Interview;
import com.atstrack.entity.InterviewStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    List<Interview> findByApplicationId(Long applicationId);

    List<Interview> findByStatus(InterviewStatus status);

    @Query("SELECT i FROM Interview i WHERE i.scheduledDate BETWEEN :startDate AND :endDate ORDER BY i.scheduledDate, i.scheduledTime")
    List<Interview> findUpcomingInterviews(@Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT i FROM Interview i WHERE i.scheduledDate >= :today AND i.status = 'SCHEDULED' ORDER BY i.scheduledDate, i.scheduledTime")
    List<Interview> findFutureScheduledInterviews(@Param("today") LocalDate today);

    @Query("SELECT COUNT(i) FROM Interview i WHERE i.status = 'SCHEDULED' AND i.scheduledDate BETWEEN :startDate AND :endDate")
    long countUpcomingInterviews(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    List<Interview> findByApplicationIdOrderByScheduledDateDesc(Long applicationId);
}

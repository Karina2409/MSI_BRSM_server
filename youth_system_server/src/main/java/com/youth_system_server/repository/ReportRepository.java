package com.youth_system_server.repository;

import com.youth_system_server.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findReportsByDormNumber(Integer dormNumber);

    @Query("SELECT r FROM Report r WHERE r.report_date >= :startDate")
    List<Report> findReportsByDateAfter(@Param("startDate") Date startDate);

}

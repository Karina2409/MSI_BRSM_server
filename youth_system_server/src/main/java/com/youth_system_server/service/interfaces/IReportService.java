package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.Report;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IReportService {
    List<Report> getAllReports();
    ResponseEntity<Set<Report>> saveReport();
    Optional<Report> getReportById(Long id);
    ResponseEntity<Void> deleteReportById(Long id);
    List<Report> getReportsByDormNumber(int dormNumber);
    void downloadReport(Long reportId);
}

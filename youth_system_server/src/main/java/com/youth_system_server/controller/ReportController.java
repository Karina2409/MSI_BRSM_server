package com.youth_system_server.controller;

import com.youth_system_server.entity.Report;
import com.youth_system_server.service.interfaces.IReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @GetMapping
    public List<Report> getReports(){
        return reportService.getAllReports();
    }

    @PostMapping("/post/month")
    public ResponseEntity<Set<Report>> createReport(){
        return reportService.saveReport();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportFile(@PathVariable Long id){
        Optional<Report> reportOptional = reportService.getReportById(id);
        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + report.getReport_name());

            return new ResponseEntity<>(report, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId){
        return reportService.deleteReportById(reportId);
    }

    @GetMapping("/dormNumber/{dormNumber}")
    public List<Report> getReportByDormNumber(@PathVariable("dormNumber") Integer dormNumber){
        return reportService.getReportsByDormNumber(dormNumber);
    }

    @PostMapping("/download/{reportId}")
    public void downloadReport(@PathVariable Long reportId){
        reportService.downloadReport(reportId);
    }
}

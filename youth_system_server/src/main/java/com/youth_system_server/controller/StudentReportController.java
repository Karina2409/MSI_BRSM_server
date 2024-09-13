package com.youth_system_server.controller;

import com.youth_system_server.service.StudentReportService;
import com.youth_system_server.service.interfaces.IStudentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sr")
public class StudentReportController {

    @Autowired
    private IStudentReportService studentReportService;

    @DeleteMapping("/remove/student/{studentId}/report/{reportId}")
    public void removeStudentFromReport(@PathVariable Long studentId, @PathVariable Long reportId){
        studentReportService.removeStudentFromReport(studentId, reportId);
    }
}

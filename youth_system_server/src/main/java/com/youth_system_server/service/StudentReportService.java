package com.youth_system_server.service;

import com.youth_system_server.repository.StudentReportRepository;
import com.youth_system_server.service.interfaces.IStudentReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentReportService implements IStudentReportService {

    @Autowired
    private StudentReportRepository studentReportRepository;

    @Override
    @Transactional
    public void removeStudentFromReport(Long studentId, Long reportId) {
        studentReportRepository.removeStudentFromReport(studentId, reportId);
    }
}

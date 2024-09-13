package com.youth_system_server.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.youth_system_server.entity.Report;
import com.youth_system_server.entity.Student;
import com.youth_system_server.help.DateFormat;
import com.youth_system_server.pdf.PdfGenerator;
import com.youth_system_server.pdf.ReportTemplate;
import com.youth_system_server.repository.ReportRepository;
import com.youth_system_server.repository.StudentReportRepository;
import com.youth_system_server.repository.StudentRepository;
import com.youth_system_server.service.interfaces.IReportService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class ReportService implements IReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentReportRepository studentReportRepository;

    @Override
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    @Override
    @Transactional
    public ResponseEntity<Set<Report>> saveReport() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date oneMonthAgo = calendar.getTime();

        List<Student> students = studentRepository.findStudentsByEventDateAfter(oneMonthAgo);

        List<Report> recentReports = reportRepository.findReportsByDateAfter(oneMonthAgo);
        if (!recentReports.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Set<Report> returnReports = new HashSet<>();

        for (int numberOfDormitory = 1; numberOfDormitory <= 5; numberOfDormitory++) {
            boolean hasParticipatingStudents = false;
            Set<Student> studentsToReport = new HashSet<>();

            for (Student student : students) {
                Integer optCount = studentRepository.findOptCountByStudentIdAndEventDateAfter(student.getStudentId(), oneMonthAgo);
                if (optCount != null) {
                    Integer dormNumber = student.getDorm_number();
                    if (dormNumber != null && dormNumber == numberOfDormitory) {
                        studentsToReport.add(student);
                        hasParticipatingStudents = true;
                    }
                }
            }

            if (hasParticipatingStudents) {

                String fileName = "докладная_" + DateFormat.Date_Format(new Date()) + "_obsh" + numberOfDormitory + ".pdf";

                Report report = new Report();
                report.setReport_name(fileName);
                report.setReport_date(new Date());
                report.setDormNumber(numberOfDormitory);
                report.setStudents(studentsToReport);

                reportRepository.save(report);
                returnReports.add(report);

                for (Student student : studentsToReport) {
                    Integer optCount = studentRepository.findOptCountByStudentIdAndEventDateAfter(student.getStudentId(), oneMonthAgo);
                    studentReportRepository.addStudentToReport(student.getStudentId(), report.getReportId(), optCount);
                }
            }
        }
        return ResponseEntity.ok().body(returnReports);
    }

    @Override
    public Optional<Report> getReportById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    @Transactional
    public ResponseEntity<Void> deleteReportById(Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);

        if (reportOptional.isPresent()) {
            Report report = reportOptional.get();

            for (Student student : studentReportRepository.findStudentsByReportId(report.getReportId())) {
                studentReportRepository.removeStudentFromReport(student.getStudentId(), report.getReportId());
            }

            reportRepository.delete(report);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<Report> getReportsByDormNumber(int dormNumber) {
        return reportRepository.findReportsByDormNumber(dormNumber);
    }

    @Override
    public void downloadReport(Long reportId) {

        PdfGenerator pdfGenerator = new PdfGenerator();

        ReportTemplate reportTemplate = new ReportTemplate();
        String directoryName = "D:/универ/2курс/4семестр/Курсач/документация/докладные";

        Path directoryPath = Paths.get(directoryName);

        Report report = reportRepository.findById(reportId).get();

        Set<Student> students = studentReportRepository.findStudentsByReportId(reportId);

        String reportHeader = "Заместителю начальника студгородка " +
                "по информационно-воспитательной работе\n" +
                "Чурбановой О.П.\n";


        StringBuilder studentsInfo = new StringBuilder();

        for (Student student : students) {
            studentsInfo.append("студенту факультета ")
                    .append(student.getStudent_faculty())
                    .append(" группы ")
                    .append(student.getGroup_number())
                    .append(" ")
                    .append(student.getStudent_full_name_d())
                    .append(", проживающему в общежитии №")
                    .append(report.getDormNumber())
                    .append(", к. ")
                    .append(student.getDorm_block_number())
                    .append(" в количестве ")
                    .append(studentReportRepository.findOptByStudentId(student.getStudentId()))
                    .append(" часов;\n");
        }

        String reportContent = reportTemplate.generateContent(studentsInfo);
        String reportBeforeContent = reportTemplate.generateBeforeContent();

        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }

            String fileName = directoryName + "/докладная_" + DateFormat.Date_Format(new Date()) + "_obsh" + report.getDormNumber() + ".pdf";
            float[] columnWidths = {1, 1};
            pdfGenerator.createPDF(fileName, reportHeader, reportBeforeContent, reportContent, columnWidths);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);

            Document document = new Document(pdfDocument);
            document.add(new Paragraph(reportContent));
            document.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}

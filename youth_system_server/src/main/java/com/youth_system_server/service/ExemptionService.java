package com.youth_system_server.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.youth_system_server.entity.*;
import com.youth_system_server.help.DateFormat;
import com.youth_system_server.pdf.ExemptionTemplate;
import com.youth_system_server.pdf.PdfGenerator;
import com.youth_system_server.repository.EventRepository;
import com.youth_system_server.repository.ExemptionRepository;
import com.youth_system_server.repository.ExemptionStudentsRepository;
import com.youth_system_server.repository.StudentRepository;
import com.youth_system_server.service.interfaces.IExemptionService;
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
public class ExemptionService implements IExemptionService {
    @Autowired
    private ExemptionRepository exemptionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ExemptionStudentsRepository exemptionStudentsRepository;

    @Override
    public List<Exemption> getAllExemptions() {
        return exemptionRepository.findAll();
    }

    @Override
    @Transactional
    public void saveExemption(Long eventId, Set<Long> studentIds) {

        List<Student> students = studentRepository.findAllById(studentIds);

        Set<FacultyEnum> faculties = new HashSet<>();
        for (Student student : students) {
            faculties.add(student.getStudent_faculty());
        }

        for (FacultyEnum faculty : faculties) {

            String fileName = "освобождение_" + DateFormat.Date_Format(new Date()) + "_" + faculty + ".pdf";

            Exemption exemption = new Exemption();
            exemption.setExemption_name(fileName);
            exemption.setExemption_date(new Date());
            exemption.setStudentsFacultyExemption(faculty);

            Optional<Event> event = eventRepository.findById(eventId);
            if (!event.isPresent()) {
                throw new IllegalArgumentException("Event not found with id: " + eventId);
            }
            exemption.setEvent(event.get());
            exemption.setEventName(event.get().getEventName());

            exemptionRepository.save(exemption);

            for (Long studentId : studentIds) {
                Optional<Student> student = studentRepository.findById(studentId);
                student.ifPresent(s -> exemptionStudentsRepository.saveExemptionStudent(exemption.getExemptionId(), s.getStudentId()));
            }

        }
    }

    @Override
    public ResponseEntity<Void> deleteExemptionById(Long exemption_id) {
        Optional<Exemption> exemption = exemptionRepository.findById(exemption_id);
        exemptionStudentsRepository.delete(exemptionStudentsRepository.findById(exemption_id).get());
        if (exemption.isPresent()) {
            exemptionRepository.delete(exemption.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public List<Exemption> getExemptionsByFaculty(FacultyEnum faculty) {
        return exemptionRepository.findByStudentsFacultyExemption(faculty);
    }

    @Override
    public void downloadExemption(Long exemptionId) {
        PdfGenerator pdfGenerator = new PdfGenerator();

        ExemptionTemplate exemptionTemplate = new ExemptionTemplate(eventRepository);
        String directoryName = "D:/универ/2курс/4семестр/Курсач/документация/освобождения";

        Path directoryPath = Paths.get(directoryName);

        Set<Student> students = exemptionStudentsRepository.findStudentsByExemptionId(exemptionId);

        Exemption exemption = exemptionRepository.findById(exemptionId).get();

        FacultyEnum faculty = exemption.getStudentsFacultyExemption();



        StringBuilder studentsInfo = new StringBuilder();
        int k = 0;
        String exemptionHeader = exemptionTemplate.generateHeader(faculty,
                DeanData.getDean(faculty));
        for (Student student : students) {
            if (k != 0) {
                studentsInfo.append(", ");
            }
            studentsInfo.append("гр. ")
                    .append(student.getGroup_number())
                    .append(" ")
                    .append(student.getLastName())
                    .append(" ")
                    .append(student.getFirstName())
                    .append(" ")
                    .append(student.getMiddle_name());
            k++;

        }
        String exemptionContent = exemptionTemplate.generateContent(
                studentsInfo,
                exemption.getEvent().getEventId()
        );

        try {
            if (!Files.exists(directoryPath)) {
                Files.createDirectories(directoryPath);
            }
            String fileName = directoryName + "/освобождение_" + DateFormat.Date_Format(new Date()) + "_" + faculty + ".pdf";
            float[] columnWidths = {3, 1};
            pdfGenerator.createPDF(fileName, exemptionHeader, exemptionTemplate.generateBeforeContent(), exemptionContent, columnWidths);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(outputStream);
            PdfDocument pdfDocument = new PdfDocument(writer);

            Document document = new Document(pdfDocument);
            document.add(new Paragraph(exemptionContent));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Exemption> getExemptionsByEventName(String eventName){
        return exemptionRepository.findByEventNameStartingWithIgnoreCase(eventName);
    }
}

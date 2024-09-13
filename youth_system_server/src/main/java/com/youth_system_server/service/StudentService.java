package com.youth_system_server.service;

import com.youth_system_server.entity.Student;
import com.youth_system_server.repository.StudentEventRepository;
import com.youth_system_server.repository.StudentRepository;
import com.youth_system_server.service.interfaces.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentService implements IStudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentEventRepository studentEventRepository;

    @Autowired
    private EventService eventService;

    @Override
    public List<Student> findAllStudents(){
        return studentRepository.findAll();
    }

    @Override
    public ResponseEntity<Void> deleteStudentById(Long student_id) {
        Optional<Student> student = studentRepository.findById((long) student_id);
        if (student.isPresent()) {
            studentRepository.delete(student.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Student getStudentById(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        return optionalStudent.orElse(null);
    }

    @Override
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> getStudentsByEventId(Long eventId) {
        return studentRepository.findStudentsByEventId(eventId);
    }

    @Override
    public List<Student> getStudentsByLastName(String lastName) {
        return studentRepository.findByLastNameStartingWithIgnoreCase(lastName);
    }

    @Override
    public int countEventsWithPetitionByStudentId(Long studentId) {
        return studentEventRepository.countEventsWithPetitionByStudentId(studentId);
    }

    @Override
    public List<Student> findEligibleStudents() {
        List<Student> allStudents = studentRepository.findAll();
        return allStudents.stream()
                .filter(student -> eventService.getEventByStudentIdPetition(student.getStudentId()).size() >= 5)
                .collect(Collectors.toList());
    }
}

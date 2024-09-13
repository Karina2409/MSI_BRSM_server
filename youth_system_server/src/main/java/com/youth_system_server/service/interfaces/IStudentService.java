package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStudentService {
    List<Student> findAllStudents();
    ResponseEntity<Void> deleteStudentById(Long student_id);
    Student getStudentById(Long id);
    Student createStudent(Student student);
    List<Student> getStudentsByEventId(Long eventId);
    List<Student> getStudentsByLastName(String lastName);
    int countEventsWithPetitionByStudentId(Long studentId);
    List<Student> findEligibleStudents();
}

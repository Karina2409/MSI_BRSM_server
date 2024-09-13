package com.youth_system_server.controller;

import com.youth_system_server.entity.Event;
import com.youth_system_server.entity.Student;
import com.youth_system_server.service.EventService;
import com.youth_system_server.service.StudentService;
import com.youth_system_server.service.UserService;
import com.youth_system_server.service.interfaces.IEventService;
import com.youth_system_server.service.interfaces.IStudentService;
import com.youth_system_server.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private IStudentService studentService;
    @Autowired
    private IEventService eventService;

    @Autowired
    private IUserService userService;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping
    public List<Student> getStudents() {

        return studentService.findAllStudents();
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable Long studentId){
        return studentService.getStudentById(studentId);
    }

    @DeleteMapping("/delete/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        return studentService.deleteStudentById(studentId);
    }

    @PostMapping("/post")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student createdStudent = studentService.createStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/user/{userId}/events")
    public List<Event> getEventsByUserId(@PathVariable Long userId) {
        Long studentId = userService.findStudentById(userId).getStudentId();
        return eventService.getEventsByStudentId(studentId);
    }

    @GetMapping("/{studentId}/events")
    public List<Event> getEventsByStudentId(@PathVariable Long studentId) {
        return eventService.getEventsByStudentId(studentId);
    }

    @PutMapping("/student/{studentId}")
    public ResponseEntity<?> updateStudent(@PathVariable Long studentId, @RequestBody Student updateStudent) {
        Student student = studentService.getStudentById(studentId);
        if (student != null) {
            student.setLastName(updateStudent.getLastName());
            student.setFirstName(updateStudent.getFirstName());
            student.setMiddle_name(updateStudent.getMiddle_name());
            student.setGroup_number(updateStudent.getGroup_number());
            student.setStudent_faculty(updateStudent.getStudent_faculty());
            student.setDate_of_birth(updateStudent.getDate_of_birth());
            student.setEmail_adress(updateStudent.getEmail_adress());
            student.setDormitory_residence(updateStudent.isDormitory_residence());
            student.setDorm_block_number(updateStudent.getDorm_block_number());
            student.setDorm_number(updateStudent.getDorm_number());
            student.setStudent_full_name_d(updateStudent.getStudent_full_name_d());
            studentService.createStudent(student);
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студент с указанным айди не найден");
        }
    }

    @GetMapping("/search/by/last/name")
    public List<Student> getStudentsByLastName(@RequestParam("lastName") String lastName) {
        return studentService.getStudentsByLastName(lastName);
    }

    @GetMapping("/student/{studentId}/petition-events-count")
    public ResponseEntity<Integer> countEventsWithPetitionByStudentId(@PathVariable Long studentId) {
        int count = studentService.countEventsWithPetitionByStudentId(studentId);
        return ResponseEntity.ok(count);
    }

    @GetMapping("/eligible")
    public List<Student> getEligibleStudents() {
        return studentService.findEligibleStudents();
    }
}

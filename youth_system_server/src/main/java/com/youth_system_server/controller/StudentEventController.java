package com.youth_system_server.controller;

import com.youth_system_server.service.StudentEventService;
import com.youth_system_server.service.UserService;
import com.youth_system_server.service.interfaces.IStudentEventService;
import com.youth_system_server.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/se")
public class StudentEventController {
    @Autowired
    private IStudentEventService studentEventService;

    @Autowired
    private IUserService userService;

    @PostMapping("/{userId}/events/{eventId}")
    public void addEventToStudent(@PathVariable Long userId, @PathVariable Long eventId) {
        studentEventService.addEventToStudent(userId, eventId);
    }

    @DeleteMapping("/remove/student/{userId}/event/{eventId}")
    public void removeEventFromStudent(@PathVariable("userId") Long userId, @PathVariable("eventId") Long eventId) {
        Long studentId = userService.findStudentById(userId).getStudentId();
        studentEventService.removeEventFromStudent(studentId, eventId);
    }
}

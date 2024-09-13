package com.youth_system_server.service;

import com.youth_system_server.repository.StudentEventRepository;
import com.youth_system_server.service.interfaces.IStudentEventService;
import com.youth_system_server.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentEventService implements IStudentEventService {
    @Autowired
    private StudentEventRepository studentEventRepository;

    @Autowired
    private IUserService userService;

    @Override
    public void addEventToStudent(Long userId, Long eventId) {
        Long studentId = userService.findStudentById(userId).getStudentId();
        studentEventRepository.addEventToStudent(studentId, eventId);
    }

    @Override
    @Transactional
    public void removeEventFromStudent(Long studentId, Long eventId) {
        studentEventRepository.removeEventFromStudent(studentId, eventId);
    }
}

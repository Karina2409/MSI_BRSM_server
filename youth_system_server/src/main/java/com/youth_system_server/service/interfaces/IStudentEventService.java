package com.youth_system_server.service.interfaces;

public interface IStudentEventService {
    void addEventToStudent(Long userId, Long eventId);
    void removeEventFromStudent(Long studentId, Long eventId);
}


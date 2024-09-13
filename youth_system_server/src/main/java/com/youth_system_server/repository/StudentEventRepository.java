package com.youth_system_server.repository;

import com.youth_system_server.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

@Repository
public interface StudentEventRepository extends JpaRepository<Student, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO students_has_events (students_student_id, events_event_id) VALUES (:studentId, :eventId)", nativeQuery = true)
    void addEventToStudent(@Param("studentId") Long studentId, @Param("eventId") Long eventId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM students_has_events WHERE students_student_id = :studentId AND events_event_id = :eventId", nativeQuery = true)
    void removeEventFromStudent(@Param("studentId") Long studentId, @Param("eventId") Long eventId);

    @Query("SELECT COUNT(e) FROM Event e JOIN e.students s WHERE s.studentId = :studentId AND e.for_petition = true")
    int countEventsWithPetitionByStudentId(@Param("studentId") Long studentId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM students_has_events WHERE students_student_id = :studentId", nativeQuery = true)
    void deleteAllStudentEvents(@Param("studentId") Long studentId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM students_has_events WHERE events_event_id = :eventId", nativeQuery = true)
    void deleteByEventId(@Param("eventId") Long eventId);

}

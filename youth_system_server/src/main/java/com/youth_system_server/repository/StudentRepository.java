package com.youth_system_server.repository;

import com.youth_system_server.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s JOIN s.events e WHERE e.eventId = :eventId")
    List<Student> findStudentsByEventId(@Param("eventId") Long eventId);

    @Query("SELECT DISTINCT s FROM Student s JOIN s.events e WHERE e.eventDate >= :startDate")
    List<Student> findStudentsByEventDateAfter(@Param("startDate") Date startDate);

    @Query("SELECT SUM(e.opt_count) FROM Student s JOIN s.events e WHERE s.studentId = :studentId AND e.eventDate BETWEEN :startDate AND CURRENT_DATE ")
    Integer findOptCountByStudentIdAndEventDateAfter(@Param("studentId") Long studentId, @Param("startDate") Date startDate);

    List<Student> findByLastNameStartingWithIgnoreCase(String lastName);

    @Query("SELECT s.student_faculty, COUNT(s) FROM Student s JOIN s.events e " +
            "WHERE e.eventDate BETWEEN :startDate AND :endDate GROUP BY s.student_faculty")
    List<Object[]> countStudentsByFacultyBetweenDates(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}

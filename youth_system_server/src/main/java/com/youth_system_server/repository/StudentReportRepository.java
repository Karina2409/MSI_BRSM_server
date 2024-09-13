package com.youth_system_server.repository;

import com.youth_system_server.entity.Report;
import com.youth_system_server.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface StudentReportRepository  extends JpaRepository<Report, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO student_report (student_id, report_id, opt) VALUES (:studentId, :reportId, :optCount)", nativeQuery = true)
    void addStudentToReport(@Param("studentId") Long studentId, @Param("reportId") Long reportId, @Param("optCount") Integer optCount);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM student_report WHERE student_id = :studentId AND report_id = :reportId", nativeQuery = true)
    void removeStudentFromReport(@Param("studentId") Long studentId, @Param("reportId") Long reportId);

    @Query(value = "SELECT opt FROM student_report WHERE student_id = :studentId", nativeQuery = true)
    Integer findOptByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT s FROM Student s JOIN s.reports r WHERE r.reportId = :reportId")
    Set<Student> findStudentsByReportId(@Param("reportId") Long reportId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM student_report WHERE student_id = :studentId", nativeQuery = true)
    void deleteAllStudentReports(@Param("studentId") Long studentId);

}

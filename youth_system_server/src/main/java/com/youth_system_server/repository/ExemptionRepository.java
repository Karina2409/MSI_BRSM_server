package com.youth_system_server.repository;

import com.youth_system_server.entity.Exemption;
import com.youth_system_server.entity.FacultyEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExemptionRepository extends JpaRepository<Exemption, Long> {
    List<Exemption> findByStudentsFacultyExemption(FacultyEnum students_faculty);

    List<Exemption> findByEventNameStartingWithIgnoreCase(String eventName);
}

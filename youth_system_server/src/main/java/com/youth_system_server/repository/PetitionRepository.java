package com.youth_system_server.repository;

import com.youth_system_server.entity.FacultyEnum;
import com.youth_system_server.entity.Petition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetitionRepository extends JpaRepository<Petition, Long> {
    List<Petition> findPetitionByStudentFacultyPetition(FacultyEnum faculty);

    List<Petition> findPetitionByStudentLastNameStartingWithIgnoreCase(String studentLastName);
}

package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.FacultyEnum;
import com.youth_system_server.entity.Petition;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPetitionService {
    List<Petition> getAllPetitions();
    Petition savePetition(Long studentId);
    ResponseEntity<Void> deletePetitionById(Long id);
    List<Petition> getPetitionByFaculty(FacultyEnum faculty);
    void downloadPetition(Long petitionId);
    List<Petition> findPetitionsByStudentLastName(String studentLastName);
}

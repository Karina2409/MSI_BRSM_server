package com.youth_system_server.controller;

import com.youth_system_server.entity.Petition;
import com.youth_system_server.help.FacultyNumber;
import com.youth_system_server.service.interfaces.IPetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petitions")
public class PetitionController {

    @Autowired
    private IPetitionService petitionService;

    @GetMapping
    public List<Petition> getPetitions(){
        return petitionService.getAllPetitions();
    }

    @PostMapping("/post/{studentId}")
    public ResponseEntity<Petition> createPetition(@PathVariable("studentId") Long studentId){
        Petition savedPetition = petitionService.savePetition(studentId);
        return ResponseEntity.ok(savedPetition);
    }

    @DeleteMapping("/delete/{petitionId}")
    public ResponseEntity<Void> deletePetition(@PathVariable("petitionId") Long petitionId){
        return petitionService.deletePetitionById(petitionId);
    }

    @GetMapping("/faculty/{faculty}")
    public List<Petition> getPetitionsByFaculty(@PathVariable("faculty") Integer faculty){
        return petitionService.getPetitionByFaculty(FacultyNumber.getFacultyNameByNum(faculty));
    }

    @PostMapping("/download/{petitionId}")
    public void downloadPetition(@PathVariable Long petitionId){
        petitionService.downloadPetition(petitionId);
    }

    @GetMapping("/get/by/student/lastName")
    public List<Petition> getPetitionsByStudentLastName(@RequestParam String studentLastName) {
        return petitionService.findPetitionsByStudentLastName(studentLastName);
    }
}

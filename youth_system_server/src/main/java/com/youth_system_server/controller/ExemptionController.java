package com.youth_system_server.controller;

import com.youth_system_server.entity.Exemption;
import com.youth_system_server.help.FacultyNumber;
import com.youth_system_server.service.interfaces.IExemptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;


@RestController
@RequestMapping("/exemptions")
public class ExemptionController {

    @Autowired
    private IExemptionService exemptionService;

    @GetMapping
    public List<Exemption> getExemptions() {
        return exemptionService.getAllExemptions();
    }

    @PostMapping("/post/{eventId}")
    public ResponseEntity<Void> createExemption(@PathVariable("eventId") Long eventId,
                                                @RequestBody Map<String, Set<Long>> request) throws MissingServletRequestParameterException {
        Set<Long> studentIds = request.get("studentIds");
        if (studentIds == null || studentIds.isEmpty()) {
            throw new MissingServletRequestParameterException("studentIds", "Set<Long>");
        }
        exemptionService.saveExemption(eventId, studentIds);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{exemptionId}")
    public ResponseEntity<Void> deleteExemption(@PathVariable Long exemptionId) {
        return exemptionService.deleteExemptionById(exemptionId);
    }

    @GetMapping("/faculty/{faculty}")
    public List<Exemption> getExemptionsByFaculty(@PathVariable("faculty") Integer faculty) {
        return exemptionService.getExemptionsByFaculty(FacultyNumber.getFacultyNameByNum(faculty));
    }

    @PostMapping("/download/{exemptionId}")
    public void downloadExemption(@PathVariable Long exemptionId) {
        exemptionService.downloadExemption(exemptionId);
    }

    @GetMapping("/find/by/eventName")
    public List<Exemption> findByEventName(@RequestParam String eventName) {
        return exemptionService.getExemptionsByEventName(eventName);
    }
}

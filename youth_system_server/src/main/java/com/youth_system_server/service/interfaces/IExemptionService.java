package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.Exemption;
import com.youth_system_server.entity.FacultyEnum;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface IExemptionService {
    List<Exemption> getAllExemptions();
    void saveExemption(Long eventId, Set<Long> studentIds);
    ResponseEntity<Void> deleteExemptionById(Long exemption_id);
    List<Exemption> getExemptionsByFaculty(FacultyEnum faculty);
    void downloadExemption(Long exemptionId);
    List<Exemption> getExemptionsByEventName(String eventName);
}

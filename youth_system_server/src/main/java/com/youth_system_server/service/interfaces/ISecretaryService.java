package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.Secretary;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ISecretaryService {
    List<Secretary> findAllSecretaries();
    Secretary getSecretaryById(Long id);
    ResponseEntity<Void> deleteSecretaryById(Long secretary_id);
    Secretary createSecretary(Secretary secretary);
    byte[] getSecretaryImageById(Long id);
    void updateImageForSecretary(Long secretaryId, String imagePath);
}

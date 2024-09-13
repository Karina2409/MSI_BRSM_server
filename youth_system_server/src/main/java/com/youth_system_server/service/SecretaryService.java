package com.youth_system_server.service;

import com.youth_system_server.entity.Secretary;
import com.youth_system_server.repository.SecretaryRepository;
import com.youth_system_server.service.interfaces.ISecretaryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;
import java.util.Optional;

@Service
public class SecretaryService implements ISecretaryService {
    @Autowired
    private SecretaryRepository secretaryRepository;

    @Override
    public List<Secretary> findAllSecretaries(){
        return secretaryRepository.findAll();
    }

    @Override
    public Secretary getSecretaryById(Long id) {
        Optional<Secretary> optionalSecretary = secretaryRepository.findById(id);
        return optionalSecretary.orElse(null);
    }

    @Override
    public ResponseEntity<Void> deleteSecretaryById(Long secretary_id){
        Optional<Secretary> secretary = secretaryRepository.findById(secretary_id);
        if (secretary.isPresent()) {
            secretaryRepository.delete(secretary.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public Secretary createSecretary(Secretary secretary) {
        return secretaryRepository.save(secretary);
    }

    @Override
    public byte[] getSecretaryImageById(Long id) {
        Secretary secretary = secretaryRepository.findById(id).orElse(null);
        if (secretary != null && secretary.getImage() != null) {
            return secretary.getImage();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateImageForSecretary(Long secretaryId, String imagePath) {

        Secretary secretary = secretaryRepository.findById(secretaryId).orElse(null);
        if (secretary != null) {
            try {
                File imageFile = new File(imagePath);
                byte[] imageData = new byte[(int) imageFile.length()];
                FileInputStream fileInputStream = new FileInputStream(imageFile);
                fileInputStream.read(imageData);
                fileInputStream.close();

                secretary.setImage(imageData);

                secretaryRepository.save(secretary);

                System.out.println("Изображение добавлено к существующему секретарю!");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Секретарь с указанным идентификатором не найден!");
        }
    }
}

package com.youth_system_server.controller;

import com.youth_system_server.entity.Secretary;
import com.youth_system_server.service.SecretaryService;
import com.youth_system_server.service.interfaces.ISecretaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/secretaries")
public class SecretaryController {
    @Autowired
    private ISecretaryService secretaryService;

    @CrossOrigin(origins = "http://localhost:3001")
    @GetMapping
    public List<Secretary> getSecretaries() {
        return secretaryService.findAllSecretaries();
    }

    @GetMapping("/secretaries/{id}/image")
    public ResponseEntity<byte[]> getSecretaryImage(@PathVariable Long id) {
        byte[] imageData = secretaryService.getSecretaryImageById(id);
        if (imageData != null) {
            try {
                ByteArrayInputStream bis = new ByteArrayInputStream(imageData);

                BufferedImage bufferedImage = ImageIO.read(bis);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.IMAGE_JPEG);

                return ResponseEntity.ok()
                        .headers(headers)
                        .body(imageData);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{secretary_id}")
    public ResponseEntity<Void> deleteSecretary(@PathVariable Long secretary_id) {
        return secretaryService.deleteSecretaryById(secretary_id);
    }

    @PostMapping("/post")
    public ResponseEntity<Secretary> createSecretary(@RequestBody Secretary secretary) {
        Secretary createdSecretary = secretaryService.createSecretary(secretary);
        return ResponseEntity.ok(createdSecretary);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSecretaryById(@PathVariable Long id) {
        Secretary secretary = secretaryService.getSecretaryById(id);
        if (secretary != null) {
            return ResponseEntity.ok(secretary);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Секретарь с указанным айди не найден");
        }
    }

    @PutMapping("/secretary/{id}")
    public ResponseEntity<?> updateSecretary(@PathVariable Long id, @RequestBody Secretary updatedSecretary) {
        Secretary secretary = secretaryService.getSecretaryById(id);
        if (secretary != null) {
            secretary.setFirst_name(updatedSecretary.getFirst_name());
            secretary.setLast_name(updatedSecretary.getLast_name());
            secretary.setMiddle_name(updatedSecretary.getMiddle_name());
            secretary.setTelegram_username(updatedSecretary.getTelegram_username());
            secretaryService.createSecretary(secretary);
            return ResponseEntity.ok(secretary);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Секретарь с указанным айди не найден");
        }
    }

}

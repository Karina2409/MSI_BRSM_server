package com.youth_system_server.controller;

import com.youth_system_server.entity.User;
import com.youth_system_server.service.UserService;
import com.youth_system_server.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {
    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody User user) throws Exception {
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            String token = UUID.randomUUID().toString();

            userService.saveUserSession(registeredUser.getUser_id(), token);

            Map<String, Object> response = new HashMap<>();
            response.put("userId", registeredUser.getUser_id());
            response.put("token", token);
            response.put("role", registeredUser.getRole());

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Ошибка аутентификации"));
        }
    }
}

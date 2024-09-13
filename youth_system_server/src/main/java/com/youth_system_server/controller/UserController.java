package com.youth_system_server.controller;

import com.youth_system_server.entity.RoleEnum;
import com.youth_system_server.entity.Secretary;
import com.youth_system_server.entity.Student;
import com.youth_system_server.entity.User;
import com.youth_system_server.service.UserService;
import com.youth_system_server.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping
    public List<User> getUsers() {
        return userService.findAllUsers();
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int user_id) {
        return userService.deleteUserById(user_id);
    }

    @PostMapping("/post")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }

    @GetMapping("/info/{user_id}")
    public ResponseEntity<Student> getStudentByID(@PathVariable Long user_id) {
        Student student = userService.findStudentById(user_id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PatchMapping("/{userId}/role")
    public ResponseEntity<Void> changeUserRole(@PathVariable Long userId, @RequestBody Map<String, String> payload) {
        String role = payload.get("role");
        if (role == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            RoleEnum newRole = RoleEnum.valueOf(role);
            userService.changeUserRole(userId, newRole);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/student/{userId}")
    public ResponseEntity<Student> getStudent(@PathVariable Long userId) {

        Student student = userService.findStudentById(userId);
        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/secretary/{userId}")
    public ResponseEntity<Secretary> getSecretary(@PathVariable Long userId) {

        Secretary secretary = userService.findSecretaryById(userId);
        if (secretary != null) {
            return ResponseEntity.ok(secretary);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

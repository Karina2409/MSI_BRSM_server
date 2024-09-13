package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.RoleEnum;
import com.youth_system_server.entity.Secretary;
import com.youth_system_server.entity.Student;
import com.youth_system_server.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    List<User> findAllUsers();
    ResponseEntity<Void> deleteUserById(int user_id);
    User createUser(User user);
    User authenticateUser(User user);
    void saveUserSession(Long userId, String token);
    boolean isValidToken(Long userId, String token);
    User registerUser(User user) throws Exception;
    Student findStudentById(Long id);
    Secretary findSecretaryById(Long id);
    void changeUserRole(Long userId, RoleEnum newRole);
}

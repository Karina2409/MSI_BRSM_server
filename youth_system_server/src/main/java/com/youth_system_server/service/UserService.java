package com.youth_system_server.service;

import com.youth_system_server.entity.*;
import com.youth_system_server.help.ImageUtil;
import com.youth_system_server.repository.*;
import com.youth_system_server.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SecretaryRepository secretaryRepository;

    @Autowired
    private StudentEventRepository studentEventRepository;

    @Autowired
    private ExemptionStudentsRepository exemptionStudentsRepository;

    @Autowired
    private StudentReportRepository studentReportRepository;

    private Map<Long, String> userSessions = new ConcurrentHashMap<>();

    private final static String STATIC_IMAGE_PATH = "D:/универ/2курс/4семестр/Курсач/youth_system/public/BRSM.png";
    private final byte[] staticImage;

    public UserService() throws IOException {

        this.staticImage = ImageUtil.imageToByteArray(STATIC_IMAGE_PATH);
    }

    @Override
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @Override
    public ResponseEntity<Void> deleteUserById(int user_id) {
        Optional<User> user = userRepository.findById((long) user_id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User authenticateUser(User user) {
        User existingUser = userRepository.findByLogin(user.getLogin());
        if(existingUser != null && existingUser.getPassword().equals(user.getPassword())){
            return existingUser;
        }
        return null;
    }

    @Override
    public void saveUserSession(Long userId, String token) {
        userSessions.put(userId, token);
    }

    @Override
    public boolean isValidToken(Long userId, String token) {
        return token.equals(userSessions.get(userId));
    }

    @Override
    public User registerUser(User user) throws Exception{
        if(userRepository.findByLogin(user.getLogin()) != null){
            throw new Exception("Username already exists");
        }
        Student newStudent = new Student();
        studentRepository.save(newStudent);

        User newUser = new User();
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        newUser.setRole(RoleEnum.student);

        newUser.setStudent(newStudent);

        return userRepository.save(newUser);
    }

    @Override
    public Student findStudentById(Long id){
        User userP = userRepository.findById(id).orElse(null);
        if(userP != null){
            Student student = userP.getStudent();
            return student;
        }
        return null;
    }

    @Override
    public Secretary findSecretaryById(Long id){
        User userP = userRepository.findById(id).orElse(null);
        if(userP != null){
            Secretary secretary = userP.getSecretary();
            return secretary;
        }
        return null;
    }

    @Override
    @Transactional
    public void changeUserRole(Long userId, RoleEnum newRole) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (newRole == RoleEnum.secretary && user.getStudent() != null) {
            Student student = user.getStudent();

            studentEventRepository.deleteAllStudentEvents(student.getStudentId());
            exemptionStudentsRepository.deleteAllExemptionStudents(student.getStudentId());
            studentReportRepository.deleteAllStudentReports(student.getStudentId());

            studentRepository.delete(student);
            user.setStudent(null);

            Secretary secretary = new Secretary();
            secretary.setLast_name(student.getLastName());
            secretary.setFirst_name(student.getFirstName());
            secretary.setMiddle_name(student.getMiddle_name());
            secretary.setSecretary_faculty(student.getStudent_faculty());
            secretary.setImage(staticImage);
            secretary.setTelegram_username("@brsm_bsuir");
            secretaryRepository.save(secretary);

            user.setSecretary(secretary);
        }else if (newRole == RoleEnum.student && user.getSecretary() != null) {
            Secretary secretary = user.getSecretary();

            Student student = new Student();
            student.setLastName(secretary.getLast_name());
            student.setFirstName(secretary.getFirst_name());
            student.setMiddle_name(secretary.getMiddle_name());
            student.setStudent_faculty(secretary.getSecretary_faculty());
            studentRepository.save(student);

            secretaryRepository.delete(secretary);
            user.setSecretary(null);
            user.setStudent(student);
        }

        user.setRole(newRole);
        userRepository.save(user);
    }
}

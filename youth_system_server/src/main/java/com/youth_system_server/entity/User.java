package com.youth_system_server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long user_id;

    @Column(unique = true)
    private String login;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private RoleEnum role;

    @Column(name="register_date")
    private Date registerDate;

    @OneToOne
    @JoinColumn(name = "students_student_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "secretaries_secretary_id")
    private Secretary secretary;
}

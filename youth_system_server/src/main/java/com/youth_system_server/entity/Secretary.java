package com.youth_system_server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "secretaries")
public class Secretary {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column
    private Long secretary_id;
    @Column
    private String last_name;
    @Column
    private String first_name;
    @Column
    private String middle_name;
    @Enumerated(EnumType.STRING)
    @Column
    private FacultyEnum secretary_faculty;
    @Column
    private String telegram_username;
    @Column
    private byte[] image;
}
package com.youth_system_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name="petition")
public class Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "petition_id")
    private Long petitionId;

    @Column
    private String petition_name;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date petition_date;

    @Column(name = "student_faculty")
    @Enumerated(EnumType.STRING)
    private FacultyEnum studentFacultyPetition;

    @Column(name="student_name")
    private String studentLastName;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}

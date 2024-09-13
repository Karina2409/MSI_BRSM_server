package com.youth_system_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name="students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    @ManyToMany
    @JoinTable(
            name = "students_has_events",
            joinColumns = @JoinColumn(name = "students_student_id"),
            inverseJoinColumns = @JoinColumn(name = "events_event_id")
    )
    private Set<Event> events;

    @ManyToMany(mappedBy = "students")
    private Set<Exemption> exception;

    @Column(name="student_full_name_d")
    private String student_full_name_d;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="first_name")
    private String firstName;

    @Column()
    private String middle_name;

    @Column
    private String group_number;

    @Enumerated(EnumType.STRING)
    @Column
    private FacultyEnum student_faculty;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Minsk")
    private Date date_of_birth;

    @Column
    private String email_adress;

    @Column
    private boolean dormitory_residence;

    @Column
    private String dorm_block_number;

    @Column
    private Integer dorm_number;

    @ManyToMany
    @JoinTable(
            name = "student_report",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "report_id")
    )
    private Set<Report> reports;
}

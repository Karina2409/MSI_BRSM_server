package com.youth_system_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name="exemption")
public class Exemption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exemption_id")
    private Long exemptionId;

    @Column
    private String exemption_name;

    @Column(columnDefinition = "DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date exemption_date;

    @Column(name = "students_faculty")
    @Enumerated(EnumType.STRING)
    private FacultyEnum studentsFacultyExemption;

    @Column(name="event_name")
    private String eventName;

    @ManyToMany
    @JoinTable(
            name = "exemption_students",
            joinColumns = @JoinColumn(name = "exemption_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
}

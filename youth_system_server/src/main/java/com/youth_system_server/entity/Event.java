package com.youth_system_server.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Time;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Data
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToMany(mappedBy = "events")
    private Set<Student> students;

    @Column
    private String eventName;
    @Column(columnDefinition = "DATE", name = "event_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Minsk")
    private Date eventDate;
    @Column(columnDefinition = "TIME")
    @JsonFormat(pattern = "HH:mm:ss", timezone = "Europe/Minsk")
    private Time event_time;
    @Column
    private String event_place;
    @Column
    private int student_count;
    @Column
    private int opt_count;
    @Column
    private boolean for_petition;

    @OneToMany(mappedBy = "event")
    private Set<Exemption> exemption = new HashSet<>();

    public Event() {

    }
}

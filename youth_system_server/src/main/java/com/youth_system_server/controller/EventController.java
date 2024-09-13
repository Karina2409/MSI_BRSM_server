package com.youth_system_server.controller;

import com.youth_system_server.entity.Event;
import com.youth_system_server.entity.FacultyEnum;
import com.youth_system_server.entity.Student;
import com.youth_system_server.service.EventService;
import com.youth_system_server.service.StudentService;
import com.youth_system_server.service.interfaces.IEventService;
import com.youth_system_server.service.interfaces.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private IEventService eventService;

    @Autowired
    private IStudentService studentService;

    @GetMapping
    public List<Event> getEvents() {
        return eventService.findAllEvents();
    }

    @GetMapping("/{event_id}")
    public Event getEvent(@PathVariable Long event_id){
        return eventService.findById(event_id);
    }

    @DeleteMapping("/delete/{event_id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long event_id) {
        return eventService.deleteEventById(event_id);
    }

    @PostMapping("/post")
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);
    }

    @GetMapping("/{eventId}/students")
    public List<Student> getStudentsByEventId(@PathVariable Long eventId) {
        return studentService.getStudentsByEventId(eventId);
    }

    @PutMapping("/event/update/{eventId}")
    public ResponseEntity<?> updateEvent(@PathVariable Long eventId, @RequestBody Event updateEvent) {
        Event event = eventService.findById(eventId);
        if (event != null) {
            Date currentDate = new Date();
            if (event.getEventDate().after(currentDate)) {
                event.setEventName(updateEvent.getEventName());
                event.setEventDate(updateEvent.getEventDate());
                event.setEvent_time(updateEvent.getEvent_time());
                event.setEvent_place(updateEvent.getEvent_place());
                event.setStudent_count(updateEvent.getStudent_count());
                event.setOpt_count(updateEvent.getOpt_count());
                event.setFor_petition(updateEvent.isFor_petition());
                eventService.createEvent(event);
                return ResponseEntity.ok(event);
            } else {
                return ResponseEntity.badRequest().build();
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Мероприятие с указанным айди не найдено");
        }
    }

    @GetMapping("/upcoming")
    public List<Event> getUpcomingEventsWithAvailableSlots() {
        return eventService.getUpcomingEventsWithAvailableSlots();
    }

    @GetMapping("/byDate")
    public List<Event> getEventsByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventDate) {
        return eventService.getEventsByDate(eventDate);
    }

    @GetMapping("/byName")
    public List<Event> getEventsByName(@RequestParam("name") String eventName) {
        return eventService.getEventsByName(eventName);
    }

    @GetMapping("/get/events/with/petition/student/{studentId}")
    public List<Event> getEventsWithPetition(@PathVariable Long studentId){
        return eventService.getEventByStudentIdPetition(studentId);
    }

    @GetMapping("/past")
    public ResponseEntity<List<Event>> getPastEvents() {
        List<Event> pastEvents = eventService.getPastEvents();
        return ResponseEntity.ok(pastEvents);
    }

    @GetMapping("/eventStatistics")
    public Map<FacultyEnum, Long> getEventStatistics(@RequestParam String period) {
        Date[] dateRange = eventService.getDateRange(period);
        return eventService.countStudentsByFacultyBetweenDates(dateRange[0], dateRange[1]);
    }
}


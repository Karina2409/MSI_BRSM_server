package com.youth_system_server.service.interfaces;

import com.youth_system_server.entity.Event;
import com.youth_system_server.entity.FacultyEnum;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IEventService {
    List<Event> findAllEvents();
    ResponseEntity<Void> deleteEventById(Long event_id);
    Event createEvent(Event event);
    Event findById(long id);
    List<Event> getEventsByStudentId(Long studentId);
    List<Event> getUpcomingEventsWithAvailableSlots();
    List<Event> getEventsByDate(Date eventDate);
    List<Event> getEventsByName(String eventName);
    List<Event> getEventByStudentIdPetition(Long studentId);
    List<Event> getPastEvents();
    Map<FacultyEnum, Long> countStudentsByFacultyBetweenDates(Date startDate, Date endDate);
    Date[] getDateRange(String period);
}

package com.youth_system_server.pdf;

import com.youth_system_server.entity.Event;
import com.youth_system_server.entity.FacultyEnum;
import com.youth_system_server.help.DateFormat;
import com.youth_system_server.repository.EventRepository;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ExemptionTemplate {

    private final EventRepository eventRepository;

    public ExemptionTemplate(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public String generateContent(StringBuilder studentsInfo, Long eventId) {

        Event event = eventRepository.findById(eventId).get();

        String eventName = event.getEventName();
        Date eventDate = event.getEventDate();

        return  "Прошу пропуски студента " + studentsInfo + " " + DateFormat.DateDotFormat(eventDate) +
                " считать по уважительной причине в связи с тем, что он принимал участие в " +
                eventName + ".\n\n\n\n\n\n\n\n";
    }

    public String generateHeader(FacultyEnum faculty, String recipient) {
        return "Декану " + faculty + "\n" + recipient;
    }

    public String generateBeforeContent(){
        return "\n\n\n\n" +
                "ДОКЛАДНАЯ ЗАПИСКА" + "\n" +
                DateFormat.DateDotFormat(new Date()) + "\n\n\n";
    }
}

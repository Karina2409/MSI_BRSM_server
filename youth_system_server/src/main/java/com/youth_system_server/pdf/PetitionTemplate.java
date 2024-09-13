package com.youth_system_server.pdf;

import com.youth_system_server.entity.Event;
import com.youth_system_server.entity.Student;
import com.youth_system_server.help.DateFormat;
import com.youth_system_server.repository.EventRepository;
import com.youth_system_server.repository.StudentRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PetitionTemplate {
    private final StudentRepository studentRepository;
    private final EventRepository eventRepository;

    public PetitionTemplate(StudentRepository studentRepository, EventRepository eventRepository) {
        this.studentRepository = studentRepository;
        this.eventRepository = eventRepository;
    }

    public String generateContent(Long studentId) {

        Student student = studentRepository.findById(studentId).get();

        List<Event> events = eventRepository.findPetitionEventsByStudentId(studentId);

        StringBuilder eventsStudent = new StringBuilder();

        for(Event event : events){
            String eventStudent = "\n"+DateFormat.DateDotFormat(event.getEventDate()) + " – "
                    + event.getEventName() + ".";
            eventsStudent.append(eventStudent);
        }

        return "Прошу Вас рассмотреть возможность предоставления общежития студенту гр. "
                + student.getGroup_number() + " " + student.getLastName() + " "
                + student.getFirstName() + " " + student.getMiddle_name() + "."
                + "\n" + student.getLastName() + " " + student.getFirstName() + " "
                + "активист ПО ОО «БРСМ» с правами РК БГУИР, является членом БРСМ. "
                + "В работе зарекомендовал себя как исполнительный и ответственный активист."
                + "\n" + "В период за 2023-2024 учебный год " + student.getLastName() + " "
                + student.getFirstName() + " участвовал в подготовке "
                + "и организации мероприятий, принимал активное участие в патриотических акциях, "
                + "возложениях, мероприятиях, посвященных памятным датам, выполнял разовые поручения."
                + eventsStudent;
    }

    public String generateBeforeContent(){
        return "\n\n\n";
    }
}

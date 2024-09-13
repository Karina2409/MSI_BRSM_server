package com.youth_system_server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youth_system_server.entity.Event;
import com.youth_system_server.service.StudentEventService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentEventControllerTests.class)
@AutoConfigureMockMvc
public class StudentEventControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentEventService studentEventService;

    @Autowired
    private ObjectMapper objectMapper;



//    @Test
//    public void testGetEvents() throws Exception {
//        List<Event> events = Arrays.asList(new Event(), new Event());
//        when(eventService.findAllEvents()).thenReturn(events);
//
//        mockMvc.perform(MockMvcRequestBuilders.get("/events")
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json("[{}, {}]"));
//    }

//    @PostMapping("/{userId}/events/{eventId}")
//    public void addEventToStudent(@PathVariable Long userId, @PathVariable Long eventId) {
//        studentEventService.addEventToStudent(userId, eventId);
//    }
}

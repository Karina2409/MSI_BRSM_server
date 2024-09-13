package com.youth_system_server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youth_system_server.controller.EventController;
import com.youth_system_server.controller.StudentEventControllerTests;
import com.youth_system_server.entity.*;
import com.youth_system_server.help.FacultyNumber;
import com.youth_system_server.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@WebMvcTest(StudentEventControllerTests.class)
class YouthSystemServerApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ExemptionService exemptionService;
	@MockBean
	private EventService eventService;
	@MockBean
	private StudentService studentService;
	@MockBean
	private StudentEventService studentEventService;
	@InjectMocks
	private EventController eventController;
	@MockBean
	private PetitionService petitionService;
	@MockBean
	private ReportService reportService;
	@MockBean
	private UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testAddEventToStudent() throws Exception {
		Long userId = 1L;
		Long eventId = 2L;
		Mockito.doNothing().when(studentEventService).addEventToStudent(userId, eventId);
		mockMvc.perform(MockMvcRequestBuilders.post("/se/{userId}/events/{eventId}", userId, eventId));
	}

	@Test
	public void testGetEvents() throws Exception {
		List<Event> events = Arrays.asList(new Event(), new Event());
		when(eventService.findAllEvents()).thenReturn(events);
		mockMvc.perform(MockMvcRequestBuilders.get("/events")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("[{}, {}]"));
	}

	@Test
	public void testGetEventsByStudentId() throws Exception {
		List<Event> events = Arrays.asList(new Event(), new Event());
		when(eventService.getEventsByStudentId(anyLong())).thenReturn(events);
		mockMvc.perform(get("/se/1/events")
				.accept(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testCreatePetition() throws Exception {
		Long studentId = 1L;
		Petition petition = new Petition();
		petition.setPetitionId(1L);
		Mockito.when(petitionService.savePetition(anyLong())).thenReturn(petition);
		mockMvc.perform(post("/petitions/post/{studentId}", studentId)
						.contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void testDeletePetition() throws Exception {
		Long petitionId = 1L;
		Mockito.when(petitionService.deletePetitionById(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		mockMvc.perform(delete("/petitions/delete/{petitionId}", petitionId));
	}

	@Test
	public void testCreateReport() throws Exception {
		Set<Report> reports = new HashSet<>(Collections.singletonList(new Report()));
		Mockito.when(reportService.saveReport()).thenReturn(new ResponseEntity<>(reports, HttpStatus.OK));
		mockMvc.perform(post("/reports/post/month"));
	}

	@Test
	public void testDeleteReport() throws Exception {
		Long reportId = 1L;
		Mockito.when(reportService.deleteReportById(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		mockMvc.perform(delete("/reports/delete/{reportId}", reportId));
	}

	@Test
	public void testCreateEvent() throws Exception {
		Event event = new Event();
		when(eventService.createEvent(any(Event.class))).thenReturn(event);
		mockMvc.perform(MockMvcRequestBuilders.post("/events/post")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isOk())
				.andExpect(content().json("{}"));
	}

	@Test
	public void testDeleteEvent() throws Exception {
		when(eventService.deleteEventById(anyLong())).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		mockMvc.perform(MockMvcRequestBuilders.delete("/events/delete/1"))
				.andExpect(status().isOk());
	}

	@Test
	public void testUpdateEvent() throws Exception {
		Event event = new Event();
		event.setEventDate(new Date(System.currentTimeMillis() + 100000));
		when(eventService.findById(anyLong())).thenReturn(event);
		when(eventService.createEvent(any(Event.class))).thenReturn(event);
		mockMvc.perform(MockMvcRequestBuilders.put("/events/event/update/1")
						.contentType(MediaType.APPLICATION_JSON)
						.content("{}"))
				.andExpect(status().isOk())
				.andExpect(content().json("{}"));
	}

	@Test
	public void testGetStudentsByEventId() throws Exception {
		List<Student> students = Arrays.asList(new Student(), new Student());
		when(studentService.getStudentsByEventId(anyLong())).thenReturn(students);
		mockMvc.perform(MockMvcRequestBuilders.get("/events/1/students")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().json("[{}, {}]"));
	}

	@Test
	public void testCreateExemption() throws Exception {
		Map<String, Set<Long>> request = new HashMap<>();
		request.put("studentIds", Set.of(1L, 2L, 3L));
		mockMvc.perform(post("/exemptions/post/1")
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"studentIds\": [1, 2, 3]}"));
	}

	@Test
	public void testDeleteExemption() throws Exception {
		Mockito.when(exemptionService.deleteExemptionById(anyLong())).thenReturn(ResponseEntity.ok().build());
		mockMvc.perform(delete("/exemptions/delete/1"));
	}

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
	}

	@Test
	public void testChangeUserRoleToStudent() throws Exception {
		Long userId = 1L;
		Map<String, String> payload = new HashMap<>();
		payload.put("role", "student");
		Mockito.doNothing().when(userService).changeUserRole(anyLong(), any(RoleEnum.class));
		mockMvc.perform(patch("/users/{userId}/role", userId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"role\":\"student\"}"));
	}

	@Test
	public void testChangeUserRoleToSecretary() throws Exception {
		Long userId = 2L;
		Map<String, String> payload = new HashMap<>();
		payload.put("role", "secretary");
		Mockito.doNothing().when(userService).changeUserRole(anyLong(), any(RoleEnum.class));
		mockMvc.perform(patch("/users/{userId}/role", userId)
						.contentType(MediaType.APPLICATION_JSON)
						.content("{\"role\":\"secretary\"}"));
	}
}
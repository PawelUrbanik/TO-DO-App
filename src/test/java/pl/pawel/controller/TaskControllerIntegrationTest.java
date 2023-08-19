package pl.pawel.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;

import java.time.LocalDateTime;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void httGet_returnsGivenTask() throws Exception {
        //given
        final long id = taskRepository.save(new Task("Foo", LocalDateTime.now())).getId();

        //when + then
        mockMvc.perform(get("/tasks/" + id))
                .andExpect(status().is2xxSuccessful());
    }


    @Test
    void httpGet_returnsAllTasks() throws Exception {
        //given
        taskRepository.save(new Task("Foo", LocalDateTime.now())).getId();
        taskRepository.save(new Task("Foo2", LocalDateTime.now())).getId();

        //when + then
        mockMvc.perform(get("/tasks/"))
                .andExpect(jsonPath("$[0].description", is("Foo")))
                .andExpect(jsonPath("$[1].description", is("Foo2")))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void httpPost_createTask() throws Exception {
        //given
        Task taskToSave = new Task("Bar", LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestBody = objectWriter.writeValueAsString(taskToSave);

        //when
        mockMvc.perform(post("/tasks/").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        //then
        mockMvc.perform(get("/tasks/"))
                .andExpect(jsonPath("$[0].description", is("Bar")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void httpPost_throwBadRequest() throws Exception {
        mockMvc.perform(post("/tasks")).andExpect(status().isBadRequest());
    }

    @Test
    void httpPut_changeTask() throws Exception {
        //given
        Task taskToSave = new Task("Bar", LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new JavaTimeModule());
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestBody = objectWriter.writeValueAsString(taskToSave);

        //when
        final MvcResult result = mockMvc.perform(post("/tasks/").contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        Task saved = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Task>() {});
        saved.setDescription("Updated");
        String updatedRequestBody = objectWriter.writeValueAsString(saved);
        mockMvc.perform(put("/tasks/{id}", saved.getId()).contentType(MediaType.APPLICATION_JSON)
                        .content(updatedRequestBody))
                .andExpect(status().isNoContent());
        //then
        mockMvc.perform(get("/tasks/"))
                .andExpect(jsonPath("$[0].description", is("Updated")))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
}

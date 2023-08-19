package pl.pawel.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;


import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(TaskController.class)
public class TaskControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @Test
    public void httGet_returnsGivenTasks() throws Exception {
        String description = "foo";
        //given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.ofNullable(new Task(description, LocalDateTime.now())));

        mockMvc.perform(
                get("/tasks/123"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString(description)));
    }
}

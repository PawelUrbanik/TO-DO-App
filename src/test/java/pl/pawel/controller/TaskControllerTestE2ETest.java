package pl.pawel.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pawel.model.Task;
import pl.pawel.repository.TaskRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTestE2ETest {

    @LocalServerPort
    private int port;
    @Autowired
    TaskRepository taskRepository;

    private WebClient webClient;

    @Test
    void httpGetReturnsAllTasks() {
        //given
        webClient= WebClient.create("http://localhost:"+port);

        final int size = taskRepository.findAll().size();
        taskRepository.save(new Task("Foo", LocalDateTime.now()));
        taskRepository.save(new Task("Bar", LocalDateTime.now()));
        //when
        final Flux<Task> taskFlux = webClient.get()
                .uri("/tasks")
                .retrieve()
                .bodyToFlux(Task.class);



        //then
        assertThat(taskFlux.toStream().count()).isEqualTo(size +2);
    }



}
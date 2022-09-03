package pl.pawel.model;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import pl.pawel.repository.TaskRepository;

@Configuration
public class TestConfiguration {

    @Bean
    @Profile("integration")
    TaskRepository testRepo() {
        return new TestTaskRepository();
    }
}

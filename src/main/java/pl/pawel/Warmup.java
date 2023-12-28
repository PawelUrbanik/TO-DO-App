package pl.pawel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import pl.pawel.model.Task;
import pl.pawel.model.TaskGroups;
import pl.pawel.repository.TaskGroupRepository;

import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    private final TaskGroupRepository groupRepository;

    public Warmup(TaskGroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after refreshed");
        String description = "ApplicationContextEvent";
        if (!groupRepository.existsByDescription(description)) {
            logger.info("No required group found! Adding it!");
            final TaskGroups taskGroups = new TaskGroups();
            taskGroups.setDescription(description);
            taskGroups.setTasks(
                    Set.of(
                            new Task("ApplicationClosedEvent", null, taskGroups),
                            new Task("ApplicationStoppedEvent", null, taskGroups),
                            new Task("ApplicationStartedEvent", null, taskGroups)
                    )
            );

            groupRepository.save(taskGroups);
        };

    }
}

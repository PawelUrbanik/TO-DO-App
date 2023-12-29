package pl.pawel.reports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.pawel.event.TaskDone;
import pl.pawel.event.TaskEvent;
import pl.pawel.event.TaskUndone;

@Service
public class ChangedTaskEventListener {

    public static final Logger logger = LoggerFactory.getLogger(ChangedTaskEventListener.class);

    private final PersistedTaskEventsRepository persistedTaskEventsRepository;
    public ChangedTaskEventListener(PersistedTaskEventsRepository persistedTaskEventsRepository) {
        this.persistedTaskEventsRepository = persistedTaskEventsRepository;
    }

    @EventListener
    public void on(TaskDone event) {
        onChanged(event);
    }

    @EventListener
    public void on(TaskUndone event) {
        onChanged(event);
    }

    private void onChanged(TaskEvent event) {
        logger.info("Got " + event);
        persistedTaskEventsRepository.save(new PersistedTaskEvents(event));
    }
}

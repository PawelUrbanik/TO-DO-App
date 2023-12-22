package pl.pawel.model.projection;

import pl.pawel.model.Task;

public class GroupTaskReadModel {

    private boolean done;
    private String description;

    public GroupTaskReadModel(Task task) {
        this.description = task.getDescription();
        this.done= task.isDone();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String descriiption) {
        this.description = descriiption;
    }


}

package pl.pawel.model.projection;

import pl.pawel.model.Task;

public class GroupTaskReadModel {

    private boolean done;
    private String descriiption;

    public GroupTaskReadModel(Task task) {
        this.descriiption = task.getDescription();
        this.done= task.isDone();
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getDescriiption() {
        return descriiption;
    }

    public void setDescriiption(String descriiption) {
        this.descriiption = descriiption;
    }


}

package game.content.phantasye.skill.slayer.task;

import java.util.ArrayList;
import java.util.List;

public class TaskPool {

    private final List<SlayerTask> taskPool;

    public TaskPool() {
        this.taskPool = new ArrayList<>();
    }

    public List<SlayerTask> getTaskPool() {
        return taskPool;
    }
}

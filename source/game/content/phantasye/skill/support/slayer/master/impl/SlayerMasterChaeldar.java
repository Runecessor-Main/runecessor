package game.content.phantasye.skill.support.slayer.master.impl;

import game.content.phantasye.skill.support.slayer.master.SlayerMaster;
import game.content.phantasye.skill.support.slayer.master.data.Chaeldar;
import game.content.phantasye.skill.support.slayer.task.SlayerTask;

import java.util.Arrays;

public class SlayerMasterChaeldar extends SlayerMaster {

    public static final int ID = 404;

    public SlayerMasterChaeldar() {
        super(ID, 70,10);
        Arrays.stream(Chaeldar.values()).forEach(task -> this.getTaskList().add(
                new SlayerTask(task.getAssignment(), task.getCount(), task.getExtended(), task.getWeight())
        ));
    }
}

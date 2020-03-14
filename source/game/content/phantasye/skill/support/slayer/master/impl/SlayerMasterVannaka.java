package game.content.phantasye.skill.support.slayer.master.impl;

import game.content.phantasye.skill.support.slayer.master.SlayerMaster;
import game.content.phantasye.skill.support.slayer.master.data.Vannaka;
import game.content.phantasye.skill.support.slayer.task.SlayerTask;

import java.util.Arrays;

public class SlayerMasterVannaka extends SlayerMaster {

    public static final int ID = 403;

    public SlayerMasterVannaka() {
        super(ID, 40,4);
        Arrays.stream(Vannaka.values()).forEach(task -> this.getTaskList().add(
                new SlayerTask(task.getAssignment(), task.getCount(), task.getExtended(), task.getWeight())
        ));
    }
}

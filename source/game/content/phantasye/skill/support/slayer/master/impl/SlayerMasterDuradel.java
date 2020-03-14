package game.content.phantasye.skill.support.slayer.master.impl;

import game.content.phantasye.skill.support.slayer.master.SlayerMaster;
import game.content.phantasye.skill.support.slayer.master.data.Duradel;
import game.content.phantasye.skill.support.slayer.task.SlayerTask;

import java.util.Arrays;

public class SlayerMasterDuradel extends SlayerMaster {

    public static final int ID = 405;

    public SlayerMasterDuradel() {
        super(ID, 99,15);
        Arrays.stream(Duradel.values()).forEach(task -> this.getTaskList().add(
                new SlayerTask(task.getAssignment(), task.getCount(), task.getExtended(), task.getWeight())
        ));
    }
}

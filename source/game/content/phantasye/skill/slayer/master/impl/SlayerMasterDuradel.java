package game.content.phantasye.skill.slayer.master.impl;

import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.content.phantasye.skill.slayer.master.data.Duradel;
import game.content.phantasye.skill.slayer.task.SlayerTask;

import java.util.Arrays;

public class SlayerMasterDuradel extends SlayerMaster {

    public static final int ID = 405;

    public SlayerMasterDuradel() {
        super(ID, 99,15);
        this.getOptions().add("Boss Task");
        this.getOptions().add("Social Slayer");
        Arrays.stream(Duradel.values()).forEach(task -> this.getTaskList().add(
                new SlayerTask(task.getAssignment(), task.getCount(), task.getExtended(), task.getWeight())
        ));
    }
}

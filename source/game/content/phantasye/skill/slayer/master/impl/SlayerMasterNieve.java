package game.content.phantasye.skill.slayer.master.impl;

import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.content.phantasye.skill.slayer.master.data.Nieve;
import game.content.phantasye.skill.slayer.task.SlayerTask;

import java.util.Arrays;

public class SlayerMasterNieve extends SlayerMaster {

    public static final int ID = 6797;

    public SlayerMasterNieve() {
        super(ID, 75,12);
        this.getOptions().add("Create Zamorakian Hasta");
        Arrays.stream(Nieve.values()).forEach(task -> this.getTaskList().add(
                new SlayerTask(task.getAssignment(), task.getCount(), task.getExtended(), task.getWeight())
        ));
    }
}

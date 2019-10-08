package game.content.phantasye.skill.slayer.master.assignment;

import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.content.phantasye.skill.slayer.master.SlayerMasterFactory;
import game.content.phantasye.skill.slayer.master.assignment.impl.*;
import game.content.phantasye.skill.slayer.task.TaskGenerator;
import game.player.Player;

public final class SoloSlayerAssignmentChain {

    private final AssignmentChain base;
    private final Player player;
    private final SlayerMaster master;

    public SoloSlayerAssignmentChain(Player player, SlayerMaster master) {
        this.base = new CheckLevelAssignmentChain(player);
        this.player = player;
        this.master = master;
        final AssignmentChain c2 = new CheckTaskAssignmentChain(player);
        final AssignmentChain c3 = new CheckBlockedTaskAssignmentChain(player);
        final AssignmentChain c4 = new CheckTaskLevelAssignmentChain(player);
        final AssignmentChain c5 = new AssignTaskAssignmentChain(player);

        base.setNextChain(c2);
        c2.setNextChain(c3);
        c3.setNextChain(c4);
        c4.setNextChain(c5);
    }

    public SoloSlayerAssignmentChain(Player player,int master) {
        this(player, SlayerMasterFactory.getSlayerMaster(master).orElse(null));
    }

    public void execute() {
        try {
            base.assignTask(TaskGenerator.generateSlayerTaskFor(player, master));
        } catch (NullPointerException e) {
            this.execute();
            e.printStackTrace();
        }
    }
}

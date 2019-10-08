package game.content.phantasye.skill.slayer.master.assignment;

import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.content.phantasye.skill.slayer.master.SlayerMasterFactory;
import game.content.phantasye.skill.slayer.master.assignment.impl.*;
import game.content.phantasye.skill.slayer.task.TaskGenerator;
import game.player.Player;

public class DuoPlayerAssignmentChain {

    private final AssignmentChain base;
    private final Player player;
    private final SlayerMaster master;

    public DuoPlayerAssignmentChain(Player player, SlayerMaster master) {
        this.base = new CheckLevelAssignmentChain(SlayerSkill.getWeakestMemberFromDuo(player));
        this.player = player;
        this.master = master;

        final AssignmentChain c2 = new CheckTaskAssignmentChain(SlayerSkill.getWeakestMemberFromDuo(player));
        final AssignmentChain c3 = new CheckBlockedTaskAssignmentChain(player);
        final AssignmentChain c4 = new CheckTaskLevelAssignmentChain(SlayerSkill.getWeakestMemberFromDuo(player));
        final AssignmentChain c5 = new AssignTaskAssignmentChain(player);
        final AssignmentChain c6 = new AssignTaskAssignmentChain(player.getSlayerPartner());

        base.setNextChain(c2);
        c2.setNextChain(c3);
        c3.setNextChain(c4);
        c4.setNextChain(c5);
        c5.setNextChain(c6);
    }

    public DuoPlayerAssignmentChain(Player player,int master) {
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

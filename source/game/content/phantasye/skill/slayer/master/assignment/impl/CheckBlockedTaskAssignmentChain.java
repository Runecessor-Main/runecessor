package game.content.phantasye.skill.slayer.master.assignment.impl;

import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.master.assignment.AbstractAssignmentChainBase;
import game.content.phantasye.skill.slayer.master.assignment.DuoPlayerAssignmentChain;
import game.content.phantasye.skill.slayer.master.assignment.SoloSlayerAssignmentChain;
import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;
import game.player.Player;

import java.util.Objects;

public class CheckBlockedTaskAssignmentChain extends AbstractAssignmentChainBase {

    public CheckBlockedTaskAssignmentChain(Player player) {
        super(player);
    }

    @Override
    public void assignTask(PlayerSlayerTask task) {
        if (!this.getPlayer().getPlayerDetails().getBlockedTasks().contains(Objects.requireNonNull(task).getAssignment())) {
            this.getNextChain().assignTask(task);
        } else {
            if(SlayerSkill.isDoingDuoSlayer(this.getPlayer())) {
                new DuoPlayerAssignmentChain(this.getPlayer(),task.getMaster());
            } else {
                new SoloSlayerAssignmentChain(this.getPlayer(),task.getMaster());
            }
        }
    }
}

package game.content.phantasye.skill.slayer.master.assignment.impl;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.master.assignment.AbstractAssignmentChainBase;
import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;
import game.player.Player;

public class AssignTaskAssignmentChain extends AbstractAssignmentChainBase {

    public AssignTaskAssignmentChain(Player player) {
        super(player);
    }

    @Override
    public void assignTask(PlayerSlayerTask task) {
        this.getPlayer().getPlayerDetails().setSlayerTask(task);
        this.getPlayer().receiveMessage("You've been assigned to slay "
                + task.getAmount().value()
                + " "
                + SlayerAssignment.values()[task.getAssignment()].toString()
                + ".");
        this.getPlayer().saveDetails();
        if(this.getNextChain() != null) {
            this.getNextChain().assignTask(task);
        }
    }
}

package game.content.phantasye.skill.slayer.master.assignment.impl;

import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.master.assignment.AbstractAssignmentChainBase;
import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;
import game.player.Player;

public class CheckTaskAssignmentChain extends AbstractAssignmentChainBase {

    public CheckTaskAssignmentChain(Player player) {
        super(player);
    }

    @Override
    public void assignTask(PlayerSlayerTask task) {
        if (this.getPlayer().getPlayerDetails().getSlayerTask() == null) {
            if (!SlayerSkill.isDoingDuoSlayer(this.getPlayer())) {
                this.getNextChain().assignTask(task);
            } else if(this.getPlayer().getSlayerPartner().getPlayerDetails().getSlayerTask() != null) {
                this.getPlayer().receiveMessage("Your partner has not finished the task. Please leave the group or wait.");
            } else {
                this.getNextChain().assignTask(task);
            }
        } else {
            this.getPlayer().receiveMessage("Please complete, or cancel your current Slayer task.");
        }
    }
}

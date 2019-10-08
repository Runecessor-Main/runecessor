package game.content.phantasye.skill.slayer.master.assignment.impl;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.content.phantasye.skill.slayer.master.SlayerMasterFactory;
import game.content.phantasye.skill.slayer.master.assignment.AbstractAssignmentChainBase;
import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;
import game.content.skilling.Skill;
import game.player.Player;

public class CheckLevelAssignmentChain extends AbstractAssignmentChainBase {

    public CheckLevelAssignmentChain(Player player) {
        super(player);
    }

    @Override
    public void assignTask(PlayerSlayerTask task) {
        final SlayerMaster master = SlayerMasterFactory.getSlayerMaster(task.getMaster()).orElse(null);
        if (master != null) {
            if (this.getPlayer().baseSkillLevel[Skill.SLAYER.getId()]
                    >= master.getLevelRequirement()) {
                this.getNextChain().assignTask(task);
            } else if(!SlayerSkill.isDoingDuoSlayer(this.getPlayer())) {
                this.getPlayer().receiveMessage("Come back to me when you have a Slayer level of at least "
                        + master.getLevelRequirement()
                        + ".");
            } else {
                this.getPlayer().receiveMessage("Come back to me when you have a Slayer level of at least "
                        + master.getLevelRequirement()
                        + ".");
                this.getPlayer().getSlayerPartner().receiveMessage("Your partner is not a high enough level.");
            }
        }
    }
}

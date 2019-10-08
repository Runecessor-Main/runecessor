package game.content.phantasye.skill.slayer.master.assignment.impl;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.master.assignment.AbstractAssignmentChainBase;
import game.content.phantasye.skill.slayer.master.assignment.DuoPlayerAssignmentChain;
import game.content.phantasye.skill.slayer.master.assignment.SoloSlayerAssignmentChain;
import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;
import game.content.skilling.Skill;
import game.player.Player;

public class CheckTaskLevelAssignmentChain extends AbstractAssignmentChainBase {

    public CheckTaskLevelAssignmentChain(Player player) {
        super(player);
    }

    @Override
    public void assignTask(PlayerSlayerTask task) {
        if(SlayerAssignment.values()[task.getAssignment()].getLevel()
                <= this.getPlayer().baseSkillLevel[Skill.SLAYER.getId()]) {
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

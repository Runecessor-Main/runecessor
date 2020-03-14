package game.content.phantasye.skill.support.slayer.master.assignment;

import game.content.phantasye.skill.support.slayer.task.PlayerSlayerTask;

public interface AssignmentChain {

    void setNextChain(AssignmentChain chain);

    void assignTask(PlayerSlayerTask task);
}

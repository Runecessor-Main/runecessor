package game.content.phantasye.skill.slayer.master.assignment;

import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;

public interface AssignmentChain {

    void setNextChain(AssignmentChain chain);

    void assignTask(PlayerSlayerTask task);
}

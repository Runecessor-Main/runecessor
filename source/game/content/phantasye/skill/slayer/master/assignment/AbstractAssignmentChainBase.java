package game.content.phantasye.skill.slayer.master.assignment;

import game.player.Player;

public abstract class AbstractAssignmentChainBase implements AssignmentChain {

    private AssignmentChain nextChain;
    private final Player player;

    protected AbstractAssignmentChainBase(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public AssignmentChain getNextChain() {
        return nextChain;
    }

    @Override
    public void setNextChain(AssignmentChain nextChain) {
        this.nextChain = nextChain;
    }
}

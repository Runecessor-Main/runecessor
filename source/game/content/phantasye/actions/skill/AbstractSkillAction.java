package game.content.phantasye.actions.skill;

import game.player.Player;
import org.menaphos.action.impl.AbstractAction;

public abstract class AbstractSkillAction extends AbstractAction {

    private final Player actor;
    private final int levelRequired;
    private long actionTimer;

    public AbstractSkillAction(int levelRequired, Player actor ) {
        this.levelRequired = levelRequired;
        this.actor = actor;
    }

    public long getActionTimer() {
        return actionTimer;
    }

    public void setActionTimer(long actionTimer) {
        this.actionTimer = actionTimer;
    }

    public Player getActor() {
        return actor;
    }

    public int getLevelRequired() {
        return levelRequired;
    }
}

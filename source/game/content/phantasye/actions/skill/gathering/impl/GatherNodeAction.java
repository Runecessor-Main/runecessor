package game.content.phantasye.actions.skill.gathering.impl;

import game.content.phantasye.actions.skill.gathering.AbstractGatheringSkillAction;
import game.content.phantasye.skill.nodes.impl.GatheringNode;
import game.player.Player;

import java.util.List;

public abstract class GatherNodeAction<N extends GatheringNode> extends AbstractGatheringSkillAction<N> {

    public GatherNodeAction(int levelRequirement, List<Integer> toolList, N node, Player actor) {
        super(levelRequirement,toolList,node,actor);
    }

    protected void startActionTimer() {
        this.setActionTimer(this.getNode().getDifficulty());
    }

    @Override
    public boolean invoke() {
        this.startActionTimer();
        return false;
    }
}

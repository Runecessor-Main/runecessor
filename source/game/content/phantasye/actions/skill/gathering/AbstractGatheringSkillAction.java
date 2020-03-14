package game.content.phantasye.actions.skill.gathering;

import game.content.phantasye.actions.skill.AbstractSkillAction;
import game.content.phantasye.skill.nodes.impl.GatheringNode;
import game.player.Player;

import java.util.List;

public abstract class AbstractGatheringSkillAction<N extends GatheringNode> extends AbstractSkillAction {

    private final List<Integer> toolList;
    private final N node;

    public AbstractGatheringSkillAction(int levelRequirement, List<Integer> toolList, N node, Player actor) {
        super(levelRequirement,actor);
        this.toolList = toolList;
        this.node = node;
    }

    public N getNode() {
        return node;
    }

    public List<Integer> getToolList() {
        return toolList;
    }
}

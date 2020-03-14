package game.content.phantasye.actions.skill.gathering.impl;

import game.content.phantasye.skill.gathering.mining.Pickaxe;
import game.content.phantasye.skill.nodes.impl.ReplenishingGatheringNode;
import game.player.Player;

public class MineRockAction extends GatherNodeAction<ReplenishingGatheringNode> {

    public MineRockAction(ReplenishingGatheringNode node, Player actor) {
        super(node.getLevelRequirement(), Pickaxe.getIds(), node, actor);
    }


}

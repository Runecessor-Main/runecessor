package game.content.phantasye.skill.nodes;

import game.content.phantasye.skill.gathering.mining.Ore;
import game.content.phantasye.skill.gathering.mining.Pickaxe;
import game.content.phantasye.skill.nodes.impl.ReplenishingGatheringNode;

import java.util.HashMap;
import java.util.Map;

public final class NodeFactory {

    private final static Map<Integer,SkillNode> NODE_MAP = new HashMap<>();
    {

    }


    public static SkillNode getSkillNode(int id) {
        SkillNode skillNode = NODE_MAP.get(id);

        return skillNode;
    }

}

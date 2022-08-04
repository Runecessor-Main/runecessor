package game.content.phantasye.skill.nodes;

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

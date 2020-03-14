package game.content.phantasye.skill.nodes.impl;

import game.content.phantasye.skill.nodes.ExperienceNode;
import game.content.phantasye.skill.nodes.SkillNode;
import org.menaphos.model.world.location.Location;

public abstract class GatheringNode extends SkillNode implements ExperienceNode {

    private final int difficulty;

    public GatheringNode(int objectId, int levelRequirement, Location location, int difficulty) {
        super(objectId, levelRequirement,location);
        this.difficulty = difficulty;
    }

    public void test() {

    }

    public int getDifficulty() {
        return difficulty;
    }
}

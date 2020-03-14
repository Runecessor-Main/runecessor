package game.content.phantasye.skill.nodes;

import org.menaphos.model.world.location.Location;

public class SkillNode {

    private final int objectId;
    private final int levelRequirement;
    private final Location location;

    public SkillNode(int objectId, int levelRequirement, Location location) {
        this.objectId = objectId;
        this.levelRequirement = levelRequirement;
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getObjectId() {
        return objectId;
    }
}

package game.content.phantasye.skill.nodes.impl;

import game.content.phantasye.skill.nodes.ReplishableNode;
import game.object.custom.Object;
import game.player.PlayerHandler;
import org.menaphos.model.world.location.Location;

public class ReplenishingGatheringNode extends GatheringNode implements ReplishableNode {

    private final int depletedId;
    private final int respawnTimer;
    private final int xp;

    public ReplenishingGatheringNode(int objectId, int levelRequirement, Location location, int depletedId,int respawnTimer, int xp, int difficulty) {
        super(objectId, levelRequirement, location,difficulty);
        this.depletedId = depletedId;
        this.respawnTimer = respawnTimer;
        this.xp = xp;
    }

    @Override
    public int grantExperience() {
        return xp;
    }

    @Override
    public void respawn() {

    }

    @Override
    public void despawn() {
        new Object(depletedId, this.getLocation().getXCoordinate(), this.getLocation().getZCoordinate(), this.getLocation().getYCoordinate(), 0, 10, this.getObjectId(), respawnTimer);
        for (int t = 0; t < PlayerHandler.players.length; t++) {
            if (PlayerHandler.players[t] != null) {
                if (PlayerHandler.players[t].getObjectX() == this.getLocation().getXCoordinate()
                        && PlayerHandler.players[t].getObjectY() == this.getLocation().getZCoordinate()) {
                    PlayerHandler.players[t].playerAssistant.stopAllActions();
                    PlayerHandler.players[t].startAnimation(65535);
                    PlayerHandler.players[t].setObjectX(0);
                    PlayerHandler.players[t].setObjectY(0);
                }
            }
        }
    }

    public int getRespawnTimer() {
        return respawnTimer;
    }

    public int getDepletedId() {
        return depletedId;
    }

    public int getXp() {
        return xp;
    }
}

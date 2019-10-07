package game.content.phantasye.skill;

import core.Server;
import game.object.custom.Object;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.Timer;
import java.util.TimerTask;

public class Trap extends Object {

    private final Location location;
    private final Region area;
    private final int objectId;
    private final Player owner;
    private final Timer duration;

    public Trap(Location location, int objectId, Player owner) {
        super(objectId,location.getXCoordinate(),location.getZCoordinate(),location.getYCoordinate(),0,10,-1,-1);
        this.location = location;
        this.area = new Region(
                new Location(location.getXCoordinate() - 5, location.getZCoordinate() -5),
                new Location(location.getXCoordinate() + 5, location.getZCoordinate() + 5)
        );
        this.objectId = objectId;
        this.owner = owner;
        this.duration = new Timer();

        this.startTimer();
    }

    private void startTimer() {
        duration.schedule(new TimerTask() {
            @Override
            public void run() {
                remove();
            }
        },60000 * 5);
    }

    private void remove() {
        Server.objectManager.removeObject(this);
    }

    public Location getLocation() {
        return location;
    }

    public Player getOwner() {
        return owner;
    }

    public int getObjectId() {
        return objectId;
    }

    public Region getArea() {
        return area;
    }
}

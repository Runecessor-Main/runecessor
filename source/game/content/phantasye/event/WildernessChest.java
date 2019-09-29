package game.content.phantasye.event;

import game.object.custom.Object;
import org.menaphos.model.world.location.Face;
import org.menaphos.model.world.location.Location;

public class WildernessChest extends Object {

    private final Location location;

    public WildernessChest(Location location, Face face) {
        super(11341, location.getXCoordinate(), location.getZCoordinate(), location.getYCoordinate(), face.ordinal(), 10, -1, -1);
        this.location = location;
    }

    public WildernessChest(Location location) {
        this(location,Face.NORTH);
    }

    public Location getLocation() {
        return location;
    }
}

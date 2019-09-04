package game.content.phantasye.minigame.pirate;

import game.object.custom.Object;
import game.player.Player;
import game.position.Position;
import org.menaphos.model.world.location.Face;
import org.menaphos.model.world.location.Location;

public class PirateCannon extends Object {

    private final Player target;
    private final Location location;

    public PirateCannon(Location location, int face, Player target) {
        super(7137,location.getX().value(),location.getZ().value(),location.getY().value(), face, 10, -1, -1);
        this.target = target;
        this.location = location;
    }

    public void fire() {
        target.getPA().createPlayersProjectile(
                new Position(getPosition().getX() + 1, getPosition().getY() + 1, getPosition().getZ()),
                target.getPosition(), 50, 80, 53, 35, 15, target.getId(), 0, 0);
        target.getPA().stillGfx(1028,target.getX(),target.getY(),target.getHeight(),60);
    }

    public Location getLocation() {
        return location;
    }
}

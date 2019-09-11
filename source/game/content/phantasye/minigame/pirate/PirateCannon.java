package game.content.phantasye.minigame.pirate;

import core.Server;
import core.ServerConstants;
import game.content.combat.Combat;
import game.content.phantasye.RegionUtils;
import game.object.ObjectEvent;
import game.object.custom.Object;
import game.player.Player;
import game.player.PlayerHandler;
import game.position.Position;
import game.position.PositionUtils;
import org.menaphos.model.world.location.Face;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.Timer;
import java.util.TimerTask;

public class PirateCannon extends Object {

    public static final int ID = 7137;
    public static final int CANNONBALL = 53;
    public static final int EXPLOSION = 1028;

    private final Player target;
    private final Location location;
    private final Region firingRadius;
    private final PirateMinigame session;

    private Location targetedLocation;

    public PirateCannon(Location location, int face, Player target) {
        super(ID, location.getX().value(), location.getZ().value(), location.getY().value(), face, 10, -1, -1);
        this.target = target;
        this.location = location;
        this.firingRadius = this.calculateFiringRadius();
        this.session = target.getPirateMinigameSession();

        session.addCannon(this);
    }

    public PirateCannon(Location location, Player target) {
        super(ID, location.getX().value(), location.getZ().value(), location.getY().value(), location.directionFrom(new Location(target.getX(), target.getHeight(), target.getY())).ordinal(), 10, -1, -1);
        this.target = target;
        this.location = location;
        this.firingRadius = this.calculateFiringRadius();
        this.session = target.getPirateMinigameSession();

        session.addCannon(this);
    }

    public PirateCannon(Location location, Face face, Player target) {
        this(location, face.ordinal() * 2, target);
    }

    private Region calculateFiringRadius() {
        final Location swCorner = new Location(location.getXCoordinate() - 5, target.getHeight(), location.getZCoordinate() - 5);
        final Location neCorner = new Location(location.getXCoordinate() + 5, target.getHeight(), location.getZCoordinate() + 5);
        return new Region(swCorner, neCorner);
    }

    public static Location spawnForTarget(Player target) {
        final Location swCorner = new Location(target.getX() - 5, target.getHeight(), target.getY() - 5);
        final Location neCorner = new Location(target.getX() + 5, target.getHeight(), target.getY() + 5);
        final Region spawningRegion = new Region(swCorner, neCorner);
        final Location spawningLocation = RegionUtils.getLocationInRegion(spawningRegion);
        return spawningLocation;
    }


    public void fire() {
        final Timer fireTimer = new Timer();
        fireTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                final Location targetedLocation = new Location(target.getX(), target.getHeight(), target.getY());
                if (session.getCannonAtLocation(location).isPresent()) {
                    if (firingRadius.contains(targetedLocation)) {
                        setTargetedLocation(targetedLocation);
                        turnTo(location.directionFrom(targetedLocation));
                        target.getPA().createPlayersProjectile(
                                new Position(getPosition().getX() + 1, getPosition().getY() + 1, getPosition().getZ()),
                                target.getPosition()
                                , 50, 80, CANNONBALL, 35, 15, target.getId(), 0, 0);
                        target.getPA().stillGfx(EXPLOSION, target.getX(), target.getY(), target.getHeight(), 60);
                        hitTarget();
                    }
                } else {
                    this.cancel();
                }
            }
        }, 1000, 4000);
    }

    private void turnTo(Face face) {
        int animation = face.ordinal() + 515;

        if (face == Face.NORTHWEST)
            animation = 514;
        for (Player player : PlayerHandler.getPlayers()) {
            if (player != null && PositionUtils.isWithinDistance(player.getPosition(), new Position(objectX, objectY)))
                player.getPA().objectAnimation(objectX, objectY, 10, 0, animation);
        }
    }

    public void destroy() {
        target.startAnimation(827);
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                target.getPA().stillGfx(EXPLOSION, location.getXCoordinate(), location.getZCoordinate(), target.getHeight(), 10);
                remove();
            }
        }, 1000);
    }

    private void hitTarget() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (new Location(target.getX(), target.getHeight(), target.getY()).matches(targetedLocation)) {
                    Combat.createHitsplatOnPlayerNormal(target, 25, ServerConstants.NORMAL_HITSPLAT_COLOUR, ServerConstants.NO_ICON);
                }
            }

        }, 1600);
    }

    private void remove() {
        session.removeCannon(this);
        Server.objectManager.removeObject(this);
    }

    public Location getLocation() {
        return location;
    }

    public Location getTargetedLocation() {
        return targetedLocation;
    }

    public void setTargetedLocation(Location targetedLocation) {
        this.targetedLocation = targetedLocation;
    }
}

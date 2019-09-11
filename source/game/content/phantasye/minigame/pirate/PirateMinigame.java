package game.content.phantasye.minigame.pirate;

import game.content.phantasye.RegionUtils;
import game.content.phantasye.minigame.instance.Instancable;
import game.content.phantasye.minigame.instance.InstanceFactory;
import game.content.phantasye.minigame.instance.InstanceKey;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PirateMinigame implements Instancable {

    public static final int GANGPLANK = 11211;

    private static final int CANNONS = 3;

    private final Map<Location, PirateCannon> cannonMap;
    private final Player player;
    private final Region topDeck;
    private final InstanceKey instanceKey = InstanceFactory.getKeyForInstance(this);

    public PirateMinigame(Player player) {
        this.cannonMap = new HashMap<>();
        this.player = player;
        this.topDeck = new Region(new Location(3676, instanceKey.getInstance(), 2946), new Location(3685, instanceKey.getInstance(), 2950));
    }

    public boolean processClick(int objectId, int objectX, int objectY) {
        switch (objectId) {
            case PirateCannon.ID:
                final Optional<PirateCannon> cannon = this.getCannonAtLocation(new Location(objectX, player.getHeight(), objectY));
                cannon.ifPresent(PirateCannon::destroy);
                return true;
            case 11212:
                this.disembark();
                return true;

        }
        return false;
    }

    public void disembark() {
        player.getPA().movePlayer(3684 ,2953, 0);
        player.receiveMessage("You disembark the boat...");
        InstanceFactory.reclaimKey(instanceKey);
        player.setPirateMinigameSession(null);
    }

    public void boardBoat() {
        player.getPA().movePlayer(3684, 2950, instanceKey.getInstance());
        player.receiveMessage("You board the boat...");
        this.spawnCannons();
    }

    public Optional<PirateCannon> getCannonAtLocation(Location location) {
        Optional<Location> match = cannonMap.keySet().stream().filter(key -> key.matches(location)).findAny();
        return match.map(cannonMap::get);
    }

    public void addCannon(PirateCannon cannon) {
        cannonMap.put(cannon.getLocation(), cannon);
    }

    public void removeCannon(PirateCannon cannon) {
        cannonMap.remove(cannon.getLocation());
    }

    private void reset() {
        cannonMap.clear();
    }

    private void spawnCannons() {
        final Location playerLocation = new Location(player.getX(), player.getHeight(),
                player.getY());
        for (int i = 0; i < CANNONS; i++) {
            final Location location = RegionUtils.getLocationInRegion(topDeck);
            this.addCannon(new PirateCannon(location,location.directionFrom(playerLocation),player));
        }
    }

    private void spawnPirates(Region region) {

    }

    private void spawnPirates(Location location) {

    }

    public Player getPlayer() {
        return player;
    }

    public Map<Location, PirateCannon> getCannonMap() {
        return cannonMap;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int startingInstance() {
        return 1;
    }
}

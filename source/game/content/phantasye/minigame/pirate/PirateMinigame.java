package game.content.phantasye.minigame.pirate;

import core.ServerConstants;
import game.content.phantasye.RegionUtils;
import game.content.phantasye.minigame.instance.Instancable;
import game.content.phantasye.minigame.instance.InstanceFactory;
import game.content.phantasye.minigame.instance.InstanceKey;
import game.item.ItemAssistant;
import game.menaphos.looting.model.loot.Loot;
import game.menaphos.looting.model.loot.LootableContainer;
import game.menaphos.looting.model.loot.factory.LootFactory;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.*;

public class PirateMinigame implements Instancable {

    public static final int GANGPLANK = 11211;
    public static final int REWARD_CHEST = 11341;
    public static final int CAPTAIN = 5426;
    public static final int THROW = 806; //anim
    public static final int ATTACK = 4177; //anim
    public static final int DRINK = 1327; //anim


    private static final int CANNONS = 3;


    private final Map<Location, PirateCannon> cannonMap;
    private final Player player;
    private final Region topDeck;
    private final InstanceKey instanceKey = InstanceFactory.getKeyForInstance(this);
    private final List<Integer> pirateList;

    public PirateMinigame(Player player) {
        this.cannonMap = new HashMap<>();
        this.pirateList = new ArrayList<>();
        this.player = player;
        this.topDeck = new Region(new Location(3676, instanceKey.getInstance(), 2946), new Location(3685, instanceKey.getInstance(), 2950));
    }

    private void loadPirateList() {
        pirateList.add(1447);
        for (int i = 2879; i < 2883; i++) {
            pirateList.add(i);
        }
        for (int i = 4023; i < 4053; i++) {
            pirateList.add(i);
        }
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
        player.getPA().movePlayer(3684, 2953, 0);
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

    public static List<Loot> openRewardChest(Player player) {
        final List<Loot> rewards = new ArrayList<>();
        final LootableContainer chest = LootFactory.getLootableObject(REWARD_CHEST);
        for (int i = 0; i < 4; i++) {
            Loot loot = chest.open();
            rewards.add(loot);
            player.receiveMessage("You open the chest and receive " + ServerConstants.BLUE_COL + "x"
                    + loot.getItem().getAmount()
                    + " "
                    + ItemAssistant.getItemName(loot.getItem().getId()));
        }
        return rewards;
    }

    private void reset() {
        cannonMap.clear();
    }

    private void spawnCannons() {
        final Location playerLocation = new Location(player.getX(), player.getHeight(),
                player.getY());
        for (int i = 0; i < CANNONS; i++) {
            final Location location = RegionUtils.getLocationInRegion(topDeck);
            this.addCannon(new PirateCannon(location, location.directionFrom(playerLocation), player));
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

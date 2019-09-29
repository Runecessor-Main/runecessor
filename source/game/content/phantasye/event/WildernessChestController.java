package game.content.phantasye.event;

import core.Server;
import core.ServerConstants;
import game.content.miscellaneous.Announcement;
import game.content.phantasye.RegionUtils;
import game.item.ItemAssistant;
import game.menaphos.looting.model.loot.Loot;
import game.menaphos.looting.model.loot.LootableContainer;
import game.menaphos.looting.model.loot.factory.LootFactory;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;
import utility.Misc;

import java.util.*;

public final class WildernessChestController {

    private enum Spawn {
        CHINCHOMPA_HILL(3134, 0, 3780, 3141, 0, 3787, "on Chinchompa Hill"),
        ROGUES_CASTLE(3290, 0, 3937, 3296, 0, 3942, "in the Rogue's Castle"),
        NORTH_LAVA_DRAGONS(3057, 0, 3795, 3071, 0, 3803, "North of the Lava Dragons"),
        MAGE_ARENA(3074, 0, 3949, 3082, 0, 3956, "West of the Mage Arena"),
        DEMONIC_RUINS(3285, 0, 3883, 3292, 0, 3887, "in the Demonic Ruins"),
        RESOURCE_AREA(3176, 0, 3950, 3187, 0, 3952, "North of the Resource Area");

        private final int swX;
        private final int swY;
        private final int swZ;
        private final int neX;
        private final int neY;
        private final int neZ;
        private final String location;

        private Spawn(int swX, int swY, int swZ, int neX, int neY, int neZ, String location) {
            this.swX = swX;
            this.swY = swY;
            this.swZ = swZ;
            this.neX = neX;
            this.neY = neY;
            this.neZ = neZ;
            this.location = location;
        }

        public int getNeX() {
            return neX;
        }

        public int getNeY() {
            return neY;
        }

        public int getNeZ() {
            return neZ;
        }

        public int getSwX() {
            return swX;
        }

        public int getSwY() {
            return swY;
        }

        public int getSwZ() {
            return swZ;
        }

        public String getLocation() {
            return location;
        }
    }

    private static WildernessChestController instance = null;
    private static final int ONE_HOUR = 3600000;

    private final Queue<Spawn> spawnQueue;
    private final Timer eventTimer;
    private final List<Loot> lootList;

    private WildernessChest activeChest;
    private Spawn activeSpawn;

    public static WildernessChestController getInstance() {
        if (instance == null)
            instance = new WildernessChestController();
        return instance;
    }

    private WildernessChestController() {
        this.spawnQueue = new LinkedList<>();
        this.eventTimer = new Timer();
        this.lootList = new ArrayList<>();
    }

    public void initialize() {
        this.loadSpawnQueue();
        eventTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                resetChest();
                spawnChest();
            }
        },ONE_HOUR/2,ONE_HOUR);
    }

    public void openChest(Player player) {
        lootList.stream().forEach(loot ->
                player.getPA().sendMessage("You receive x"
                        + loot.getItem().getAmount()
                        + " "
                        + ItemAssistant.getItemName(loot.getItem().getId())));
        lootList.stream().forEach(loot -> ItemAssistant.addItem(player,loot.getItem().getId(),loot.getItem().getAmount()));
        this.resetChest();
    }

    private void loadSpawnQueue() {
        spawnQueue.addAll(Arrays.asList(Spawn.values()));
    }

    public void spawnChest() {
        if (!spawnQueue.isEmpty()) {
            final Spawn spawn = spawnQueue.poll();
            final Region region = new Region(
                    new Location(spawn.getSwX(), spawn.getSwY(), spawn.getSwZ()),
                    new Location(spawn.getNeX(), spawn.getNeY(), spawn.getNeZ())
            );

            this.setActiveChest(new WildernessChest(RegionUtils.getLocationInRegion(region)));
            this.setActiveSpawn(spawn);

            this.fillChest();
            this.announceChest();
        } else {
            this.loadSpawnQueue();
            this.spawnChest();
        }
    }

    private void announceChest() {
        final StringBuilder sb = new StringBuilder();

        sb.append("A Loot Chest has spawned")
                .append(" ")
                .append(activeSpawn.location);
        Announcement.announce(sb.toString(), ServerConstants.RED_COL);

        sb.setLength(0);

        sb.append("Contents:");

        lootList.stream().forEach(loot ->
                sb.append(" ")
                .append("x")
                .append(loot.getItem().getAmount())
                .append(" ")
                .append(ItemAssistant.getItemName(loot.getItem().getId())));

        Announcement.announce(sb.toString(), ServerConstants.RED_COL);
    }

    private void resetChest() {
        lootList.clear();
        Server.objectManager.removeObject(activeChest);
        this.setActiveChest(null);
        this.setActiveSpawn(null);
    }

    private void fillChest() {
        final LootableContainer lootableContainer = LootFactory.getLootableObject(11341);

        for (int i = 0; i < Misc.random(1, 5); i++) {
            lootList.add(lootableContainer.open());
        }
    }

    public Timer getEventTimer() {
        return eventTimer;
    }

    public Queue<Spawn> getSpawnQueue() {
        return spawnQueue;
    }

    public List<Loot> getLootList() {
        return lootList;
    }

    public void setActiveChest(WildernessChest activeChest) {
        this.activeChest = activeChest;
    }

    public WildernessChest getActiveChest() {
        return activeChest;
    }

    public Spawn getActiveSpawn() {
        return activeSpawn;
    }

    public void setActiveSpawn(Spawn activeSpawn) {
        this.activeSpawn = activeSpawn;
    }
}

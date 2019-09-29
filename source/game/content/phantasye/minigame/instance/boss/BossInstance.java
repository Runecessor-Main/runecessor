package game.content.phantasye.minigame.instance.boss;

import core.ServerConstants;
import game.content.miscellaneous.Teleport;
import game.content.phantasye.RegionUtils;
import game.content.phantasye.minigame.instance.Instancable;
import game.content.phantasye.minigame.instance.InstanceFactory;
import game.content.phantasye.minigame.instance.InstanceKey;
import game.item.GameItem;
import game.item.ItemAssistant;
import game.npc.Npc;
import game.npc.NpcHandler;
import game.player.Player;
import org.menaphos.model.math.AdjustableNumber;
import org.menaphos.model.math.impl.AdjustableInteger;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BossInstance implements Instancable {

    private static final int SESSION_DURATION = 10;

    private final Npc boss;
    private final Player instanceOwner;
    private final InstanceKey key;
    private final Timer eventTimer;
    private final Region instanceRegion;
    private final List<GameItem> itemsOnEntry;

    public BossInstance(Npc boss, Player player) {
        this.boss = boss;
        this.itemsOnEntry = new ArrayList<>();
        this.instanceOwner = player;
        this.key = InstanceFactory.getKeyForInstance(this);
        this.eventTimer = new Timer();
        this.instanceRegion = new Region(
                new Location(2580, key.getInstance(), 3150),
                new Location(2607, key.getInstance(), 3172)

        );
    }

    private void saveItems() {
        for (int i = 0; i < instanceOwner.playerItems.length; i++) {
            if (instanceOwner.playerItems[i] > 0)
                itemsOnEntry.add(new GameItem(instanceOwner.playerItems[i] - 1, instanceOwner.playerItemsN[i]));
        }

        for (int i = 0; i < instanceOwner.playerEquipment.length; i++) {
            if (instanceOwner.playerEquipment[i] > 0)
                itemsOnEntry.add(new GameItem(instanceOwner.playerEquipment[i], instanceOwner.playerEquipmentN[i]));
        }
    }

    public void create(Region region) {
        instanceOwner.receiveMessage(ServerConstants.RED_COL + "Boss will spawn in 5 seconds...");
        instanceOwner.setActiveBossInstance(this);
        this.saveItems();
        final Location location = RegionUtils.getLocationInRegion(region);
        eventTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                NpcHandler.spawnNpc(
                        instanceOwner,
                        boss.npcType,
                        location.getXCoordinate(),
                        location.getZCoordinate(),
                        key.getInstance(),
                        false,
                        false);

                startSession();
                process();
            }
        }, 5000);
    }

    private void startTimer() {
        final AdjustableNumber<Integer> currentMinute = new AdjustableInteger(0);
        instanceOwner.receiveMessage(ServerConstants.RED_COL + "Instance will close in " + SESSION_DURATION + " Minutes.");
        eventTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentMinute.increment();
                instanceOwner.receiveMessage(ServerConstants.RED_COL + "Instance will close in " + (SESSION_DURATION - currentMinute.value()) + " Minutes.");
                if (currentMinute.value() == SESSION_DURATION) {
                    this.cancel();
                }
            }
        }, 60000, 60000);
    }

    public void startSession() {
        this.startTimer();
        eventTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                removePlayerFromInstance();
            }


        }, 60000 * SESSION_DURATION);
    }

    private void process() {
        eventTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!instanceRegion.contains(new Location(instanceOwner.getX(), instanceOwner.getHeight(), instanceOwner.getY()))) {
                    this.cancel();
                }
                if(instanceOwner.dead) {
                    instanceOwner.receiveMessage(ServerConstants.RED_COL + "Your Items have been dropped at ::home. ");
                }
            }

            @Override
            public boolean cancel() {
                closeInstance();
                return true;
            }
        }, 600, 600);
    }

    private void refundItemsAndClose() {
        final Timer respawnTimer = new Timer();
        respawnTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ItemAssistant.deleteAllItems(instanceOwner);
                itemsOnEntry.forEach(gameItem -> ItemAssistant.addItemToInventoryOrDrop(instanceOwner, gameItem.getId(), gameItem.getAmount()));
                closeInstance();
                respawnTimer.cancel();
            }
        },5000);


    }

    public void closeInstance() {
        instanceOwner.setActiveBossInstance(null);
        eventTimer.cancel();
        instanceOwner.receiveMessage(ServerConstants.RED_COL + "Closing Instance...");
        InstanceFactory.reclaimKey(key);
    }

    public void removePlayerFromInstance() {
        Teleport.spellTeleport(instanceOwner, 3087, 3493, 0, true);
        instanceOwner.setX(3087);
        instanceOwner.setY(3493);
        instanceOwner.setHeight(0);
        this.closeInstance();
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public int startingInstance() {
        return 0;
    }

    public Player getInstanceOwner() {
        return instanceOwner;
    }

    public InstanceKey getKey() {
        return key;
    }

    public Npc getBoss() {
        return boss;
    }
}

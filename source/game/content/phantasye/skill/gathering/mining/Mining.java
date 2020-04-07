package game.content.phantasye.skill.gathering.mining;

import core.Server;
import core.ServerConstants;
import game.content.skilling.Skill;
import game.content.skilling.Skilling;
import game.content.skilling.SkillingStatistics;
import game.item.ItemAssistant;
import game.object.custom.Object;
import game.object.custom.ObjectManagerServer;
import game.player.Player;
import game.player.PlayerHandler;
import game.player.event.CycleEvent;
import game.player.event.CycleEventContainer;
import game.player.event.CycleEventHandler;
import org.menaphos.model.loot.factory.LootFactory;
import org.menaphos.model.world.location.Location;
import utility.Misc;

import java.util.*;

/**
 * New and improved mining based off the woodcutting class
 * see Woodcutting.java for documentation
 * to add a new ore do the following
 * 1.add the ore data to the Ores.java enum
 * 2.add the ore ID in ActionHandler.java with the others
 *
 * @author Quinton
 */
public class Mining {

    private Player player;
    private int miningTimer;
    private Map<Integer, Ore> oreList = new HashMap<>();
    private Map<Integer, Pickaxe> pickList = new HashMap<>();
    private int anim; // is set later

    private int[] gems = {1623, 1621, 1619, 1617};

    /**
     * static initialize to populate our hashmaps
     */ {
        for (Ore ore : Ore.values()) {
            for (int i = 0; i < ore.getOreId().length; i++) {
                oreList.put(ore.getOreId()[i], ore); // populates the treeList
            }
        }
        for (Pickaxe pickaxe : Pickaxe.values()) {
            pickList.put(pickaxe.getPick(), pickaxe); // populates the axeList
        }
    }

    public Mining(Player c) {
        this.player = c;
    }

    private boolean hasLevel(int oreId) {
        if (oreList.get(oreId).getLevelRequirements() > player.baseSkillLevel[Skill.MINING
                .getId()]) {
            player.sendMessage("You need a Mining level of "
                    + oreList.get(oreId).getLevelRequirements()
                    + " to mine this.");
            return false;
        }

        return true;
    }

    private boolean hasValidPick() {
        for (Pickaxe pickaxe : Pickaxe.values()) {
            if (player.hasItem(pickaxe.getPick(), 1)
                    || player.getWieldedWeapon() == pickaxe.getPick()) {
                if (pickaxe.getLevelRequirement() <= player.baseSkillLevel[Skill.MINING
                        .getId()]) {
                    anim = pickaxe.getAnimation();
                    return true;
                } else {
                    player.sendMessage("You need a pickaxe.");
                    return false;
                }
            }
        }
        return false;
    }

    private int getPick() {
        int highest = -1;
        for (Pickaxe data : Pickaxe.values()) {
            if ((ItemAssistant.hasItemInInventory(player, data.getPick()) || ItemAssistant.hasItemEquipped(player, data.getPick()))
                    && player.baseSkillLevel[ServerConstants.MINING] >= data.getLevelRequirement()) {
                highest = data.getPick();
            }
        }
        return highest;
    }

    private boolean hasInventorySpace() {
        if (ItemAssistant.getFreeInventorySlots(player) < 1) {
            player.sendMessage("Your inventory is full.");
            return false;
        }
        return true;
    }

    private boolean canMine(int ore) {
        if (hasInventorySpace() && hasValidPick()
                && hasLevel(ore))
            return true;
        return false;
    }

    private int getTimer(int oreId, int pickId, int level) {
        double timer = 0;
        if (pickId == pickList.get(pickId).getPick()) {
            timer = (oreList.get(oreId).getLevelRequirements() * 2)
                    + 20
                    + Misc.random(20)
                    - ((pickList.get(pickId).getEfficiency() * (pickList.get(
                    pickId).getEfficiency() * 0.75)) + level);
            if (timer < 3.0)
                return 2 + Misc.random(3 + (level / (level - oreList.get(oreId).getLevelRequirements())));
        }
        return (int) timer;
    }

    public boolean startMining(final int x, final int y, final int oreId) {
        player.turnPlayerTo(x, y);
        if (canMine(oreId)) {
            miningTimer = getTimer(oreId, getPick(),
                    player.baseSkillLevel[Skill.MINING.getId()]);
            player.startAnimation(anim);
            this.startMiningEvent(x, y, oreId);
            return true;
        }
        return false;
    }

    private void startMiningEvent(final int x, final int y, final int oreId) {
        if (Skilling.cannotActivateNewSkillingEvent(player)) {
            return;
        }
        CycleEventHandler.getSingleton().addEvent(player, new CycleEvent() {
            @Override
            public void execute(CycleEventContainer event) {
                if (Skilling.forceStopSkillingEvent(player)) {
                    event.stop();
                    return;
                }
                if (miningTimer > 0) {
                    miningTimer--;
                    findGems();
                    player.startAnimation(anim);
                    if (miningTimer == 0) {
                        player.addItemToInventory(oreList.get(oreId).getItemId(), 1);
                        Skilling.addSkillExperience(
                                player,
                                oreList.get(oreId).getXp(),
                                Skill.MINING.getId(),
                                false);
                        mineRock(oreList.get(oreId).getRespawnTime(), x, y,
                                oreId);
                        event.stop();
                    }
                }
            }

            @Override
            public void stop() {
                player.startAnimation(65535);
                Skilling.endSkillingEvent(player);
            }
        }, 1);
    }

    private void findGems() {
        if (Misc.random(500) == 0) {
            player.loot(LootFactory.getLootableItem(4));
            player.receiveMessage("You find a gem!");
        }
    }

    private boolean containsOre(Ore ore, int id) {
        return Arrays.stream(ore.getOreId()).anyMatch(value -> value == id);
    }

    private void mineRock(int respawnTime, int x, int y, int ore) {
        Skilling.petChance(player, oreList.get(ore).getXp(), 125, 3800, ServerConstants.MINING, null);
        player.skillingStatistics[SkillingStatistics.ORES_MINED]++;
        if (!containsOre(Ore.WHITE_WOLF_MOUNTAIN_ROCKSLIDE, ore)) {
            new Object(10081, x, y, 0, 0, 10, ore, respawnTime);
            for (int t = 0; t < PlayerHandler.players.length; t++) {
                if (PlayerHandler.players[t] != null) {
                    if (PlayerHandler.players[t].getObjectX() == x
                            && PlayerHandler.players[t].getObjectY() == y) {
                        PlayerHandler.players[t].playerAssistant.stopAllActions();
                        PlayerHandler.players[t].startAnimation(65535);
                        PlayerHandler.players[t].setObjectX(0);
                        PlayerHandler.players[t].setObjectY(0);
                    }
                }
            }
        } else {
            new Object(-1, x, y, 0, 0, 10, ore, respawnTime);
            player.playerAssistant.stopAllActions();
            player.startAnimation(65535);
            player.setObjectX(0);
            player.setObjectY(0);
            player.moveTo(new Location(2839,3517));
        }

    }
}

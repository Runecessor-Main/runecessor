package game.content.phantasye.skill.slayer;

import core.ServerConstants;
import game.content.phantasye.skill.slayer.master.SlayerMasterFactory;
import game.content.phantasye.skill.slayer.task.BossTask;
import game.content.phantasye.skill.slayer.task.PlayerSlayerTask;
import game.content.skilling.Skilling;
import game.content.skilling.Slayer;
import game.npc.Npc;
import game.npc.NpcHandler;
import game.npc.impl.superior.SuperiorNpc;
import game.object.clip.Region;
import game.player.Player;
import game.position.Position;
import org.menaphos.model.world.location.Location;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class SlayerSkill {

    public static final int LIKE_BOSS_SCROLL = 12049;

    public static boolean unlock(Player player,SlayerUnlocks unloackable) {
        return !player.getPlayerDetails().getUnlocksList().contains(unloackable.ordinal());
    }

    public static Player getWeakestMemberFromDuo(Player player) {
        if(player.baseSkillLevel[18] < player.getSlayerPartner().baseSkillLevel[18]) {
            return player;
        }
        return  player.getSlayerPartner();
    }

    public static String getAssignmentName(PlayerSlayerTask task) {
        if(task != null)
            return SlayerAssignment.values()[task.getAssignment()].toString();
        return "None";
    }

    public static boolean isDoingDuoSlayer(Player player) {
        return player.getSlayerPartner() != null;
    }

    public static void slayerKill(Player player, Npc npc, int hp) {
        if (npc != null) {
            if (player.getPlayerDetails().getSlayerTask() != null) {
                if (correctNpc(npc, SlayerAssignment.values()[player.getPlayerDetails().getSlayerTask().getAssignment()])) {
                    final org.menaphos.model.world.location.region.Region effectedArea = new org.menaphos.model.world.location.region.Region(
                            new Location(player.getX() - 15, player.getHeight(), player.getY() - 15),
                            new Location(player.getX() + 15, player.getHeight(), player.getY() + 15)
                    );
                    if (player.getSlayerPartner() != null && effectedArea.contains(
                            new Location(
                                    player.getSlayerPartner().getX(),
                                    player.getSlayerPartner().getHeight(),
                                    player.getSlayerPartner().getY()))
                            && player.getSlayerPartner().getPlayerDetails().getSlayerTask() != null) {
                        player.getPlayerDetails().getSlayerTask().getAmount().decrement();
                        player.getSlayerPartner().getPlayerDetails().getSlayerTask().getAmount().decrement();
                    } else {
                        player.getPlayerDetails().getSlayerTask().getAmount().decrement();
                    }
                    if (player.getPlayerDetails().getSlayerTask().getAmount().greaterThan(0)) {
                        spawnSuperior(player, npc.npcType);
                        Skilling.addSkillExperience(player, hp, ServerConstants.SLAYER, false);
                    } else {
                        completeTask(player);
                    }
                    player.saveDetails();
                }
            }
        }
    }

    public static boolean isBossTask(int assignment) {
        return Arrays.stream(BossTask.values()).anyMatch(bossTask ->
                Arrays.equals(bossTask.getNpcs(), SlayerAssignment.values()[assignment].getNpcs()));
    }

    private static boolean correctNpc(Npc npc, SlayerAssignment assignment) {
        return Arrays.stream(assignment.getNpcs()).anyMatch(id -> id == npc.npcType);
    }

    private static void spawnSuperior(Player player, int npcId) {
        Arrays.stream(Slayer.Task.values())
                .filter(task -> task.getNpcId()[0] == npcId)
                .filter(task -> task.getSuperiorNpc() != -1)
                .forEach(task -> spawn(player, task.getSuperiorNpc()));
    }

    private static void spawn(Player player, int npcId) {
        if (ThreadLocalRandom.current().nextInt(0, 50) == 0) {
            Position tile = Region.nextOpenTileOrNull(player.getX(), player.getY(), player.getHeight());

            if (tile == null) {
                tile = new Position(player);
            }
            Npc superior = NpcHandler.spawnNpc(player, npcId, tile.getX(), tile.getY(), tile.getZ(), false, false);

            if (superior != null) {
                if (superior instanceof SuperiorNpc) {
                    superior.getAttributes().put(SuperiorNpc.SPAWNED_FOR, player.getPlayerName());
                    player.getPA().sendMessage("A superior foe has appeared...");
                } else {
                    superior.setItemsDroppable(false);
                    superior.killIfAlive();
                }
            }
        }
    }

    private static void completeTask(Player player) {
        SlayerMasterFactory.getSlayerMaster(player.getPlayerDetails().getSlayerTask().getMaster())
                .ifPresent(slayerMaster -> slayerMaster.completeTask(player));
    }
}

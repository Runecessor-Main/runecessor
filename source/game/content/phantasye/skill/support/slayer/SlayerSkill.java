package game.content.phantasye.skill.support.slayer;

import core.ServerConstants;
import game.content.dialogue.DialogueChain;
import game.content.miscellaneous.Teleport;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.content.phantasye.skill.support.slayer.master.SlayerMasterFactory;
import game.content.phantasye.skill.support.slayer.task.BossTask;
import game.content.phantasye.skill.support.slayer.task.PlayerSlayerTask;
import game.content.skilling.Skill;
import game.content.skilling.Skilling;
import game.content.skilling.Slayer;
import game.item.ItemAssistant;
import game.npc.Npc;
import game.npc.NpcHandler;
import game.npc.impl.superior.SuperiorNpc;
import game.object.clip.Region;
import game.player.Player;
import game.position.Position;
import org.menaphos.action.impl.item.BaseItemOnItemAction;
import org.menaphos.entity.impl.impl.PlayableCharacter;
import org.menaphos.model.world.location.Location;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SlayerSkill {

    public static final int ENCHANTMENT_SCROLL = 21257;

    public static boolean unlock(Player player, SlayerUnlocks unloackable) {
        return !player.getPlayerDetails().getUnlocksList().contains(unloackable.ordinal());
    }

    public static Player getWeakestMemberFromDuo(Player player) {
        if (player.baseSkillLevel[18] < player.getSlayerPartner().baseSkillLevel[18]) {
            return player;
        }
        return player.getSlayerPartner();
    }

    public static void useSlayerRing(Player player, int ringId) {
        if (player.getSlayerTask() != null) {
            final DialogueOptionPaginator.DialogueOptionPaginatorBuilder builder = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player);
            builder.withTitle("Teleport to Task");
            Arrays.stream(SlayerAssignment.values()[player.getSlayerTask().getAssignment()].getLocations())
                    .forEach(location -> builder.addOption(location.toString()));
            final DialogueOptionPaginator paginator = builder.build();
            final List<Locations> locations = new ArrayList<>(Arrays.asList(SlayerAssignment.values()[player.getSlayerTask().getAssignment()].getLocations()));
            player.setDialogueChain(paginator.getPageAsDialogOptions(0,
                    new DialoguePaginatorClickListener(paginator) {
                        @Override
                        public void onOption(Player player, int option) {
                            final int actualOption = option + (this.getPaginator().getIndex() * 3);
                            if (!this.pageAction(player, option)) {
                                switch (actualOption) {
                                    default:
                                        Teleport.spellTeleport(player, locations.get(actualOption - 1).getX(),
                                                locations.get(actualOption - 1).getY(),
                                                locations.get(actualOption - 1).getHeight(),
                                                true);
                                        player.getPA().closeInterfaces(true);
                                        if (ringId == 7121) {

                                        } else if (ringId == 11873) {
                                            player.removeItemFromInventory(ringId, 1);
                                            player.receiveMessage("Your ring crumbles to dust.");
                                        } else {
                                            player.removeItemFromInventory(ringId, 1);
                                            player.addItemToInventory(ringId + 1, 1);
                                        }
                                }
                            }
                        }
                    })).start(player);

        } else {
            player.receiveMessage("You must have a Slayer task to do this.");
        }
    }

    public static boolean enchantItem(Player player, int sourceId, int targetId) {
        if (sourceId == ENCHANTMENT_SCROLL || targetId == ENCHANTMENT_SCROLL) {
            switch (targetId) {
                case 11864:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 11865));
                case 19639:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 19641));
                case 19643:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 19645));
                case 19647:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 19649));
                case 21264:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 21266));
                case 13072:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 5562));
                case 13073:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 5563));
                case 4081:
                    final DialogueOptionPaginator paginator = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                            .withTitle("Which type of imbue would you like?")
                            .addOption("Salve Amulet (e) (+ 20% melee damage and accuracy)")
                            .addOption("Salve Amulet (i) (+ 15% magic/range damage and accuracy)")
                            .build();
                    player.setDialogueChain(paginator.getPageAsDialogOptions(0,
                            new DialoguePaginatorClickListener(paginator) {
                                @Override
                                public void onOption(Player player, int option) {
                                    final int actualOption = option + (this.getPaginator().getIndex() * 3);
                                    if (!this.pageAction(player, option)) {
                                        switch (actualOption) {
                                            case 1:
                                                player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 10588));
                                                player.getPA().closeInterfaces(true);
                                                break;
                                            case 2:
                                                player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 12017));
                                                player.getPA().closeInterfaces(true);
                                                break;
                                        }
                                    }
                                }
                            })).start(player);
                    return true;
                case 10588:
                case 12017:
                    return player.getActionInvoker().perform(new BaseItemOnItemAction(player, ENCHANTMENT_SCROLL, targetId, 12018));
                case ENCHANTMENT_SCROLL:
                    return enchantItem(player, ENCHANTMENT_SCROLL, sourceId);
                default:
                    return false;
            }
        }
        return false;
    }

    public static boolean applyDiamondEffect(Player player, int sourceId, int targetId) {
        if (player.getPlayerDetails().getUnlocksList().contains(SlayerUnlocks.SOCKETING.ordinal())) {
            if (isChaotic(sourceId) && isDiamond(targetId))
                return applyDiamondEffect(player, targetId, sourceId);
            else {
                if (targetId == 6605) {
                    switch (sourceId) {
                        case 4670: //gfx effect 1176 creating effect 1002
                            player.setDialogueChain(new DialogueChain().option((p, option) -> {
                                switch (option) {
                                    case 1:
                                        p.receiveMessage(ServerConstants.RED_COL + "You socket the Blood Diamond into your Rapier's hilt.");
                                        p.performAnimation(887);
                                        p.gfxDelay(1002, 60, 0);
                                        p.getActionInvoker().perform(new BaseItemOnItemAction(p, sourceId, targetId, 16389));
                                        p.getPA().closeInterfaces(true);
                                        break;
                                    case 2:
                                        p.getPA().closeInterfaces(true);
                                        break;
                                }
                            }, "Socket Item? (This Action Can't Be Undone)", "Yes", "No")).start(player);
                            return true;
                    }
                } else if(targetId == 6607) {
                    switch (sourceId) {
                        case 4670: //gfx effect 1176 creating effect 1002
                            player.setDialogueChain(new DialogueChain().option((p, option) -> {
                                switch (option) {
                                    case 1:
                                        p.receiveMessage(ServerConstants.RED_COL + "You socket the Blood Diamond into your longsword's hilt.");
                                        p.performAnimation(887);
                                        p.gfxDelay(1002, 60, 0);
                                        p.getActionInvoker().perform(new BaseItemOnItemAction(p, sourceId, targetId, 19515));
                                        p.getPA().closeInterfaces(true);
                                        break;
                                    case 2:
                                        p.getPA().closeInterfaces(true);
                                        break;
                                }
                            }, "Socket Item? (This Action Can't Be Undone)", "Yes", "No")).start(player);
                            return true;
                    }
                } else if(targetId == 6609) {
                    switch (sourceId) {
                        case 4670: //gfx effect 1176 creating effect 1002
                            player.setDialogueChain(new DialogueChain().option((p, option) -> {
                                switch (option) {
                                    case 1:
                                        p.receiveMessage(ServerConstants.RED_COL + "You socket the Blood Diamond into your maul's handle.");
                                        p.performAnimation(887);
                                        p.gfxDelay(1002, 60, 0);
                                        p.getActionInvoker().perform(new BaseItemOnItemAction(p, sourceId, targetId, 19517));
                                        p.getPA().closeInterfaces(true);
                                        break;
                                    case 2:
                                        p.getPA().closeInterfaces(true);
                                        break;
                                }
                            }, "Socket Item? (This Action Can't Be Undone)", "Yes", "No")).start(player);
                            return true;
                    }
                } else if(targetId == 6077) {
                    switch (sourceId) {
                        case 4670: //gfx effect 1176 creating effect 1002
                            player.setDialogueChain(new DialogueChain().option((p, option) -> {
                                switch (option) {
                                    case 1:
                                        p.receiveMessage(ServerConstants.RED_COL + "You socket the Blood Diamond into your bow's handle.");
                                        p.performAnimation(887);
                                        p.gfxDelay(1002, 60, 0);
                                        p.getActionInvoker().perform(new BaseItemOnItemAction(p, sourceId, targetId, 6077));
                                        p.getPA().closeInterfaces(true);
                                        p.getPlayerDetails().getSocketList().add(Socket.BLOOD_DIAMOND.ordinal());
                                        p.saveDetails();
                                        break;
                                    case 2:
                                        p.getPA().closeInterfaces(true);
                                        break;
                                }
                            }, "Socket Item? (This Action Can't Be Undone)", "Yes", "No")).start(player);
                            return true;
                    }
                }
            }
        }
        return false;
    }

    public static enum Socket {
        BLOOD_DIAMOND,ICE_DIAMOND;
    }

    private static boolean isChaotic(int itemId) {
        final int[] items = {6605, 6607, 6609,6077};
        for (int i = 0; i < items.length; i++) {
            if (itemId == items[i]) {
                return true;
            }
        }
        return false;
    }

    private static boolean isDiamond(int itemId) {
        final int[] items = {4670, 4671, 4672, 4673};
        for (int i = 0; i < items.length; i++) {
            if (itemId == items[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean dyeSlayerHelm(PlayableCharacter player, int sourceId, int targetId) {
        if (isSlayerHead(sourceId) && isSlayerHelm(targetId)) {
            return dyeSlayerHelm(player, targetId, sourceId);
        } else if (isSlayerHelm(sourceId) && isSlayerHead(targetId)) {
            switch (targetId) {
                case 7980:
                    switch (sourceId) {
                        case 11864:
                        case 19643:
                        case 19647:
                        case 21264:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 19639));
                        case 11865:
                        case 19645:
                        case 19649:
                        case 21266:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 19641));
                        case 19639:
                        case 19641:
                            player.receiveMessage("This item is already this color.");
                            return true;
                    }
                    return true;
                case 7981:
                    switch (sourceId) {
                        case 11864:
                        case 19639:
                        case 19647:
                        case 21264:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 19643));
                        case 11865:
                        case 19641:
                        case 19649:
                        case 21266:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 19645));
                        case 19643:
                        case 19645:
                            player.receiveMessage("This item is already this color.");
                            return true;
                    }
                    return true;
                case 7979:
                    switch (sourceId) {
                        case 11864:
                        case 19639:
                        case 19643:
                        case 21264:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 19647));
                        case 11865:
                        case 19641:
                        case 19645:
                        case 21266:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 19649));
                        case 19647:
                        case 19649:
                            player.receiveMessage("This item is already this color.");
                            return true;
                    }
                    return true;
                case 21275:
                    switch (sourceId) {
                        case 11864:
                        case 19639:
                        case 19647:
                        case 19643:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 21264));
                        case 11865:
                        case 19641:
                        case 19649:
                        case 19645:
                            return player.getActionInvoker().perform(new BaseItemOnItemAction(player, sourceId, targetId, 21266));
                        case 21264:
                        case 21266:
                            player.receiveMessage("This item is already this color.");
                            return true;
                    }
                    return true;
            }
        }
        return false;
    }

    public static String getAssignmentName(PlayerSlayerTask task) {
        if (task != null)
            return SlayerAssignment.values()[task.getAssignment()].toString();
        return "None";
    }

    public static boolean isDoingDuoSlayer(Player player) {
        return player.getSlayerPartner() != null;
    }

    public static void slayerKill(Player player, Npc npc, int hp) {
        if (npc != null) {
            if (player != null) {
                if (player.getPlayerDetails() != null) {
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
                                if (player.getPlayerDetails().getUnlocksList().contains(SlayerUnlocks.BIGGER_AND_BADDER.ordinal()))
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

    public static boolean isSlayerTask(int assignment, int npcId) {
        return Arrays.stream(SlayerAssignment.values()[assignment].getNpcs()).anyMatch(npc -> npc == npcId);
    }

    private static void spawn(Player player, int npcId) {
        if (ThreadLocalRandom.current().nextInt(0, 50) == 0) {
            Position tile = Region.nextOpenTileOrNull(player.getX(), player.getY(), player.getHeight());

            if (tile == null) {
                tile = new Position(player);
            }

            Npc superior = NpcHandler.spawnNpc(player, npcId, tile.getX(), tile.getY(), tile.getZ(), false, false);
            player.setUnderAttackBy(superior.npcType);
            if (superior != null) {
                if (superior instanceof SuperiorNpc) {
                    superior.getAttributeMap().put(SuperiorNpc.SPAWNED_FOR, player.getPlayerName());
                    player.getPA().sendPlainMessage("A superior foe has appeared...");
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

    public static boolean isWearingSlayerHelm(Player player) {
        for (int index = 0; index < ServerConstants.getSlayerHelms().length; index++) {
            int helmId = ServerConstants.getSlayerHelms()[index];
            if (ItemAssistant.hasItemEquipped(player, helmId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWearingImbuedSlayerHelm(Player player) {
        final int[] imbuedHelmets = {11865, 19641, 19645, 19649, 21266};
        for (int index = 0; index < imbuedHelmets.length; index++) {
            int helmId = imbuedHelmets[index];
            if (ItemAssistant.hasItemEquipped(player, helmId)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSlayerHelm(int itemId) {
        for (int index = 0; index < ServerConstants.getSlayerHelms().length; index++) {
            int helmId = ServerConstants.getSlayerHelms()[index];
            if (itemId == helmId) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSlayerHead(int itemId) {
        final int[] heads = {7980, 7981, 7979, 21275};
        for (int i = 0; i < heads.length; i++) {
            if (itemId == heads[i]) {
                return true;
            }
        }
        return false;
    }

    public static boolean consumeXpTome(Player player, int tomeId) {
        switch (tomeId) {
            case 7788:
                if (player.removeItemFromInventory(tomeId, 1))
                    Skilling.addSkillExperience(player, 20000, Skill.SLAYER.getId(), true);
                return true;
            case 7789:
                if (player.removeItemFromInventory(tomeId, 1))
                    Skilling.addSkillExperience(player, 50000, Skill.SLAYER.getId(), true);
                return true;
            case 7790:
                if (player.removeItemFromInventory(tomeId, 1))
                    Skilling.addSkillExperience(player, 100000, Skill.SLAYER.getId(), true);
                return true;
            default:
                return false;
        }
    }
}

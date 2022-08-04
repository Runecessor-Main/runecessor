package game.content.phantasye.item;

import core.GameType;
import core.ServerConstants;
import game.content.item.ItemInteraction;
import game.content.item.ItemInteractionComponent;
import game.content.phantasye.item.degradable.impl.Bonecrusher;
import game.player.Player;
import game.type.GameTypeIdentity;

@ItemInteractionComponent(
        identities = {@GameTypeIdentity(type = GameType.ANY, identity = 22114),})
public class MythicalCape implements ItemInteraction {

    public static final int ID = 22114;
    public static final int CHARGE_ID = Bonecrusher.CHARGE_ID;

    @Override
    public void operate(Player player, int id) {
        player.receiveMessage("You have: " + ServerConstants.RED_COL + player.getChargesRemainingFor(id) + "</col> charges remaining.");
    }

    @Override
    public boolean canEquip(Player player, int id, int slot) {
        return true;
    }

    public static void upgrade(Player player, int amount) {
        final int tier = player.getPlayerDetails().getMythicalCapeTier().value();
        final int FIRST_UPGRADE = 5000;
        final int SECOND_UPGRADE = 10000;
        final int THIRD_UPGRADE = 15000;
        if (tier == 0) {
            if (player.getChargesFor(ID).add(amount) >= FIRST_UPGRADE) {
                player.removeItemFromInventory(CHARGE_ID,amount);
                player.getPlayerDetails().getMythicalCapeTier().increment();
                player.receiveMessage("You upgrade your Mythical Cape to tier 1 for a 2x XP Bonus!");
            }
        } else if (tier == 1) {
            if (player.getChargesFor(ID).add(amount) >= SECOND_UPGRADE) {
                player.removeItemFromInventory(CHARGE_ID,amount);
                player.getPlayerDetails().getMythicalCapeTier().increment();
                player.receiveMessage("You upgrade your Mythical Cape to tier 2 for a 2.5x XP Bonus!");
            }
        } else if (tier == 2) {
            if (player.getChargesFor(ID).add(amount) >= THIRD_UPGRADE) {
                player.removeItemFromInventory(CHARGE_ID,amount);
                player.getPlayerDetails().getMythicalCapeTier().increment();
                player.receiveMessage("You upgrade your Mythical Cape to tier 3 for a 3x XP Bonus!");
            }
        }
    }
}

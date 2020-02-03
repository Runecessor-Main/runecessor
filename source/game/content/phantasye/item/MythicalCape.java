package game.content.phantasye.item;

import game.player.Player;

import java.text.NumberFormat;

public class MythicalCape {

    private final Player player;

    public MythicalCape(Player player) {
        this.player = player;
    }

    public void addCharge(int amount) {
        final int canAdd = Integer.MAX_VALUE - player.getPlayerDetails().getMythicalCapeCharges().value();
        if (canAdd > 0) {
            if (amount < canAdd) {
                player.getPlayerDetails().getMythicalCapeCharges().add(amount);
            } else {
                player.getPlayerDetails().getMythicalCapeCharges().add(canAdd);
            }
            player.receiveMessage("You've charged your cape. You have: " + NumberFormat.getInstance().format(player.getPlayerDetails().getMythicalCapeCharges().value()) + " charges remaining.");
        } else {
            player.receiveMessage("You can not charge your cape any more.");
        }
    }

    public void upgrade() {
        final int tier = player.getPlayerDetails().getMythicalCapeTier().value();
        final int FIRST_UPGRADE = 5000;
        final int SECOND_UPGRADE = 10000;
        final int THIRD_UPGRADE = 15000;
        if (tier == 0) {
            if (player.hasItem(20527, FIRST_UPGRADE)) {
                player.removeItemFromInventory(20527,FIRST_UPGRADE);
                player.getPlayerDetails().getMythicalCapeTier().increment();
                player.receiveMessage("You upgrade your Mythical Cape to tier 1 for a 2x XP Bonus!");
            }
        } else if (tier == 1) {
            if (player.hasItem(20527, SECOND_UPGRADE)) {
                player.removeItemFromInventory(20527,SECOND_UPGRADE);
                player.getPlayerDetails().getMythicalCapeTier().increment();
                player.receiveMessage("You upgrade your Mythical Cape to tier 2 for a 2.5x XP Bonus!");
            }
        } else if (tier == 2) {
            if (player.hasItem(20527, THIRD_UPGRADE)) {
                player.removeItemFromInventory(20527,THIRD_UPGRADE);
                player.getPlayerDetails().getMythicalCapeTier().increment();
                player.receiveMessage("You upgrade your Mythical Cape to tier 3 for a 3x XP Bonus!");
            }
        }
    }
}

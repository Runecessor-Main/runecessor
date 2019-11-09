package game.content.phantasye.item;

import game.content.bank.Bank;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.item.ItemAssistant;
import game.player.Player;
import org.menaphos.model.math.AdjustableNumber;
import org.menaphos.model.math.impl.AdjustableInteger;

import java.util.Arrays;

public class HerbSack {

    private static final int[] GRIMY_HERB = {199, 201, 203, 205, 207, 209, 211, 213, 215, 217, 219};

    private static HerbSack instance = null;

    private final Player player;

    private HerbSack(Player player) {
        this.player = player;
    }

    public static HerbSack getInstanceForPlayer(Player player) {
        return new HerbSack(player);
    }

    private boolean isGrimyHerb(int itemId) {
        return Arrays.stream(GRIMY_HERB).anyMatch(herb -> herb == itemId);
    }

    private boolean hasGrimyHerbs() {
        for (int j = 0; j < player.playerItems.length; j++) {
            final int itemId = player.playerItems[j] - 1;
            if (isGrimyHerb(itemId)) {
                return true;
            }
        }
        return false;
    }

    public void check() {
        if (player.getPlayerDetails() != null
                && player.getPlayerDetails().getHerbSack() != null
                && !player.getPlayerDetails().getHerbSack().isEmpty()) {
            final StringBuilder sb = new StringBuilder();

            player.getPlayerDetails().getHerbSack().keySet()
                    .stream()
                    .forEach(herb -> player.receiveMessage(
                            ItemAssistant.getItemName(herb) + " x" + player.getPlayerDetails().getHerbSack().get(herb).value()));

        } else {
            player.receiveMessage("Your Herb Sack is empty.");
        }
    }

    public void empty() {
        if (player.getPlayerDetails() != null && player.getPlayerDetails().getHerbSack() != null && !player.getPlayerDetails().getHerbSack().isEmpty()) {
            player.getPlayerDetails().getHerbSack().keySet()
                    .stream()
                    .filter(herb -> Bank.addItemToBank(player, herb, player.getPlayerDetails().getHerbSack().get(herb).value(), true))
                    .forEach(herb -> player.receiveMessage("Sent x" + player.getPlayerDetails().getHerbSack().get(herb) + " " +
                            ItemAssistant.getItemName(herb) + " to the bank."));
            player.getPlayerDetails().getHerbSack().clear();
            player.saveDetails();
        } else {
            player.receiveMessage("You Herb Sack is empty.");
        }
    }

    public boolean fill() {
        if (player.getPlayerDetails() != null && player.getPlayerDetails().getHerbSack() != null) {
            if (hasGrimyHerbs()) {
                for (int j = 0; j < player.playerItems.length; j++) {
                    final int itemId = player.playerItems[j] - 1;
                    if (isGrimyHerb(itemId)) {
                        if (player.getPlayerDetails().getHerbSack().containsKey(itemId)) {
                            final AdjustableNumber<Integer> count = player.getPlayerDetails().getHerbSack().get(itemId);
                            if (count.lessThan(30) && isGrimyHerb(itemId)) {
                                if (player.removeItemFromInventory(itemId, 1)) {
                                    count.increment();
                                    player.receiveMessage("Added 1x " + ItemAssistant.getItemName(itemId));
                                }
                            } else {
                                player.receiveMessage("Your Herb Sack can not hold anymore " + ItemAssistant.getItemName(itemId));
                            }
                        } else {
                            if (player.removeItemFromInventory(itemId, 1)) {
                                player.getPlayerDetails().getHerbSack().put(itemId, new AdjustableInteger(1));
                                player.receiveMessage("Added 1x " + ItemAssistant.getItemName(itemId));
                            }
                        }
                    }
                }
            } else {
                player.receiveMessage("You have no Grimy Herbs to put in the sack.");
            }
            player.saveDetails();
        }
        return false;
    }
}

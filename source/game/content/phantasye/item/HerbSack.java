package game.content.phantasye.item;

import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.item.ItemAssistant;
import game.player.Player;
import org.menaphos.model.math.AdjustableNumber;
import org.menaphos.model.math.impl.AdjustableInteger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
//            player.getPlayerDetails().getHerbSack().keySet()
////                    .stream()
////                    .filter(herb -> Bank.addItemToBank(player, herb, player.getPlayerDetails().getHerbSack().get(herb).value(), true))
////                    .forEach(herb -> player.receiveMessage("Sent x" + player.getPlayerDetails().getHerbSack().get(herb) + " " +
////                            ItemAssistant.getItemName(herb) + " to the bank."));
////            player.getPlayerDetails().getHerbSack().clear();
////            player.saveDetails();
            final DialogueOptionPaginator.DialogueOptionPaginatorBuilder builder = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player);
            builder.withTitle("which Herb Would You Like?");
            player.getPlayerDetails().getHerbSack().keySet().forEach(herb -> builder.addOption(ItemAssistant.getItemName(herb)
                    + "  x" + player.getPlayerDetails().getHerbSack().get(herb).value()));
            final DialogueOptionPaginator paginator = builder.build();

            player.setDialogueChain(paginator.getPageAsDialogOptions(0, new DialoguePaginatorClickListener(paginator) {
                @Override
                public void onOption(Player player, int option) {
                    final int actualOption = option + (this.getPaginator().getIndex() * 3);
                    if (!this.pageAction(player, option)) {
                        switch (actualOption) {
                            default:

                                final int herbId = new ArrayList<>(player.getPlayerDetails().getHerbSack().keySet()).get(actualOption - 1);
                                final int herbAmt = player.getPlayerDetails().getHerbSack().get(herbId).value();
                                player.getOutStream().createFrame(27);
                                player.getPA().sendPlainMessage(":packet:enteramounttext Enter Amount to Withdraw");
                                player.xRemoveSlot = 0;
                                player.setxInterfaceId(420);
                                player.xRemoveId = herbId;
                                break;
                        }
                    }
                }
            })).start(player);
        } else {
            player.receiveMessage("You Herb Sack is empty.");
        }
    }

    public boolean withdrawHerb(int herbId, int amount) {
        final int freeSlots = ItemAssistant.getFreeInventorySlots(player);
        if(player.getPlayerDetails().getHerbSack().get(herbId).value() >= amount) {
            if (freeSlots >= amount) {
                player.addItemToInventory(herbId, amount);
                player.receiveMessage("You withdraw " + amount + " " + ItemAssistant.getItemName(herbId) + " from the sack.");
                player.getPlayerDetails().getHerbSack().get(herbId).subtract(amount);
                player.saveDetails();
                player.getPA().closeInterfaces(true);
                return true;
            } else {
                player.receiveMessage("You do not have enough inventory space.");
                player.getPA().closeInterfaces(true);
            }
        } else {
            player.receiveMessage("You do not have that many.");
            player.getPA().closeInterfaces(true);
        }
        return false;
    }

    public boolean fill() {
        if (player.getPlayerDetails() != null && player.getPlayerDetails().getHerbSack() != null) {
            if (hasGrimyHerbs()) {
                final List<Integer> full = new ArrayList<>();
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
                                if (!full.contains(itemId)) {
                                    player.receiveMessage("Your Herb Sack can not hold anymore " + ItemAssistant.getItemName(itemId));
                                    full.add(itemId);
                                }
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

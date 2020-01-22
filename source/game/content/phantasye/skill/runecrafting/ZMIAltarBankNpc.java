package game.content.phantasye.skill.runecrafting;

import game.content.bank.Bank;
import game.content.dialogue.DialogueChain;
import game.content.dialogueold.DialogueHandler;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.npc.data.NpcDefinition;
import game.player.Player;
import org.menaphos.model.math.AdjustableNumber;
import org.menaphos.model.math.impl.AdjustableInteger;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ZMIAltarBankNpc {

    public static final int NPC_ID = 2195;

    public static void talkToPlayer(Player player) {

        final DialogueOptionPaginator paginator = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                .withTitle("Bank Options")
                .addOption("Open Bank")
                .addOption("Configure Runes")
                .build();
        player.setDialogueChain(paginator.getPageAsDialogOptions(0, new DialoguePaginatorClickListener(paginator) {
            @Override
            public void onOption(Player player, int option) {
                final int actualOption = option + (this.getPaginator().getIndex() * 3);
                if (!this.pageAction(player, option)) {
                    switch (actualOption) {
                        case 1:
                            if (removedRunesFor(player)) {
                                player.getPA().stopAllActions();
                                Bank.openUpBank(player, player.getLastBankTabOpened(), true, true);
                                player.getPA().sendMessage("You have opened the bank.");
                            } else {
                                player.receiveMessage("You need at least 20 of one of your selected runes.");
                                player.getPA().closeInterfaces(true);
                            }
                            break;
                        case 2:
                            final DialogueOptionPaginator.DialogueOptionPaginatorBuilder runePaginatorBuilder = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                                    .withTitle("Select Payment Runes");
                            final List<Rune> runeList = Arrays.stream(Rune.values())
                                    .filter(rune -> rune != Rune.WRATH)
                                    .filter(rune -> rune != Rune.MUD)
                                    .filter(rune -> rune != Rune.STEAM)
                                    .filter(rune -> rune != Rune.LAVA)
                                    .filter(rune -> rune != Rune.DUST)
                                    .filter(rune -> rune != Rune.MIST)
                                    .filter(rune -> rune != Rune.SMOKE)
                                    .collect(Collectors.toList());
                            runeList.forEach(rune -> runePaginatorBuilder.addOption(rune.toString() + " ( " + player.getPlayerDetails().getPaymentRunes().contains(rune.getItemId()) + " )"));
                            player.setDialogueChain(runePaginatorBuilder.build().getPageAsDialogOptions(0, new DialoguePaginatorClickListener(runePaginatorBuilder.build()) {
                                @Override
                                public void onOption(Player player, int option) {
                                    final int actualOption = option + (this.getPaginator().getIndex() * 3);
                                    if (!this.pageAction(player, option)) {
                                        final Rune rune = runeList.get(actualOption - 1);
                                        if (!player.getPlayerDetails().getPaymentRunes().contains(rune.getItemId())) {
                                            player.getPlayerDetails().getPaymentRunes().add(rune.getItemId());
                                            player.saveDetails();
                                            player.getPA().closeInterfaces(true);
                                        } else {
                                            player.receiveMessage("You already have selected that rune.");
                                            player.getPA().closeInterfaces(true);
                                        }
                                    }
                                }
                            })).start(player);
                            break;
                    }
                }
            }
        })).start(player);

    }

    private static boolean removedRunesFor(Player player) {
        if (player.getPlayerDetails().getPaymentRunes().isEmpty()) {
            player.receiveMessage("Please configure your payment runes.");
            player.getPA().closeInterfaces(true);
        } else {
            final AdjustableNumber<Integer> runeId = new AdjustableInteger(-1);
            player.getPlayerDetails().getPaymentRunes().stream()
                    .filter(rune -> player.hasItem(rune, 20))
                    .findAny().ifPresent(runeId::setValue);
            return player.removeItemFromInventory(runeId.value(),20);
        }
        return false;
    }
}

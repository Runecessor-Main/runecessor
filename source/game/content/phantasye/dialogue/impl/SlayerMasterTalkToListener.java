package game.content.phantasye.dialogue.impl;

import game.content.dialogue.DialogueChain;
import game.content.dialogueold.DialogueHandler;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.content.phantasye.skill.support.slayer.master.SlayerMaster;
import game.npc.data.NpcDefinition;
import game.player.Player;

public class SlayerMasterTalkToListener extends DialoguePaginatorClickListener {

    private final SlayerMaster master;

    public SlayerMasterTalkToListener(DialogueOptionPaginator paginator, SlayerMaster master) {
        super(paginator);
        this.master = master;
    }

    @Override
    public void onOption(Player player, int option) {
        final int actualOption = option + (this.getPaginator().getIndex() * 3);
        if (!this.pageAction(player, option)) {
            switch (actualOption) {
                case 1:
                    master.assignTaskTo(player);
                    player.getPA().closeInterfaces(true);
                    break;
                case 2:
                    master.openShopFor(player);
                    break;
                case 3:
                    master.cancelTaskFor(player);
                    break;
                case 4:
                    master.extendTaskFor(player);
                    player.getPA().closeInterfaces(true);
                    break;
                case 5:
                    master.sendPreferDialog(player);
                    break;
                case 6:
                    master.sendBlockDialog(player);
                    break;
                case 7:
                    master.sendSocialSlayerInfoDialog(player);
                    break;
                case 8:
                    master.sendUnlockDialog(player);
                    break;
                case 9:
                    final DialogueOptionPaginator paginator = new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                            .withTitle("Create Hasta? (500k)")
                            .addOption("Yes")
                            .addOption("No")
                            .build();
                    player.setDialogueChain(
                            new DialogueChain()
                                    .npc(NpcDefinition.getDefinitions()[6797], DialogueHandler.FacialAnimation.DEFAULT, "I can create an Zamorakian Hasta for you", "it will cost you 500k Coins though.")
                                    .option(new DialoguePaginatorClickListener(paginator) {
                                        @Override
                                        public void onOption(Player player, int option) {
                                            final int actualOption = option + (this.getPaginator().getIndex() * 3);
                                            if (!this.pageAction(player, option)) {
                                                switch (actualOption) {
                                                    case 1:
                                                        if (player.removeItemFromInventory(995, 500000)
                                                                && player.removeItemFromInventory(11824, 1)
                                                                && player.addItemToInventory(11889, 1)) {
                                                            player.getPA().closeInterfaces(true);
                                                            player.setDialogueChain(new DialogueChain().statement("Nieve turns your Zamorakian Spear into a Zamorakian Hasta!")).start(player);
                                                        } else {
                                                            player.getPA().closeInterfaces(true);
                                                            player.setDialogueChain(new DialogueChain().statement("You do not have all the materials needed.")).start(player);
                                                        }
                                                        break;
                                                    case 2:
                                                        player.getPA().closeInterfaces(true);
                                                        break;
                                                }
                                            }
                                        }
                                    }, paginator.getTitle(), paginator.getOptions()))
                            .start(player);
                    break;
            }
        }
    }
}

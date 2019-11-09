package game.content.phantasye.dialogue.impl;

import core.ServerConstants;
import game.content.dialogue.DialogueChain;
import game.content.dialogueold.DialogueHandler;
import game.content.miscellaneous.Teleport;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.content.phantasye.event.hween.CommunityGift;
import game.content.phantasye.event.hween.GraveDigger;
import game.npc.data.NpcDefinition;
import game.player.Player;

import java.text.NumberFormat;

public class HalloweenPaginatorListener extends DialoguePaginatorClickListener {
    public HalloweenPaginatorListener(DialogueOptionPaginator paginator) {
        super(paginator);
    }

    @Override
    public void onOption(Player player, int option) {
        final int actualOption = option + (this.getPaginator().getIndex() * 3);
        if (!this.pageAction(player, option)) {
            switch (actualOption) {
                case 1:
                    player.getPA().closeInterfaces(true);
                    DialogueOptionPaginator paginator =
                            new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                                    .addOption("It's that time of year again where everyone I've laid to rest")
                                    .addOption("thinks they can just get up and leave. Well I've had it!")
                                    .addOption("I'm leaving the gate open go on in and loot those graves.")
                                    .addOption("That will show them not to leave.")
                                    .build();
                    DialogueOptionPaginator paginator2 =
                            new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                                    .addOption("I'll let you keep whatever you find and I'll tel you what.")
                                    .addOption("Loot enough graves and I'll let you buy some powerful items")
                                    .addOption("from my shop and if that's not enough. I'll even award everyone")
                                    .addOption("if you all loot enough graves!")
                                    .build();
                    player.setDialogueChain(
                            new DialogueChain()
                                    .npc(NpcDefinition.getDefinitions()[5567], DialogueHandler.FacialAnimation.ANGER_1, paginator.getOptions())
                                    .npc(NpcDefinition.getDefinitions()[5567], DialogueHandler.FacialAnimation.DEFAULT, paginator2.getOptions()))
                            .start(player);
                    break;
                case 2:
                    player.getShops().openShop(44);
                    player.receiveMessage("You have " + ServerConstants.RED_COL + player.getPlayerDetails().getGraveDiggerProperties().getPoints().value() + "</col> Slayer Points.");
                    break;
                case 3:
                    DialogueOptionPaginator paginator3 =
                            new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                                    .withTitle("Purchase Attempts")
                                    .addOption("Pay With Coins (" + NumberFormat.getInstance().format(GraveDigger.ATTEMPT_COST_GP) + ")")
                                    .addOption("Pay with Donator Tokens (30)")
                                    .build();
                    player.setDialogueChain(paginator3.getPageAsDialogOptions(0, new DialoguePaginatorClickListener(paginator3) {
                        @Override
                        public void onOption(Player player, int option) {
                            switch (option) {
                                case 1:
                                    if (player.removeItemFromInventory(995, GraveDigger.ATTEMPT_COST_GP)) {
                                        player.receiveMessage("You've purchased an attempt. You now have "
                                                + (player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().increment()
                                                + player.getPlayerDetails().getGraveDiggerProperties().getAttempts().value())
                                                + " attempts.");
                                        GraveDigger.getInstance().saveProperties(player);
                                        player.getPA().closeInterfaces(true);
                                    } else {
                                        player.getPA().closeInterfaces(true);
                                        player.receiveMessage("You do not have enough coins.");
                                    }
                                    break;
                                case 2:
                                    if (player.removeItemFromInventory(7478, GraveDigger.ATTEMPT_COST_DT)) {
                                        player.receiveMessage("You've purchased an attempt. You now have "
                                                + (player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().increment()
                                                + player.getPlayerDetails().getGraveDiggerProperties().getAttempts().value())
                                                + " attempts.");
                                        GraveDigger.getInstance().saveProperties(player);
                                        player.getPA().closeInterfaces(true);
                                    } else {
                                        player.getPA().closeInterfaces(true);
                                        player.receiveMessage("You do not have enough tokens.");
                                    }
                                    break;
                            }
                        }
                    })).start(player);
                    break;
                case 4:
                    Teleport.spellTeleport(player,1930,4996,0,true);
                    player.getPA().closeInterfaces(true);
                    break;
                case 5:
                    new CommunityGift(1);
                    player.getPA().closeInterfaces(true);
                    break;
            }
        }
    }
}

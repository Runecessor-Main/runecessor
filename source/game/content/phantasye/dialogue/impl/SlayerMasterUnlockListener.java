package game.content.phantasye.dialogue.impl;

import game.content.dialogue.DialogueChain;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.content.phantasye.skill.slayer.SlayerSkill;
import game.content.phantasye.skill.slayer.SlayerUnlocks;
import game.content.skilling.Slayer;
import game.player.Player;

public class SlayerMasterUnlockListener extends DialoguePaginatorClickListener {

    public SlayerMasterUnlockListener(DialogueOptionPaginator paginator) {
        super(paginator);
    }

    @Override
    public void onOption(Player player, int option) {
        try {
            final int actualOption = option + (this.getPaginator().getIndex() * 3);
            final int unlockIndex = actualOption - 1;
            final SlayerUnlocks unlock = SlayerUnlocks.values()[unlockIndex];
            if (!this.pageAction(player, option)) {
                switch (actualOption) {
                    default:
                        if (SlayerSkill.unlock(player, unlock)) {
                            if (player.getPlayerDetails().getSlayerPoints().value() >= unlock.getCost()) {
                                player.getPA().closeInterfaces(true);
                                player.getPlayerDetails().getUnlocksList().add(unlock.ordinal());
                                player.setDialogueChain(new DialogueChain().statement("You've unlocked: " + unlock.getDescription())).start(player);
                                player.saveDetails();
                            } else {
                                player.getPA().closeInterfaces(true);
                                player.receiveMessage("You need at least  " + SlayerUnlocks.BOSS_SLAYER.getCost() + " Slayer Points to unlock this.");
                            }
                        } else {
                            player.getPA().closeInterfaces(true);
                            player.setDialogueChain(new DialogueChain().statement("You already unlocked this.")).start(player);
                        }
                        break;

                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            player.getPA().closeInterfaces(true);
        }
    }
}

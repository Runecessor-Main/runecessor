package game.content.phantasye.dialogue.impl;

import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.content.phantasye.skill.slayer.master.SlayerMaster;
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
            System.out.println(actualOption);
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
                    player.getPA().closeInterfaces(true);
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
            }
        }
    }
}

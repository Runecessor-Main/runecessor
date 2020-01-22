package game.content.phantasye.dialogue.impl;

import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.content.phantasye.skill.slayer.master.SlayerMaster;
import game.player.Player;

public class ResetTaskPaginatorListener extends DialoguePaginatorClickListener {

    private final SlayerMaster slayerMaster;

    public ResetTaskPaginatorListener(DialogueOptionPaginator paginator, SlayerMaster slayerMaster) {
        super(paginator);
        this.slayerMaster = slayerMaster;
    }

    @Override
    public void onOption(Player player, int option) {
        final int actualOption = option + (this.getPaginator().getIndex() * 3);
        if (!this.pageAction(player, option)) {
            switch (actualOption) {
                case 1:
                    slayerMaster.cancelTaskForCoins(player);
                    player.getPA().closeInterfaces(true);
                    break;
                case 2:
                    slayerMaster.cancelTaskForPoints(player);
                    player.getPA().closeInterfaces(true);
                    break;
            }
        }
    }
}

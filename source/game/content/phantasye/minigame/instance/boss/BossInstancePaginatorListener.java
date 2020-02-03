package game.content.phantasye.minigame.instance.boss;

import game.content.dialogue.DialogueChain;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.player.Player;

import java.util.Objects;

public class BossInstancePaginatorListener extends DialoguePaginatorClickListener {

    public BossInstancePaginatorListener(DialogueOptionPaginator paginator) {
        super(paginator);
    }

    @Override
    public void onOption(Player player, int option) {
        try {
            final int actualOption = option + (this.getPaginator().getIndex() * 3);
            final int bossIndex = actualOption - 1;
            final BossInstanceController.Boss boss = BossInstanceController.Boss.values()[bossIndex];
            if (!this.pageAction(player, option)) {
                switch (boss) {
                    case ZULRAH:
                        if (player.getPetSummoned()) {
                            player.setDialogueChain(new DialogueChain().statement("You must dismiss your pet", "before entering the Zulrah Instance.")).start(player);
                        } else {
                            player.getPA().closeInterfaces(true);
                            BossInstanceController.getInstance().selectBoss(Objects.requireNonNull(boss), player);
                        }
                        break;
                    default:
                        player.getPA().closeInterfaces(true);
                        BossInstanceController.getInstance().selectBoss(Objects.requireNonNull(boss), player);
                        break;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            player.getPA().closeInterfaces(true);
        }
    }
}

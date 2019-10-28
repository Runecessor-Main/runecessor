package game.content.phantasye.dialogue.impl;

import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.DialoguePaginatorClickListener;
import game.player.Player;

public class TestPaginatorClickListener extends DialoguePaginatorClickListener {

    public TestPaginatorClickListener(DialogueOptionPaginator paginator) {
        super(paginator);
    }

    @Override
    public void onOption(Player player, int option) {
        final int actualOption = option + (this.getPaginator().getIndex() * 3);
        if (!this.pageAction(player, option)) {
            switch (actualOption) {
                default:
                    System.out.println(actualOption);
                    break;
            }
        }
    }
}

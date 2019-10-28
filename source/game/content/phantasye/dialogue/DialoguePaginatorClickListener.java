package game.content.phantasye.dialogue;

import game.content.dialogue.listener.impl.ClickOptionDialogueListener;
import game.player.Player;

import java.util.Objects;

public abstract class DialoguePaginatorClickListener implements ClickOptionDialogueListener {

    private final DialogueOptionPaginator paginator;

    public DialoguePaginatorClickListener(DialogueOptionPaginator paginator) {
        this.paginator = paginator;
    }

    protected boolean pageAction(Player player, int option) {
        if(!paginator.isLastPage() && option == Objects.requireNonNull(paginator.getPage(paginator.getIndex())).length) {
            paginator.nextPage();
            player.getPA().closeInterfaces(true);
            player.setDialogueChain(paginator.getPageAsDialogOptions(paginator.getIndex(),this)).start(player);
            return true;
        } else if(paginator.isLastPage() && option == Objects.requireNonNull(paginator.getPage(paginator.getIndex())).length ) {
            player.getPA().closeInterfaces(true);
            return true;
        }
        return false;
    }

    public DialogueOptionPaginator getPaginator() {
        return paginator;
    }
}

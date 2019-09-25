package game.content.phantasye.minigame.instance.boss.chain;

import game.content.dialogue.DialogueChain;
import game.player.Player;

public class OptionsBossInstanceChain implements BossInstanceChain {

    private BossInstanceChain nextChain;

    @Override
    public void setNextChain(BossInstanceChain chain) {
        this.nextChain = chain;
    }

    @Override
    public boolean start(Player player) {
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
            player.getPA().closeInterfaces(true);

        }, "Select a Boss", "Tekton"));
        return nextChain.start(player);
    }
}

package game.content.phantasye.minigame.instance.boss.chain;

import game.content.phantasye.minigame.instance.boss.BossInstanceController;
import game.player.Player;

public class ConsumeTicketBossInstanceChain implements BossInstanceChain {

    private BossInstanceChain nextChain;

    @Override
    public void setNextChain(BossInstanceChain chain) {
        this.nextChain = chain;
    }

    @Override
    public boolean start(Player player) {
        if(player.removeItemFromInventory(BossInstanceController.BOSS_TICKET,1)) {
            return nextChain.start(player);
        } else {
            player.receiveMessage("You do not have enough boss tickets.");
            return false;
        }
    }
}

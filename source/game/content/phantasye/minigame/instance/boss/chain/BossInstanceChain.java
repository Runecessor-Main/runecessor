package game.content.phantasye.minigame.instance.boss.chain;

import game.player.Player;

public interface BossInstanceChain {

    void setNextChain(BossInstanceChain chain);

    boolean start(Player player);
}

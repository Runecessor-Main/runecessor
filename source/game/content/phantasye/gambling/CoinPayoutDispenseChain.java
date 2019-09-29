package game.content.phantasye.gambling;

import game.player.Player;

public class CoinPayoutDispenseChain {

    private final CurrencyDispenseChain chain;

    public CoinPayoutDispenseChain(Player player) {
        this.chain = new TokenDispenser(player);

        chain.setNextChain(new CoinDispenser(player));
    }

    public void payout(int amount) {
        chain.dispense(amount);
    }
}

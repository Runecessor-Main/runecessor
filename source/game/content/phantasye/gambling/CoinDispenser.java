package game.content.phantasye.gambling;

import game.player.Player;

public class CoinDispenser implements CurrencyDispenseChain {
    private static final int COIN_VALUE = 1;

    private CurrencyDispenseChain chain;

    private final Player player;

    public CoinDispenser(Player player) {
        this.player = player;
    }

    @Override
    public void setNextChain(CurrencyDispenseChain chain) {
        this.chain = chain;
    }

    @Override
    public void dispense(int coins) {
        if(coins >= COIN_VALUE){
            int num = coins/COIN_VALUE;
            int remainder = coins % COIN_VALUE;
            player.addItemToInventory(995,num);
            System.out.println("Dispensing "+num+" coins");
            if(remainder !=0) this.chain.dispense(remainder);
        }else{
            this.chain.dispense(coins);
        }
    }
}

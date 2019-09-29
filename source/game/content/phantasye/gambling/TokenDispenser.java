package game.content.phantasye.gambling;

import game.player.Player;

public class TokenDispenser implements CurrencyDispenseChain {

    private static final int TOKEN_VALUE = 1000000;

    private CurrencyDispenseChain chain;

    private final Player player;

    public TokenDispenser(Player player) {
        this.player = player;
    }

    @Override
    public void setNextChain(CurrencyDispenseChain chain) {
        this.chain = chain;
    }

    @Override
    public void dispense(int coins) {
        if(coins >= TOKEN_VALUE){
            int num = coins/TOKEN_VALUE;
            int remainder = coins % TOKEN_VALUE;
            player.addItemToInventory(13204,num);
            System.out.println("Dispensing "+num+" tokens");
            if(remainder !=0) this.chain.dispense(remainder);
        }else{
            this.chain.dispense(coins);
        }
    }
}

package game.content.phantasye.gambling;

public interface CurrencyDispenseChain {

    void setNextChain(CurrencyDispenseChain chain);

    void dispense(int coins);
}

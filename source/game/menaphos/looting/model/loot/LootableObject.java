package game.menaphos.looting.model.loot;


import java.util.List;

public class LootableObject extends LootableContainer {

    private final int keyId;
    private final int keyQty;
    private final int animationId;
    private final String openMessage;
    private final String attemptMessage;

    public LootableObject(int id, String name, List<Loot> lootList, int keyId, int keyQty, String openMessage, String attemptMessage, int animationId) {
        super(id,name,lootList);
        this.keyId = keyId;
        this.keyQty = keyQty;
        this.openMessage = openMessage;
        this.attemptMessage = attemptMessage;
        this.animationId = animationId;
    }

    public int getKeyId() {
        return keyId;
    }

    public int getKeyQty() {
        return keyQty;
    }

    public int getAnimationId() {
        return animationId;
    }

    public String getAttemptMessage() {
        return attemptMessage;
    }

    public String getOpenMessage() {
        return openMessage;
    }

}

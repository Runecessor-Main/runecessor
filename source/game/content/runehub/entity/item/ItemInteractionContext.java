package game.content.runehub.entity.item;

public class ItemInteractionContext {

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getUsedId() {
        return usedId;
    }

    public int getUsedAmount() {
        return usedAmount;
    }

    public int getUsedWithAmount() {
        return usedWithAmount;
    }

    public int getUsedWithId() {
        return usedWithId;
    }

    public ItemInteractionContext(int x, int y, int z, int usedId, int usedWithId, int usedAmount, int usedWithAmount) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.usedId = usedId;
        this.usedWithId = usedWithId;
        this.usedAmount = usedAmount;
        this.usedWithAmount = usedWithAmount;
    }

    private final int x,y,z;
    private final int usedId,usedWithId;
    private final int usedAmount,usedWithAmount;
}

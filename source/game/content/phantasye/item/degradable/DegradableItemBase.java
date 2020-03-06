package game.content.phantasye.item.degradable;

import org.menaphos.model.math.impl.AdjustableDouble;

public abstract class DegradableItemBase implements Degradable {

    private final int itemId;
    private final AdjustableDouble charge;
    private final boolean rechargable;

    public DegradableItemBase(int itemId, double charge, boolean rechargable) {
        this.itemId = itemId;
        this.charge = new AdjustableDouble(charge);
        this.rechargable = rechargable;
    }

    public abstract void onDegrade();

    public int getItemId() {
        return itemId;
    }

    public AdjustableDouble getCharge() {
        return charge;
    }

    public boolean isRechargable() {
        return rechargable;
    }

    @Override
    public double getCurrentCharges() {
        return charge.value();
    }
}

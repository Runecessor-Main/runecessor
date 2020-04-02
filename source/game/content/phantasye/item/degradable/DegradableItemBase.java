package game.content.phantasye.item.degradable;

import org.menaphos.model.math.impl.AdjustableDouble;

import java.text.NumberFormat;

public abstract class DegradableItemBase implements Degradable {

    private final int itemId;
    private final AdjustableDouble charge;
    private final boolean rechargable;

    public DegradableItemBase(int itemId, double charge, boolean rechargable) {
        this.itemId = itemId;
        this.charge = new AdjustableDouble(charge);
        this.rechargable = rechargable;
    }

    public static int addCharge(int toAdd, int stored) {
        final int canAdd = Integer.MAX_VALUE - stored;
        if (canAdd > 0) {
            if (toAdd < canAdd) {
                return toAdd;
            } else {
                return canAdd;
            }
        }
        return 0;
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

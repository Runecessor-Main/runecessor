package game.content.phantasye.event.hween;

import org.menaphos.model.math.impl.AdjustableInteger;

public final class GraveDiggerProperties {

    private final AdjustableInteger points;
    private final AdjustableInteger attempts;
    private final AdjustableInteger paidAttempts;


    public GraveDiggerProperties() {
        this.points = new AdjustableInteger(0);
        this.attempts = new AdjustableInteger(3);
        this.paidAttempts = new AdjustableInteger(0);
    }

    public AdjustableInteger getPaidAttempts() {
        return paidAttempts;
    }

    public AdjustableInteger getAttempts() {
        return attempts;
    }

    public AdjustableInteger getPoints() {
        return points;
    }
}

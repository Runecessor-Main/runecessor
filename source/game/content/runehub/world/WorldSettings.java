package game.content.runehub.world;

import org.runehub.api.model.math.impl.AdjustableLong;

import java.util.HashMap;
import java.util.Map;

public class WorldSettings {

    private final double powerModifer;
    private final double efficiencyModifier;
    private final double gainsModifier;

    private final AdjustableLong bonusXpTimer;
    private final AdjustableLong doubleDropRateTimer;

    private final Map<Integer,AdjustableLong> skillEfficiencyTimer;
    private final Map<Integer,AdjustableLong> skillPowerTimer;
    private final Map<Integer,AdjustableLong> skillGainsTimer;

    private long commodityTraderVisitTimestamp;

    public WorldSettings() {
        this.bonusXpTimer = new AdjustableLong(0L);
        this.doubleDropRateTimer = new AdjustableLong(0L);

        this.powerModifer = 1.5D;
        this.efficiencyModifier = 1.5D;
        this.gainsModifier = 1.5D;

        this.skillEfficiencyTimer = new HashMap<>();
        this.skillGainsTimer = new HashMap<>();
        this.skillPowerTimer = new HashMap<>();
    }

    public Map<Integer, AdjustableLong> getSkillEfficiencyTimer() {
        return skillEfficiencyTimer;
    }

    public Map<Integer, AdjustableLong> getSkillGainsTimer() {
        return skillGainsTimer;
    }

    public Map<Integer, AdjustableLong> getSkillPowerTimer() {
        return skillPowerTimer;
    }

    public long getCommodityTraderVisitTimestamp() {
        return commodityTraderVisitTimestamp;
    }

    public void setCommodityTraderVisitTimestamp(long commodityTraderVisitTimestamp) {
        this.commodityTraderVisitTimestamp = commodityTraderVisitTimestamp;
    }

    public double getEfficiencyModifier() {
        return efficiencyModifier;
    }

    public double getGainsModifier() {
        return gainsModifier;
    }

    public double getPowerModifer() {
        return powerModifer;
    }

    public AdjustableLong getBonusXpTimer() {
        return bonusXpTimer;
    }

    public AdjustableLong getDoubleDropRateTimer() {
        return doubleDropRateTimer;
    }
}

package game.content.phantasye.metric;

import org.menaphos.model.math.impl.AdjustableInteger;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class NpcMetric {

    private final int npcId;
    private final List<Double> killTimeList;
    private final AdjustableInteger kills;
    private double average;

    public NpcMetric(int npcId, double killTime) {
        this.npcId = npcId;
        this.kills = new AdjustableInteger(0);
        this.average = killTime;
        this.killTimeList = new ArrayList<>();
    }

    public void updateAverage() {
        if (killTimeList.size() == 100) {
            final DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);
            final double avg = killTimeList.stream().mapToDouble(Double::doubleValue).sum() / killTimeList.size();
            this.setAverage(Double.parseDouble(df.format(avg)));
            killTimeList.clear();
        }
    }

    public List<Double> getKillTimeList() {
        return killTimeList;
    }

    public AdjustableInteger getKills() {
        return kills;
    }

    public double getAverage() {
        return average;
    }

    public void setAverage(double average) {
        this.average = average;
    }

    public int getNpcId() {
        return npcId;
    }
}

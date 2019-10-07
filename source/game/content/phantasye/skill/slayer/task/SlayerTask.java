package game.content.phantasye.skill.slayer.task;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.menaphos.looting.model.Range;

public class SlayerTask {

    private final int assignment;
    private final Range amount;
    private final Range extendedAmount;
    private int weight;

    public SlayerTask(SlayerAssignment assignment, Range amount, Range extendedAmount, int weight) {
        this.assignment = assignment.ordinal();
        this.amount = amount;
        this.extendedAmount = extendedAmount;
        this.weight = weight;
    }

    public String getName() {
        return getAssignment().name();
    }

    public SlayerAssignment getAssignment() {
        return SlayerAssignment.values()[assignment];
    }

    public int getWeight() {
        return weight;
    }

    public Range getAmount() {
        return amount;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Range getExtendedAmount() {
        return extendedAmount;
    }
}

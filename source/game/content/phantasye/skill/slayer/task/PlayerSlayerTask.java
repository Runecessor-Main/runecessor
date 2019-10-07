package game.content.phantasye.skill.slayer.task;

import game.menaphos.looting.model.Range;
import org.menaphos.model.math.impl.AdjustableInteger;

public class PlayerSlayerTask {

    private final int assignment;
    private final AdjustableInteger amount;
    private final int master;

    public PlayerSlayerTask(int assignment, int amount, int master) {
        this.assignment = assignment;
        this.amount = new AdjustableInteger(amount);
        this.master = master;
    }

    public PlayerSlayerTask(SlayerTask task, int master) {
        this(task.getAssignment().ordinal(), Range.getIntFromRange(task.getAmount()),master);
    }

    public static PlayerSlayerTask emptyTask() {
        return new PlayerSlayerTask(-1,-1,-1);
    }

    public int getMaster() {
        return master;
    }

    public AdjustableInteger getAmount() {
        return amount;
    }

    public int getAssignment() {
        return assignment;
    }
}

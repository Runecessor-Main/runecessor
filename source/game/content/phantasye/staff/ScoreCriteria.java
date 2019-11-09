package game.content.phantasye.staff;

import org.menaphos.model.math.impl.AdjustableInteger;

public class ScoreCriteria {

    private final AdjustableInteger score;
    private final int id;
    private final String title;

    public ScoreCriteria(int id, String title) {
        this.score = new AdjustableInteger(0);
        this.id = id;
        this.title = title;
    }

    public AdjustableInteger getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public int getId() {
        return id;
    }
}

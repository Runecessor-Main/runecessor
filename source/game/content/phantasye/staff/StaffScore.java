package game.content.phantasye.staff;

import game.player.Player;
import org.menaphos.model.math.impl.AdjustableInteger;

import java.util.ArrayList;
import java.util.List;

public final class StaffScore {

    private final AdjustableInteger score;
    private final String staffName;
    private final List<ScoreCriteria> criteriaList;
    private int rank;

    public StaffScore(Player player) {
        this.score = new AdjustableInteger(0);
        this.criteriaList = new ArrayList<>();
        this.staffName = player.getPlayerName();
        this.rank = player.getRights();
    }

    public AdjustableInteger getScore() {
        return score;
    }

    public String getStaffName() {
        return staffName;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}

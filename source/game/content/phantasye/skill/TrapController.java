package game.content.phantasye.skill;

import game.player.Player;

import java.util.ArrayList;
import java.util.List;

public final class TrapController {

    private final List<Trap> trapList;
    private final Player owner;

    public TrapController(Player owner) {
        this.trapList = new ArrayList<>();
        this.owner = owner;
    }

    public boolean lay(Trap trap) {
        if(trapList.size() < 5) {
            return trapList.add(trap);
        }
        return false;
    }

    public Player getOwner() {
        return owner;
    }

    public List<Trap> getTrapList() {
        return trapList;
    }
}

package game.content.phantasye.actions;

import game.player.Player;
import org.menaphos.action.impl.AbstractAction;

public class RankPlayerAction extends AbstractAction {

    private final Player player;
    private final int rank;

    public RankPlayerAction(Player player, int rank) {
        this.player = player;
        this.rank = rank;
    }

    @Override
    public boolean invoke() {
        if(player.playerRights != rank) {
            player.playerRights = rank;
            return true;
        }
        return false;
    }
}

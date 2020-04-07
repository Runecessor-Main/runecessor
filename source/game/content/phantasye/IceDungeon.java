package game.content.phantasye;

import game.content.miscellaneous.Teleport;
import game.object.ObjectEvent;
import game.player.Player;

public class IceDungeon {

    public static void teleportToIceQueen(Player player) {
        final int x = 2858;
        final int y = 9917;
        Teleport.spellTeleport(player, x, y, 0, false);
    }

    public static void climbLadder(int x, int y, Player player) {
        if(y > 9000) {
            ObjectEvent.climbUpLadder(player, x, y - 6400, 0);
        } else {
            ObjectEvent.climbUpLadder(player, x, y + 6400, 0);
        }
    }
}

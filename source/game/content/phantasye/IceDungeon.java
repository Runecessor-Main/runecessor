package game.content.phantasye;

import game.content.miscellaneous.Teleport;
import game.player.Player;
import org.menaphos.model.world.location.Location;

public class IceDungeon {


    public static void enterIceDungeon(Player player) {

    }

    public static void teleportToIceQueen(Player player) {
        final int x = 2858;
        final int y = 9917;
        Teleport.spellTeleport(player,x,y,0,false);
    }

    public static void enterIceQueenLair(Player player) {
        player.startAnimation(828);
        player.moveTo(new Location(2860,9919));
    }

}

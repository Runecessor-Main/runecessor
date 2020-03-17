package game.content.peep.cycledays;

import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

public class DesertHeat {

    public static final int SW_X = 0;
    public static final int SW_Y = 0;
    public static final int NE_X = 0;
    public static final int NE_Y = 0;
    public static final Region DESERT_REGION = new Region(new Location(SW_X,SW_Y),new Location(NE_X,NE_Y));

    public static void process(Player player) {
        //TODO HEAT STUFF
    }
}

package game.object.click;

import core.GameType;
import core.Plugin;
import core.Server;
import game.content.miscellaneous.SpellBook;
import game.content.packet.preeoc.ClickObjectPreEoc;
import game.player.Player;

public class FourthClickObject {

	public static void fourthClickObject(Player player, int objectType, int objectX, int objectY) {
		game.object.custom.Object object = Server.objectManager.isServerGeneratedObject(player.getObjectX(), player.getObjectY(), player.getHeight());
		
		if (ClickObjectPreEoc.fourthClickObject(player, objectType, objectX, objectY)) {
			return;
		}
		if (!GameType.isOsrs()) {
			return;
		}
		player.turnPlayerTo(player.getObjectX(), player.getObjectY());
		player.clickObjectType = 0;
		
		if(object != null) {
			if (object.hasMenuAction(player, 4)) {
				return;
			}
		}
		
		switch (objectType) {
			// Altar of the occult.
			case 29150:
				SpellBook.switchToLunar(player);
				break;
			default:
				if (Plugin.execute("fourth_click_object_" + objectType, player)) {
				} else {
					player.getPA().sendMessage("Nothing interesting happens.");
				}
				break;
		}
	}

}

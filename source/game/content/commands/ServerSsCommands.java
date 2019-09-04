package game.content.commands;

import game.player.Player;

public class ServerSsCommands {
	public static boolean serverss(Player player, String command) {
		
	 if (command.startsWith("kick")) {
			AdministratorCommand.kick(player, command);
			return true;

	 }
	 if (command.startsWith("unjail")) {
		AdministratorCommand.unJail(player, command, false);
		return true;
	 }	
	if (command.equals("pos") || command.equals("mypos")) {
		player.getPA().sendMessage(
				String.format("position {x=%s, y=%s, z=%s}", player.getX(), player.getY(), player.getHeight()));
		return true;
	 }	
	 if (command.startsWith("xteleto")) {
		 AdministratorCommand.xTeleTo(player, command);
		 return true;

	  }
	 if (command.startsWith("xteletome")) {
		 AdministratorCommand.xTeleToMe(player, command);
		 return true;

	 }
	  if (command.startsWith("jail")) {
			AdministratorCommand.jail(player, command);
			return true;

	 }	
		return false;
		
	
}


}
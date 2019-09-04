package game.content.miscellaneous;

import game.content.miscellaneous.Teleport;
import game.player.Player;

/**
 * 
 * @author Adam_#6723
 * @Date 08/01/2019
 */

public class TeleportHandler1 {

	protected Player player;

	public TeleportHandler1(Player player) {
		this.player = player;
	}
	//**
	/*  Opens The interface on the default tab, which is bosses e.g. the first
	 /* category/tab.
	 */
	public void open() {
		player.setTeleportType1(TeleportType1.BOSSES);
		switchTab(52094);
		player.getPA().displayInterface(13400);
	}

	 /*
	  * Another open method, which can be used to open a specific TeleportType of your choosing.
	  */
	public void open(TeleportType1 type) {
		player.setTeleportType1(type);
		switchTab(52094);
		player.getPA().displayInterface(13400);
	}
	 
	 /**
	  * Handles clicking each of the tabs so that it lists the accurate information
	  * @param buttonid
	 * @return 
	  */
	public boolean switchTab(int buttonid) {
		switch (buttonid) {
		case 52094:
			player.setTeleportType1(TeleportType1.BOSSES);
			switchData();
			break;
		case 52095:
			player.setTeleportType1(TeleportType1.MONSTERS);
			switchData();
			break;
		case 52096:
			player.setTeleportType1(TeleportType1.WILDERNESS);
			switchData();
			break;
		case 52097:
			player.setTeleportType1(TeleportType1.SKILLING);
			switchData();
			break;
		case 52098:
			player.setTeleportType1(TeleportType1.MINIGAMES);
			switchData();
			break;
		case 52099:
			player.setTeleportType1(TeleportType1.CITIES);
			switchData();
			break;
		default:
			break;
		}
		return false;
	}

	//**
	 /*.Te Handles **Switching** of the tabs, so that it lists the correct information
	 /* for each category.
	 */
	public void switchData() {
		int count = 13431;
		for (int i = 13431; i < 13460; i++) {
			player.getPA().sendFrame126("", i); // boom u did it thats it
		}
		
		for (TeleportData1 data : TeleportData1.values()) {
			if (data.getType() == player.getTeleportType1()) {
				player.getPA().sendFrame126(data.getName(), count++);
				if (count >= 13461) {
					System.err.println("You are placing a teleport, where the interface ID Stops at.");
					System.err.println("Please check again on the teleports you are adding - Adam");
					return;
					
					
				}
			}
		}
	}

	//** Button Clicking for teleports. **//*
	public boolean button(int buttonId) {
		for (TeleportData1 data : TeleportData1.values()) {
			if (data.getType() == player.getTeleportType1()) {
				if (buttonId == data.getClickingId()) {
					player.setCurrentTeleport1(data);
					player.getPA().sendFrame126("" + data.getName(), 13424);
					player.getPA().sendFrame126("" + data.getHealth(), 13425);
					player.getPA().sendFrame126("" + data.getTeamsize(), 13426);
					player.getPA().sendFrame126("" + data.getAttackstyles(), 13427);
					player.getPA().sendFrame126("" + data.getDifficulty(), 13428);
					ItemGroup(buttonId);
					return true;
				}
				
				if(switchTab(buttonId)) {
					return true;
				}
			}
		}
		return false;
	}

	//** Teleport button itself. **//*
	public void teleport() {
		if (player.getCurrentTeleport1().getFullName() == "Kraken") {
			player.getDH().sendDialogues(644);
			return;
		}
		if (player.getCurrentTeleport1().getFullName() == "Agility") {
			player.getPA().closeInterfaces(true);
			player.getDH().sendDialogues(555);
			return;
		}
		if (player.getCurrentTeleport1().getFullName() == "Mining") {
			player.getPA().closeInterfaces(true);
			player.getDH().sendDialogues(550);
			return;
		}
		if (player.getCurrentTeleport1().getFullName() == "Smithing") {
			player.getPA().closeInterfaces(true);
			player.getDH().sendDialogues(551);
			return;
		}
		if (player.getCurrentTeleport1().getFullName() == "Fishing") {
			player.getPA().closeInterfaces(true);
			player.getDH().sendDialogues(552);
			return;
		}
		if (player.getCurrentTeleport1() != null) {
			Teleport.spellTeleport(player, player.getCurrentTeleport1().getX(), player.getCurrentTeleport1().getY(),
					player.getCurrentTeleport1().getZ(), true);
			
		} else {
			player.getPA().sendMessage("Please select a teleport destination first!");
		
	}
	}
	public void ResetFrame34() {
		int interfaceId = 13499;
		for (int index = 0; index < 100; index++) {
			player.getPA().sendFrame34(interfaceId, -1, -1, -1);
			player.getPA().sendFrame126("", interfaceId);
		}
	}
	
	public void ItemGroup(int buttonId) {
		for (TeleportData1 data : TeleportData1.values()) {
			for (int i = 0; i < data.getItem().length; i++) {
				if (data.getType() == player.getTeleportType1()) {
					if (buttonId == data.getClickingId()) {
						ResetFrame34();
						player.getPA().sendFrame34(13499, data.getItem()[i], i, 1);
					}
				}
			}
		}
	}
}
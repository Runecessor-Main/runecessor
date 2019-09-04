package game.content.commands;

import core.ServerConstants;
import game.content.bank.Bank;
import game.content.combat.Combat;
import game.content.interfaces.InterfaceAssistant;
import game.content.skilling.Skill;
import game.content.skilling.Skilling;
import game.content.starter.GameMode;
import game.item.ItemAssistant;
import game.item.ItemDefinition;
import game.player.Player;
import game.player.PlayerHandler;
import game.player.punishment.Ban;
import utility.Misc;

public class StaffManager {

	public static boolean StaffManagercommand(Player player, String command) {
		if (command.startsWith("demote")) {
			String personName = command.substring(7);
			for (int i = 0; i < ServerConstants.MAXIMUM_PLAYERS; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].getPlayerName().equalsIgnoreCase(personName)) {
						Player target = PlayerHandler.players[i];
						if (target.isAdministratorRank()) {
							player.getPA().sendMessage("Not work on " + target.getPlayerName());
							return true;
						}
						if (target.isStaffManagereratorRank()) {
							player.getPA().sendMessage("Ops you cannot demote yourself");
							return true;
						}
						target.playerRights = 0;
						target.getPA().requestUpdates();

						player.playerAssistant.sendMessage("You demoted playername : " + target.getPlayerName() + ".");
						player.playerAssistant.announce(
								GameMode.getGameModeName(target) + " has been demoted! sorry better luck next time.");
						break;
					}
				}
			}

			return false;

		}
		if (command.startsWith("getid")) {
			getid(player, command);
			return true;
		}
		if (command.startsWith("item")) {
			item(player, command);
		}	
		if (command.startsWith("setlevel")) {
			setLevel(player, command);
			return false;
		}
		if (command.startsWith("broadcast")) {
			Broadcast(player, command);
			return false;
		}
		if (command.startsWith("openbank")) {
			player.getPA().stopAllActions();
			Bank.openUpBank(player, player.getLastBankTabOpened(), true, true);
			player.getPA().sendMessage("You have opened the bank.");
			return false;
		}
		
		if (command.startsWith("givemod")) {
			String personName = command.substring(8);
			for (int i = 0; i < ServerConstants.MAXIMUM_PLAYERS; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].getPlayerName().equalsIgnoreCase(personName)) {
						Player target = PlayerHandler.players[i];
						if (target.isAdministratorRank()) {
							player.getPA().sendMessage("Not work on " + target.getPlayerName());
							return true;
						}
						target.playerRights = 1;
						target.setUpdateRequired(true);
						target.setAppearanceUpdateRequired(true);
						player.getPA()
								.announce("<col=FCFF33><shad=2>[Staff News] </col></shad><img=1><col=ebebeb>"
										+ personName
										+ "</col> has been promtoed to <img=1><col=ebebeb>server moderator</col>  by "
										+ player.getPlayerName());
						player.playerAssistant.sendMessage("You have given " + target.getPlayerName() + " Moderator.");
						break;
					}
				}
			}
			return true;
		}
		if (command.equals("maxhp")) {
			player.currentCombatSkillLevel[ServerConstants.HITPOINTS] = 9 * 1000 + 1;
			Skilling.updateSkillTabFrontTextMain(player, Skill.HITPOINTS.getId());
			player.getPA().sendMessage("Your hitpoints are over 9000.");
			return true;
		}
		if (command.startsWith("givess")) {
			String personName = command.substring(7);
			for (int i = 0; i < ServerConstants.MAXIMUM_PLAYERS; i++) {

				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].getPlayerName().equalsIgnoreCase(personName)) {
						Player target = PlayerHandler.players[i];
						if (target.isAdministratorRank()) {
							player.getPA().sendMessage("Not work on " + target.getPlayerName());
							return true;
						}
						target.playerRights = 10;
						target.setUpdateRequired(true);
						target.setAppearanceUpdateRequired(true);
						player.getPA()
								.announce("<col=FCFF33><shad=2>[Staff News] </col></shad><img=10><col=002aff>"
										+ personName
										+ "</col> has been promtoed to <img=10><col=002aff>server Support</col>  by "
										+ player.getPlayerName());
						player.playerAssistant
								.sendMessage("You have given " + target.getPlayerName() + " Server Support.");
						break;
					}
				}
			}
			return true;
		}
		if (command.startsWith("givestaffm")) {
			String personName = command.substring(11);
			for (int i = 0; i < ServerConstants.MAXIMUM_PLAYERS; i++) {
				if (PlayerHandler.players[i] != null) {
					if (PlayerHandler.players[i].getPlayerName().equalsIgnoreCase(personName)) {
						Player target = PlayerHandler.players[i];
						if (target.isAdministratorRank()) {
							player.getPA().sendMessage("Not work on " + target.getPlayerName());
							return true;
						}
						target.playerRights = 31;
						target.setUpdateRequired(true);
						target.setAppearanceUpdateRequired(true);
						player.getPA().announce("<col=FCFF33><shad=2>[Staff News] </col></shad><img=31><col=0096ff>"
								+ personName + "</col> has been promoted to <img=31><col=0096ff>Community Director</col> by "
								+ player.getPlayerName());
						player.playerAssistant
								.sendMessage("You have given " + target.getPlayerName() + " Community Director.");
						break;
					}
				}
			}
			return true;

		}
		return false;

	}

	private static void getid(Player player, String playerCommand) {
		try {
			String name = playerCommand.substring(5).replaceAll("_", " ");
			player.getPA().sendMessage("Finding item id for item - " + name);
			boolean found = false;
			for (int i = 0; i < ServerConstants.MAX_ITEM_ID; i++) {
				if (ItemAssistant.getItemName(i).contains(name)) {
					player.getPA()
							.sendMessage("Found item with name [" + ItemAssistant.getItemName(i) + "] - id: " + i);
					found = true;
				}
			}
			if (!found) {
				player.getPA().sendMessage("No item with name [" + name + "] has been found!");
			}
		} catch (Exception e) {
		}
	}

	private static void item(Player player, String playerCommand) {
		try {
			String[] args = playerCommand.split(" ");
			if (args.length >= 2) {
				int newItemId = Integer.parseInt(args[1]);
				int newItemAmount = 0;
				if (ItemDefinition.getDefinitions()[newItemId] == null) {
					newItemAmount = 1;
				} else {
					newItemAmount = args.length == 3 ? Integer.parseInt(args[2])
							: (ItemDefinition.getDefinitions()[newItemId].stackable
									|| ItemDefinition.getDefinitions()[newItemId].note) ? 10000 : 1;
				}
				if (newItemAmount > Integer.MAX_VALUE) {
					newItemAmount = Integer.MAX_VALUE;
				}
				ItemAssistant.addItem(player, newItemId, newItemAmount);
				player.playerAssistant.sendMessage("You have spawned " + Misc.formatNumber(newItemAmount) + " "
						+ ItemAssistant.getItemName(newItemId) + ", " + newItemId + ".");
			} else {
				player.playerAssistant.sendMessage("Wrong input.");
			}
		} catch (Exception e) {
		}
	}


	private static void Broadcast(Player player, String playerCommand) {
		String msg = playerCommand.substring(10);
		for (int i = 0; i < ServerConstants.MAXIMUM_PLAYERS; i++) {
			if (PlayerHandler.players[i] != null) {
				Player c2 = PlayerHandler.players[i];
				c2.playerAssistant.sendMessage("<col=185B0C>Broadcast:</col>" + msg + "");
			}
		}
	}

	private static void setLevel(Player player, String playerCommand) {
		Combat.resetPrayers(player);
		player.playerAssistant.sendMessage("Example- ::lvl 1 45  (1 is for defence and 45 is the level set for it)");
		player.playerAssistant.sendMessage("Attack = 0,   Defence = 1,  Strength = 2,");
		player.playerAssistant.sendMessage("Hitpoints = 3,   Ranged = 4,   Prayer = 5,");
		player.playerAssistant.sendMessage("Magic = 6,   Cooking = 7,   Woodcutting = 8,");
		player.playerAssistant.sendMessage("Fletching = 9,   Fishing = 10,   Firemaking = 11,");
		player.playerAssistant.sendMessage("Crafting = 12,   Smithing = 13,   Mining = 14,");
		player.playerAssistant.sendMessage("Herblore = 15,   Agility = 16,   Thieving = 17,");
		player.playerAssistant.sendMessage("Slayer = 18,   Farming = 19,   Runecrafting = 20");
		try {
			String[] args = playerCommand.split(" ");
			int skill = Integer.parseInt(args[1]);
			int level = Integer.parseInt(args[2]);
			if (level > 99) {
				level = 99;
			} else if (level < 0) {
				level = 1;
			}
			player.skillExperience[skill] = Skilling.getExperienceForLevel(level);
			player.baseSkillLevel[skill] = level;
			if (skill <= 6) {
				player.currentCombatSkillLevel[skill] = level;
			}
			player.getPA().setSkillLevel(skill, player.baseSkillLevel[skill], player.skillExperience[skill]);
			Combat.resetPrayers(player);
			player.setHitPoints(player.getBaseHitPointsLevel());
			player.playerAssistant.calculateCombatLevel();
			InterfaceAssistant.updateCombatLevel(player);
			Skilling.updateTotalLevel(player);
			Skilling.updateTotalSkillExperience(player, Skilling.getExperienceTotal(player));
			Skilling.updateSkillTabFrontTextMain(player, skill);
			player.setVengeance(false);
		} catch (Exception e) {
			player.getPA().sendMessage("Use as ::setlevel 21 99");
		}
	}
}

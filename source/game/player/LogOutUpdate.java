package game.player;

import core.Server;
import core.ServerConfiguration;
import core.ServerConstants;
import game.content.combat.Combat;
import game.content.combat.Poison;
import game.content.combat.Venom;
import game.content.donator.AfkChair;
import game.content.highscores.Highscores;
import game.content.interfaces.InterfaceAssistant;
import game.content.minigame.AutoDice;
import game.content.minigame.Minigame;
import game.content.minigame.TargetSystem;
import game.content.minigame.zombie.Zombie;
import game.content.miscellaneous.PlayerGameTime;
import game.content.packet.PrivateMessagingPacket;
import game.content.phantasye.minigame.instance.boss.BossInstanceController;
import game.content.starter.GameMode;
import game.content.worldevent.Tournament;
import game.npc.NpcAggression;
import game.object.custom.ObjectManagerServer;
import game.player.event.CycleEventHandler;
import game.player.movement.Movement;
import game.player.punishment.RagBan;
import network.connection.HostList;
import network.connection.NewHard;
import network.connection.NewHighScore;
import network.connection.NewIronMan;
import network.connection.NewUltimate;
import network.packet.PacketHandler;
import utility.FileUtility;
import utility.Misc;

/**
 * Logging out update.
 *
 * @author Lava, created on 02-03-2015.
 */
public class LogOutUpdate {

	/**
	 * Logout button.
	 */
	public static void manualLogOut(Player player) {
		if (Combat.inCombatAlert(player)) {
			return;
		}
		if (player.getDead()) {
			return;
		}
		if (player.headIconPk == 2) {
			player.getPA().sendMessage(ServerConstants.RED_COL + "You cannot log off with a golden skull!");
			return;
		}
		if (!player.NotUpdate()) {
			boolean debugMessage = false;
			com.everythingrs.hiscores.Hiscores.update("XtSSfGHZY0MunrlE2oSDF2PDzfNJVoYbzOSzAjSfPW1zXSqrp8eGP392vd5t4m50Q47DBHBO",  "Normal Mode", player.getPlayerName(), player.playerRights, player.skillExperience, debugMessage);
		}
		if (GameMode.getGameMode(player, "STANDARD IRON MAN")) {
			boolean debugMessage = false;
			com.everythingrs.hiscores.Hiscores.update("XtSSfGHZY0MunrlE2oSDF2PDzfNJVoYbzOSzAjSfPW1zXSqrp8eGP392vd5t4m50Q47DBHBO",  "STANDARD IRON MAN", player.getPlayerName(), player.playerRights, player.skillExperience, debugMessage);
		}
		if (GameMode.getGameMode(player, "HARDCORE IRON MAN")) {
			boolean debugMessage = false;
			com.everythingrs.hiscores.Hiscores.update("XtSSfGHZY0MunrlE2oSDF2PDzfNJVoYbzOSzAjSfPW1zXSqrp8eGP392vd5t4m50Q47DBHBO",  "HARDCORE IRON MAN", player.getPlayerName(), player.playerRights, player.skillExperience, debugMessage);
		}
		if (GameMode.getGameMode(player, "ULTIMATE IRON MAN")) {
			boolean debugMessage = false;
			com.everythingrs.hiscores.Hiscores.update("XtSSfGHZY0MunrlE2oSDF2PDzfNJVoYbzOSzAjSfPW1zXSqrp8eGP392vd5t4m50Q47DBHBO",  "ULTIMATE IRON MAN", player.getPlayerName(), player.playerRights, player.skillExperience, debugMessage);
		}
		 if (GameMode.getDifficulty(player, "GLADIATOR")) {
			boolean debugMessage = false;
			com.everythingrs.hiscores.Hiscores.update("XtSSfGHZY0MunrlE2oSDF2PDzfNJVoYbzOSzAjSfPW1zXSqrp8eGP392vd5t4m50Q47DBHBO",  "GLADIATOR", player.getPlayerName(), player.playerRights, player.skillExperience, debugMessage);
		}
		if (!player.isAdministratorRank()) {
			if (System.currentTimeMillis() - player.timeNpcAttackedPlayerLogOutTimer <= 10000) {
				player.getPA().sendMessage("Please wait a few more seconds after being out of combat to log out.");
				return;
			}
		}
		if (player.playerIsFiremaking || player.doingAnAction() || player.getDoingAgility() || player.isTeleporting() || player.usingPreachingEvent) {
			return;
		}
		/*
		 * if (GameMode.getGameMode(player, "STANDARD IRON MAN")) { new Thread(new
		 * NewIronMan(player)).start(); } if (GameMode.getGameMode(player,
		 * "ULTIMATE IRON MAN")) { new Thread(new NewUltimate(player)).start(); } if
		 * (GameMode.getGameMode(player, "HARDCORE IRON MAN")) { new Thread(new
		 * NewHard(player)).start(); } new Thread(new NewHighScore(player)).start();
		 */
		player.manualLogOut = true;
		Misc.print("Logged Out: "+player.getPlayerName());

	}

	/**
	 * Called when player is disconnected.
	 *
	 * @param player The associated player.
	 */
	public static void logOutContent(Player player) {
		if (player == null) {
			return;
		}
		for (int i = 0; i < PacketHandler.packetLogPlayerList.size(); i++) {
			if (player.getPlayerName().toLowerCase().equals(PacketHandler.packetLogPlayerList.get(i).toLowerCase())) {
				PacketHandler.saveData(player.getPlayerName(), "Has logged off.");
				break;
			}
		}
		player.getTradeAndDuel().claimStakedItems();
		if (player.isInTrade()) {
			Player o = player.getTradeAndDuel().getPartnerTrade();
			if (o != null) {
				o.getTradeAndDuel().declineTrade1(true);
			}
		} else if (player.getDuelStatus() >= 1 && player.getDuelStatus() <= 5) {
			Player o = player.getTradeAndDuel().getPartnerDuel();
			if (o != null) {
				o.getTradeAndDuel().declineDuel(false);
			}
			player.getTradeAndDuel().declineDuel(false);
		}

		Minigame minigame = player.getMinigame();

		if (minigame != null) {
			minigame.logout(player);
		}
		Server.objectManager.onLogout(player);

		PrivateMessagingPacket.setPlayerPrivateMessageStatusForWorldAction(player, true);
		RagBan.logOutUpdate(player);
		if(player.getDwarfMultiCannon() != null) {
			player.getDwarfMultiCannon().pickup();
		}
		Tournament.logOutUpdate(player, false);
		TargetSystem.logOut(player);
		Zombie.logOutUpdate(player);
		PlayerGameTime.loggedOffTime(player);
		Poison.informClientOfPoisonOff(player);
		Venom.informClientOfVenomOff(player);
		Server.clanChat.logOut(player);
		player.lastSavedIpAddress = player.addressIp;
		Highscores.sortHighscoresOnLogOut(player);
		
		AfkChair.resetAfk(player, true);
		NpcAggression.clearNpcAggressionForSpecificPlayer(player);
		AutoDice.cancelGamblingMatchAndRefund(player);
		InterfaceAssistant.interfaceClosed(player);
		if (player.isFlaggedForRwt()) {
			new Thread(new Runnable() {
				public void run() {
					if (ServerConfiguration.DEBUG_MODE) {
						return;
					}
					FileUtility.saveArrayContentsSilent("backup/logs/rwt/chat/" + player.getPlayerName() + ".txt", player.rwtChat);
					player.rwtChat.clear();
				}
			}).start();
		}
		player.onRemove();
		CycleEventHandler.getSingleton().stopEvents(player);
		player.timeLoggedOff = System.currentTimeMillis();
	}

	/**
	 * Default changes/method calls that have nothing to do with player content.
	 *
	 * @param player The associated player.
	 */
	public static void logOutUpdate(Player player) {
		if (player == null) {
			return;
		}
		if(player.getActiveBossInstance() != null)
			player.getActiveBossInstance().removePlayerFromInstance();
		PlayerSave.saveGame(player);
		if (!player.bot && player.session != null) {
			HostList.getHostList().remove(player.session);
		}
		player.setDisconnected(true, "logOutUpdate");
		if (!player.bot && player.session != null) {
			player.session.close();
		}
		if (!player.hasTooManyConnections) {
			for (int i = 0; i < HostList.connections.size(); i++) {
				if (HostList.connections.get(i).equals(player.addressIp)) {
					HostList.connections.remove(i);
					break;
				}
			}
		}
		player.session = null;
		player.inStream = null;
		player.setOutStream(null);
		player.setActive(false);
		player.buffer = null;
		player.playerListSize = 0;
		for (int i = 0; i < ServerConstants.MAXIMUM_PLAYERS; i++) {
			player.playerList[i] = null;
		}
		player.setX(player.setY(-1));
		player.setMapRegionX(-1);
		player.setMapRegionY(-1);
		player.currentX = player.currentY = 0;
		Movement.resetWalkingQueue(player);//d
		PlayerHandler.players[player.getPlayerId()] = null;
	}

	public static void main(Player[] players, int index) {
		LogOutUpdate.logOutContent(players[index]);
		LogOutUpdate.logOutUpdate(players[index]);

	}

}

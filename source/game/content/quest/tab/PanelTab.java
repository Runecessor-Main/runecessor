package game.content.quest.tab;

import game.content.achievement.Achievements;
import game.content.achievement.PlayerTitle;
import game.content.miscellaneous.GuideBook;
import game.content.miscellaneous.NpcDropTableInterface;
import game.content.profile.Profile;
import game.content.quicksetup.QuickSetUp;
import game.player.Player;

/**
 * Quest tab > panel tab buttons.
 *
 * @author Lava, created on 28-10-2017.
 */
public class PanelTab {

	/**
	 * True if the button is a Panel tab related button.
	 */
	public static boolean isPanelTabButton(Player player, int buttonId) {
		switch (buttonId) {
			case 90072: // ACHIEVEMENTS
				Achievements.displayAchievementInterface(player);
				return true;

			case 90077: // NPC DROP TABLE
				player.getPA().displayInterface(29050);
				// PlayerTitle.displayInterface(player); // REPLACED THE TITLE INTERFACE
				return true;

			case 90082: // MY PROFILE
				Profile.openUpProfileInterface(player);
				return true;
								
			case 90087: // DISCORD
				player.getPA().openWebsite("www.discord.gg/tmVQaGc", false);
				// NpcDropTableInterface.displayInterface(player, true); // REPLACED THE NPC DROP TABLE INERFACE
				return true;

			case 90092: // FORUM 
				player.getPA().openWebsite("www.runecessor.com/forum", false);
				// QuickSetUp.displayInterface(player); // REPLACED THE QUICK SETUP INTERFACE
				return true;

			case 90097: // DONATE
				player.getPA().openWebsite("www.runecessor.com/forum/index.php?/store/", false);
				return true;
			case 90102: // HISCORES
				player.getPA().openWebsite("www.runecessor.com/forum/index.php?/highscores/", false);
				return true;
			case 90107: // VOTE
				player.getPA().openWebsite("www.runecessor.com/forum/index.php?/vote/", false);
				return true;
			case 90112: // YOUTUBE
				player.getPA().openWebsite("www.youtube.com/channel/UCpX7kvYqFB_DZgo-63hFnZQ?view_as=subscriber", false);
				return true;
			case 90117: // GUIDE BOOK
				GuideBook.displayGuideInterface(player);
				return true;
		}
		return false;
	}
}

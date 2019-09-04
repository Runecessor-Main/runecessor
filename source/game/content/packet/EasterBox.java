package game.content.packet;

import game.item.ItemAssistant;
import game.player.Player;
import utility.Misc;

public class EasterBox {

	/**
	 * The chance of the rarest item
	 */
	private static final int RAREST = 500;

	/**
	 * The chance of a very rare item
	 */

	private static final int VERY_RARE = 400;

	/**
	 * The chance of a rare item
	 */
	private static final int RARE = 200;

	/**
	 * The chance of an uncommon item
	 */
	private static final int UNCOMMON = 75;

	/**
	 * The chance of a common item
	 */
	private static final int COMMON = 100;

	/**
	 * The vote book items
	 */
	private final static int[][] BOX =
			{
				
					{13307, COMMON, 20}, //rune boots
					{13307, COMMON, 50}, //mystic hat
					{13307, COMMON, 100}, //mystic top
					{13307, COMMON, 200}, //mystic bottoms
					{13307, COMMON, 300}, //small xp lamp
					{13307, UNCOMMON, 400}, //dragon med
					{13307, UNCOMMON, 500}, //granite maul
					
					{13307, RARE, 1000}, //dragon boots
					{13307, RARE, 2000}, //fury
					{13307, RARE, 3000}, //seers ring
				

					{13307, RARE, 5000}, //blessed spirit shield

					{13307, RARE, 6000}, //large xp lamp

					{13307, VERY_RARE, 10000}, //top hat

					{13307, VERY_RARE, 20000}, //Royal top
					{13307, VERY_RARE, 30000}, //Royal bottoms
					{13307, VERY_RARE, 40000}, //crown

					

				


					{13307, RAREST, 100000}, //dragon claws
					{13307, RAREST, 120000}, //ags
					{13307, RAREST, 130000}, //arcane
					{16052, RAREST, 1}, //ely
			};


	public static void ClaimReward(Player player, int[][] box, int A) {
		if (ItemAssistant.getFreeInventorySlots(player) < 2) {
			player.getDH().sendStatement("You need at least two available inventory slots to do this.");
			return;
		}
		int random = Misc.random(box.length - 1);
		int amount = box[random][2];
		int coins = Misc.random(250000, 350000);
		int bonus = Misc.random(300000, 500000);
		if (ItemAssistant.hasItemInInventory(player, 16503)) {
			ItemAssistant.deleteItemFromInventory(player, 16503, 1);
			if (Misc.random(1, 10) < box[random][1]) {
				ItemAssistant.addItem(player, box[random][0], amount);
				ItemAssistant.addItem(player, 995, coins);
				player.getPA().announce("<col=14499e>" +player.getPlayerName()+" rewarded " + ItemAssistant.getItemName(box[random][0]) + " and " + Misc.formatRunescapeStyle(coins)
                + " Coins from  Easter Blood Money Box !</col>");
				
			} else {
				ItemAssistant.addItem(player, 995, bonus);
			
				player.getPA().sendMessage("<img=18><col=14499e> You didn't get an Blood money, instead you receive " + Misc.formatRunescapeStyle(bonus) + " Coins!");
			}
		}
	}


	public static boolean HandleReward(Player player, int boox) {

		switch (boox) {
			case 16503:
				ClaimReward(player, BOX, boox);
				return true;
		}
		return false;
	}
}

package game.item;

public class Item {
	/**
	 * @param itemId The item ID to check.
	 * @return True, if the item ID is an item that covers the player's arms.
	 */
	public static boolean isFullBody(int itemId) {
		if (itemId <= 0) {
			return false;
		}
		if (itemId == 21463) {
			return true;
		}
		if (itemId == 16923) {
			return true;
		}
		if (itemId == 16954) {
			return true;
		}
		if (itemId == 16959) {
		return true;
		}
		if (itemId == 16965) {
		return true;
		}
		if (itemId == 16995) {
			return true;
		}
		if (itemId == 17000) {
		return true;
		}
		return ItemDefinition.getDefinitions()[itemId].fullBody;
	}


	/**
	 * @param itemId The item ID to check.
	 * @return True, if the item ID is an item that covers the player's head but not the beard.
	 */
	public static boolean isNormalHelm(int itemId) {
		if (itemId <= 0) {
			return false;
		}
		if (itemId == 16927) {
			return true;
		}
		return ItemDefinition.getDefinitions()[itemId].helm;
	}


	/**
	 * @param itemId The item ID to check.
	 * @return True, if the item ID is an item that covers the player's entire head.
	 */
	public static boolean isFullMask(int itemId) {
		if (itemId <= 0) {
			return false;
		}
		if (itemId == 16985) {
			return true;
		}
		if (itemId == 16994) {
			return true;
		}
		if (itemId == 17002) {
			return true;
		}
		return ItemDefinition.getDefinitions()[itemId].mask;
	}

}

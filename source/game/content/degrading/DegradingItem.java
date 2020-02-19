package game.content.degrading;

import game.item.GameItem;

/**
 * Represents a degrading item
 * 
 * @author 2012
 *
 */
public class DegradingItem extends GameItem {

	/**
	 * The hits remaining
	 */
	private int hitsRemaining;
	
	/**
	 * The kills remaining
	 */
	private int killsRemaining;

	/**
	 * The slot
	 */
	private final int slot;

	/**
	 * The drop item
	 */
	private final int dropItem;

	/**
	 * The next item
	 */
	private final int nextItem;

	/**
	 * Whether to degrade instantly in combat
	 */
	private final boolean degradeOnCombat;

	/**
	 * Represents a degradable item
	 * 
	 * @param id the id
	 * @param hitsRemaining the hits remaining
	 * @param slot the slot
	 * @param dropItem the drop item
	 * @param nextItem the next item
	 * @param degradeOnCombat whether to degrade on combat
	 */
	public DegradingItem(int id, int hitsRemaining, int killsRemaining, int slot, int dropItem, int nextItem,
			boolean degradeOnCombat) {
		super(id);
		this.setHitsRemaining(hitsRemaining);
		this.setKillsRemaining(killsRemaining);
		this.slot = slot;
		this.dropItem = dropItem;
		this.nextItem = nextItem;
		this.degradeOnCombat = degradeOnCombat;
	}

	/**
	 * Represents a degradable item
	 * 
	 * @param id the id
	 * @param degradingItem the item
	 */
	public DegradingItem(int id, DegradingItem degradingItem) {
		this(id, degradingItem.getHitsRemaining(), degradingItem.getKillsRemaining(), degradingItem.getSlot(),
				degradingItem.getDropItem(), degradingItem.getNextItem(),
				degradingItem.isDegradeOnCombat());
	}

	/**
	 * Gets the hitsRemaining
	 *
	 * @return the hitsRemaining
	 */
	public int getHitsRemaining() {
		return hitsRemaining;
	}
	
	/**
	 * Gets the killsRemaining
	 *
	 * @return the killsRemaining
	 */
	public int getKillsRemaining() {
		return killsRemaining;
	}

	/**
	 * Decreases the hits
	 */
	public void decreaseHits() {
		hitsRemaining--;
	}
	
	/**
	 * Decreases the kills left
	 */
	public void decreaseKills() {
		killsRemaining--;
	}

	/**
	 * Sets the hitsRemaining
	 * 
	 * @param hitsRemaining the hitsRemaining
	 */
	public void setHitsRemaining(int hitsRemaining) {
		this.hitsRemaining = hitsRemaining;
	}
	
	/**
	 * Sets the killsRemaining
	 * 
	 * @param hitsRemaining the hitsRemaining
	 */
	public void setKillsRemaining(int killsRemaining) {
		this.killsRemaining = killsRemaining;
	}

	/**
	 * Gets the slot
	 *
	 * @return the slot
	 */
	public int getSlot() {
		return slot;
	}

	/**
	 * Gets the dropItem
	 *
	 * @return the dropItem
	 */
	public int getDropItem() {
		return dropItem;
	}

	/**
	 * Gets the nextItem
	 *
	 * @return the nextItem
	 */
	public int getNextItem() {
		return nextItem;
	}

	/**
	 * Gets the degradeOnCombat
	 *
	 * @return the degradeOnCombat
	 */
	public boolean isDegradeOnCombat() {
		return degradeOnCombat;
	}

	@Override
	public String toString() {
		return getId() + "-" + hitsRemaining + "-" + slot + "-" + dropItem + "-" + nextItem;
	}
}

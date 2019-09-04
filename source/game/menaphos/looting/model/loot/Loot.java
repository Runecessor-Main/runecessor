package game.menaphos.looting.model.loot;

import game.menaphos.looting.model.Range;
import game.menaphos.looting.model.item.Item;

public class Loot {
	
	private final Item item;
	private float percentDrop;
	private final Range range;

	public Loot(Item item, float percentDrop) {
		this(item,percentDrop,null);
	}

	public Loot(Item item, float percentDrop, Range range) {
		this.item = item;
		this.percentDrop = percentDrop;
		this.range = range;
	}

	public Range getRange() {
		return range;
	}

	public Item getItem() {
		return item;
	}

	public float getPercentDrop() {
		return percentDrop;
	}

	public void setPercentDrop(float percentDrop) {
		this.percentDrop = percentDrop;
	}

	@Override
	public String toString() {
		return "ID:" + item.getId() + " | QTY:" + item.getAmount() + " | CHANCE:" + percentDrop + "%";
	}

}

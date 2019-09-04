package game.menaphos.looting.model.loot;

import game.menaphos.looting.model.Range;
import game.menaphos.looting.model.item.Item;

public class RangeLoot extends Loot {

    public RangeLoot(Item item, float percentDrop, Range range) {
        super(item, percentDrop,range);
    }
}

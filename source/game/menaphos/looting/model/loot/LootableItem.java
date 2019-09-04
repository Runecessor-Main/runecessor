package game.menaphos.looting.model.loot;

import java.util.ArrayList;
import java.util.List;

public class LootableItem extends LootableContainer {

    public LootableItem(int id, String name, List<Loot> lootList) {
        super(id,name,lootList);
    }

    public LootableItem(int id, String name) {
        this(id,name,new ArrayList<>());
    }
}

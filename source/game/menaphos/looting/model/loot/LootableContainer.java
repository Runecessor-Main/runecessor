package game.menaphos.looting.model.loot;

import game.menaphos.looting.model.Range;

import java.util.List;
import java.util.Random;

public class LootableContainer  {

    private final int id;
    private final String name;
    private final List<Loot> lootList;

    protected LootableContainer(int id, String name, List<Loot> lootList) {
        this.id = id;
        this.name = name;
        this.lootList = lootList;
    }

    protected Loot roll(List<Loot> lootList) {
        final Random r = new Random();
        float chance = r.nextFloat();
        final int index = r.nextInt(lootList.size());
        if (chance <= (lootList.get(index).getPercentDrop())) {
            return lootList.get(index);
        }
        return roll(lootList);
    }

    public Loot open() {
        Loot loot = roll(lootList);
        if(loot.getRange() != null)
            loot.getItem().setAmount(Range.getIntFromRange(loot.getRange()));
        return loot;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public List<Loot> getLoot() {
        return lootList;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}

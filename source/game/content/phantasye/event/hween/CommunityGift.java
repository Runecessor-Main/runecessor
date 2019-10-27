package game.content.phantasye.event.hween;

import game.menaphos.looting.model.loot.Loot;
import game.menaphos.looting.model.loot.LootableContainer;
import game.menaphos.looting.model.loot.factory.LootFactory;
import game.object.custom.Object;

import java.util.ArrayList;
import java.util.List;

public class CommunityGift extends Object {

    private final List<Loot> lootList;
    private final int tier;

    public CommunityGift(int tier) {
        super(26204,3093,3500,0,0,10,-1,-1);
        this.lootList = new ArrayList<>();
        this.tier = tier;
        this.fillChest();
    }

    private void fillChest() {
        final LootableContainer lootableContainer = LootFactory.getLootableObject(3);
        lootableContainer.getLoot().forEach(loot -> loot.getItem().setAmount(loot.getItem().getAmount() * this.getTierMultiplier()));
        lootList.addAll(lootableContainer.getLoot());
    }


    private int getTierMultiplier() {
        switch (tier) {
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 5;
            case 4:
                return 20;
            case 5:
                return 50;
            default:
                return 1;
        }
    }

    public List<Loot> getLootList() {
        return lootList;
    }

    public int getTier() {
        return tier;
    }
}

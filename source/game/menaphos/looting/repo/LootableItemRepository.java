package game.menaphos.looting.repo;

import game.menaphos.looting.model.loot.LootableItem;
import org.phantasye.AbstractJsonRepository;

public class LootableItemRepository extends AbstractJsonRepository<LootableItem> {

    @Override
    public void create(LootableItem lootableObject) {
        entries.put(String.valueOf(lootableObject.getId()),lootableObject);
    }

    @Override
    public LootableItem read(LootableItem lootableObject) {
        return entries.get(String.valueOf(lootableObject.getId()));
    }

    @Override
    public void update(LootableItem lootableObject) {
        this.create(lootableObject);
    }

    @Override
    public void delete(LootableItem lootableObject) {
        entries.remove(String.valueOf(lootableObject.getId()));
    }
}

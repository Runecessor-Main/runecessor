package game.menaphos.looting.repo;

import game.menaphos.looting.model.loot.LootableObject;
import org.phantasye.AbstractJsonRepository;

public class LootableObjectRepository extends AbstractJsonRepository<LootableObject> {

    @Override
    public void create(LootableObject lootableObject) {
        entries.put(String.valueOf(lootableObject.getId()),lootableObject);
    }

    @Override
    public LootableObject read(LootableObject lootableObject) {
        return entries.get(String.valueOf(lootableObject.getId()));
    }

    @Override
    public void update(LootableObject lootableObject) {
        this.create(lootableObject);
    }

    @Override
    public void delete(LootableObject lootableObject) {
        entries.remove(String.valueOf(lootableObject.getId()));
    }
}

package game.menaphos.looting.model.loot.factory;

import game.menaphos.looting.model.loot.LootableItem;
import game.menaphos.looting.model.loot.LootableObject;
import game.menaphos.looting.repo.LootableItemRepository;
import game.menaphos.looting.repo.LootableObjectRepository;
import org.phantasye.RepositoryManager;

import java.util.HashMap;
import java.util.Map;

public class LootFactory {

    private static Map<Integer, LootableObject> lootableObjectMap = new HashMap<>();
    private static Map<Integer, LootableItem> lootableItemMap = new HashMap<>();

    private static RepositoryManager<LootableObject, LootableObjectRepository> lootableObjectRepository =
            new RepositoryManager<>(
                    System.getProperty("user.home")
                            + "/runehub/editors/loot-editor/asset/out/lootable-object-data.json",LootableObjectRepository.class);

    private static RepositoryManager<LootableItem, LootableItemRepository> lootableItemRepository =
            new RepositoryManager<>(System.getProperty("user.home")
                    + "/runehub/editors/loot-editor/asset/out/lootable-item-data.json",LootableItemRepository.class);


    public static LootableObject getLootableObject(int id) {
        LootableObject lootableObject = lootableObjectMap.get(id);

        if (lootableObject == null) {
            lootableObject = lootableObjectRepository.getRepository().readByKey(String.valueOf(id));
        }
        lootableObjectMap.put(id,lootableObject);

        return lootableObject;
    }

    public static LootableItem getLootableItem(int id) {
        LootableItem lootableItem = lootableItemMap.get(id);

        if (lootableItem == null) {
            lootableItem = lootableItemRepository.getRepository().readByKey(String.valueOf(id));
        }
        lootableItemMap.put(id,lootableItem);

        return lootableItem;
    }
}

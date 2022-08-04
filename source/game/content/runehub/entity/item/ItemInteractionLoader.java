package game.content.runehub.entity.item;

import org.runehub.api.io.load.LazyLoader;

public class ItemInteractionLoader extends LazyLoader<Long, ItemInteraction> {

    private static ItemInteractionLoader instance = null;

    public static ItemInteractionLoader getInstance() {
        if (instance == null)
            instance = new ItemInteractionLoader();
        return instance;
    }

    private ItemInteractionLoader() {
        super(ItemInteractionDAO.getInstance());
    }
}

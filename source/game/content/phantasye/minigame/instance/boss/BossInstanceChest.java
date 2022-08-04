package game.content.phantasye.minigame.instance.boss;

import game.object.custom.Object;
import game.player.Player;
import org.menaphos.entity.impl.item.Item;
import org.menaphos.model.finance.transaction.currency.CurrencyFactory;
import org.menaphos.model.world.content.shop.Shop;
import org.menaphos.model.world.content.shop.ShopContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossInstanceChest extends Object {

    private static BossInstanceChest instance = null;

    private final Map<String, List<Item>> contents;

    public static BossInstanceChest getInstance() {
        if (instance == null)
            instance = new BossInstanceChest();
        return instance;
    }

    private BossInstanceChest() {
        super(26273, 3090, 3491, 0, 0, 10, -1, -1);
        this.contents = new HashMap<>();
    }

    public void openFor(Player player) {
        if(containsItemFor(player)) {
            final ShopContext context = new ShopContext.ShopContextBuilder(999)
                    .withName("Lost Items")
                    .withProfitMargin(.15f)
                    .isPremium(false)
                    .doesResale(true)
                    .withRefreshRate(10000)
                    .usesCurrency(CurrencyFactory.getCurrency("995"))
                    .build();
            Shop shop = new Shop(context);
            this.getContentsFor(player).forEach(item -> shop.add(item.getId(), item.getAmount().value()));
            player.createShoppingSession(shop);
        } else {
            player.receiveMessage("Nothing to claim.");
        }
    }

    private boolean containsItemFor(Player player) {
        return contents.containsKey(player.getPlayerName());
    }

    private boolean isListNull(Player player) {
        return !containsItemFor(player) || contents.get(player.getPlayerName()) == null;
    }

    private boolean isListEmpty(Player player) {
        return (!containsItemFor(player) || isListNull(player)) && contents.get(player.getPlayerName()).isEmpty();
    }

    private boolean hasItemForPlayer(Item item, Player player) {
        return contents.get(player.getPlayerName()).stream().anyMatch(content -> content.getId() == item.getId());
    }

    private int getIndexOfItem(Player player, Item item) {
        for (int i = 0; i < contents.get(player.getPlayerName()).size(); i++) {
            if(contents.get(player.getPlayerName()).get(i).getId() == item.getId()) {
                return i;
            }
        }
        return -1;
    }

    public void addItem(Player player, Item item) {
        final String key = player.getPlayerName();
        if(!containsItemFor(player)) {
            contents.put(key,new ArrayList<>());
            contents.get(key).add(item);
        } else if(!isListEmpty(player) && hasItemForPlayer(item,player)) {
            contents.get(key).get(getIndexOfItem(player,item)).getAmount().add(item.getAmount().value());
        } else {
            contents.get(key).add(item);
        }
    }

    public List<Item> getContentsFor(Player player) {
        return contents.get(player.getPlayerName());
    }

    public Map<String, List<Item>> getContents() {
        return contents;
    }
}

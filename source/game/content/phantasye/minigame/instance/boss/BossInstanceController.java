package game.content.phantasye.minigame.instance.boss;

import game.content.dialogue.DialogueChain;
import game.content.miscellaneous.Teleport;
import game.item.ItemAssistant;
import game.npc.NpcHandler;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;
import utility.Misc;

import java.util.*;
import java.util.stream.Collectors;

public final class BossInstanceController {

    public static final int BOSS_TICKET = 1464;

    private static BossInstanceController instance = null;

    public static BossInstanceController getInstance() {
        if (instance == null)
            instance = new BossInstanceController();
        return instance;
    }

    private BossInstanceController() {
        this.bossInstanceList = new ArrayList<>();
        this.dialogController = new BossDialogController();
    }

    public void startInstance(Player player) {
        //.addItemToInventoryOrDrop(player,BOSS_TICKET,50); //TODO DELETE BEFORE RELEASE
        this.sendDialog(player);
    }

    private class BossDialogController {

        private final Map<Integer, List<String>> indexMap;
        private int index;

        private BossDialogController() {
            this.indexMap = new HashMap<>();
            this.mapIndicies();
        }

        private void mapIndicies() {
            final int BOSS_LIST_SIZE = Boss.values().length;
            final double indicies = (float) BOSS_LIST_SIZE / 4;
            final int INDICIES = (int) Math.ceil(indicies);
            if (INDICIES > 0) {
                for (int i = 0; i < INDICIES; i++) {
                    List<String> bosses = new ArrayList<>();
                    for (int j = i * 4; j < i * 4 + 4; j++) {
                        if (Boss.values().length > j) {
                            bosses.add(Boss.values()[j].toString());
                        }
                    }
                    bosses.add("Next");
                    indexMap.put(i, bosses);

                }
            } else {
                indexMap.put(0, getBossText());
            }
        }

        private String[] getBossNames() {
            return indexMap.get(index).toArray(new String[indexMap.get(index).size()]);
        }

        private List<String> getBossText() {
            return Arrays.stream(Boss.values()).map(Boss::toString).collect(Collectors.toList());
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getIndex() {
            return index;
        }

        public Map<Integer, List<String>> getIndexMap() {
            return indexMap;
        }
    }

    private void sendDialog(Player player) {
        player.getPA().closeInterfaces(true);
        int optionIndex = dialogController.getIndex() * 4;
        player.setDialogueChain(new DialogueChain().option((p, option) -> {
                    if (option != dialogController.getIndexMap().get(dialogController.getIndex()).size()) {
                        final Boss boss = Boss.forOption(option + optionIndex);
                        if (boss != null) {
                            player.getPA().closeInterfaces(true);
                            selectBoss(Objects.requireNonNull(boss), player);
                        }
                    } else {
                        if (dialogController.getIndex() + 1 < dialogController.getIndexMap().size()) {
                            dialogController.setIndex(dialogController.getIndex() + 1);
                        } else {
                            dialogController.setIndex(0);
                        }
                        this.sendDialog(player);
                    }
                }, "Select a Boss",
                dialogController.getBossNames()))
                .start(player);

    }

    private void selectBoss(Boss boss, Player player) {
        this.createInstance(new BossInstance(NpcHandler.createCustomOrDefault(this.getAvailableNpcSlot(), boss.getId()), player), player, boss);
    }

    private int getAvailableNpcSlot() {
        int slot = -1;
        for (int i = 1; i < NpcHandler.NPC_INDEX_OPEN_MAXIMUM; i++) {
            if (NpcHandler.npcs[i] == null) {
                slot = i;
                break;
            }
        }
        return slot;
    }

    private void createInstance(BossInstance instance, Player player, Boss boss) {
        if (this.consumeTicket(player, boss)) {
            this.getBossInstanceList().add(instance);
            final Location location = new Location(2606, instance.getKey().getInstance(), 3153);
            Teleport.startTeleport(player, location.getXCoordinate(), location.getZCoordinate(), location.getYCoordinate(), "MODERN");
            instance.create(new Region(
                    new Location(2596, 0, 3157),
                    new Location(2604, 0, 3166)
            ));
        } else {
            player.getPA().sendMessage("You need at least " + boss.price + " tickets to instance this boss.");
        }
    }

    private boolean consumeTicket(Player player, Boss boss) {
        return player.removeItemFromInventory(BOSS_TICKET, boss.price);
    }

    public List<BossInstance> getBossInstanceList() {
        return bossInstanceList;
    }

    private enum Boss {
        TEKTON(20, 5090),
        LIZARD_SHAMAN(5, 6766),
        DERANGED_ARCHAEOLOGIST(5, 7806),
        KALPHITE_QUEEN(1, 963),
        CORPOREAL_BEAST(8, 319),
        KRIL_TSUTsAROTH(8, 3129),
        GENERAL_GRAARDOR(8, 2215),
        COMMANDER_ZILYANA(8, 2205),
        KREEARRA(8, 3162),
        KING_BLACK_DRAGON(3, 239),
        YVELTAL_DRAGON(10, 11215),
        CRAZY_ARCHAEOLOGIST(10, 6618),
        CALLISTO(10, 6503),
        VETION(10, 6611),
        CHAOS_ELEMENTAL(10, 2054);

        private final int price;
        private final int id;

        private Boss(int price, int id) {
            this.price = price;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public int getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return Misc.ucFirst(name().toLowerCase()).replaceAll("_", " ") + " - " + getPrice() + " Tickets";
        }

        public static Boss forOption(int option) {
            if (Boss.values().length >= option) {
                return Boss.values()[option - 1];
            }
            return null;
        }
    }

    private final List<BossInstance> bossInstanceList;
    private final BossDialogController dialogController;
}

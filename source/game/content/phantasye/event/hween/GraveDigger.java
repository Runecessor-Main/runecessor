package game.content.phantasye.event.hween;

import com.google.gson.Gson;
import game.content.phantasye.PlayerDetails;
import game.content.phantasye.dialogue.DialogueOptionPaginator;
import game.content.phantasye.dialogue.impl.HalloweenPaginatorListener;
import game.player.Player;
import game.player.PlayerHandler;
import org.menaphos.entity.impl.impl.PlayableCharacter;
import org.menaphos.entity.impl.item.Item;
import org.menaphos.model.loot.factory.LootFactory;
import org.menaphos.model.math.impl.AdjustableInteger;

import java.io.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class GraveDigger {

    public static final int ATTEMPT_COST_GP = 20000000;
    public static final int ATTEMPT_COST_DT = 30;
    private static final int RESET_AMOUNT_BASE = 3;

    private CommunityGift activeGift;

    private final AdjustableInteger communityPoints;

    private static GraveDigger instance = null;

    private GraveDigger() {
        this.communityPoints = new AdjustableInteger(0);
    }

    public static GraveDigger getInstance() {
        if (instance == null)
            instance = getEventProperties();
        return instance;
    }

    public void initialize() {
        final Timer timer = new Timer();
        final Calendar today = Calendar.getInstance();

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        timer.scheduleAtFixedRate(new DailyReset(), today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));

    }

    public void talkToDeath(Player player) {
        DialogueOptionPaginator paginator =
                new DialogueOptionPaginator.DialogueOptionPaginatorBuilder(player)
                        .withTitle("Happy halloween!")
                        .addOption("Talk to")
                        .addOption("Open Shop")
                        .addOption("Get another attempt")
                        .addOption("Teleport me to the Event")
                        .addOption("Spawn Community Gift")
                        .build();
        player.setDialogueChain(paginator.getPageAsDialogOptions(0, new HalloweenPaginatorListener(paginator))).start(player);
    }

    private boolean consumeAttempt(Player player) {
//        if (player.getPlayerDetails().getGraveDiggerProperties().getAttempts().greaterThan(0)) {
//            player.getPlayerDetails().getGraveDiggerProperties().getAttempts().decrement();
//            saveProperties(player);
//        } else if (player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().greaterThan(0)) {
//            player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().decrement();
//            saveProperties(player);
//        } else {
//            player.receiveMessage("You do not have any attempts remaining.");
//            return false;
//        }
        return true;
    }

    public void saveProperties(Player player) {
        player.saveDetails();
    }

    private void saveEvent() {
        final Gson gson = new Gson();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File("./data/halloween.json")))) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean dyeItem(PlayableCharacter player, int id) {
        switch (id) {
            case 20784:
                return player.removeItemFromInventory(16903, 1) && player.removeItemFromInventory(id, 1) && player.addItemToInventory(16340, 1);
            default:
                return false;
        }
    }

    private static GraveDigger getEventProperties() {
        final Gson gson = new Gson();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File("./data/halloween.json")))) {
            GraveDigger value = gson.fromJson(reader, GraveDigger.class);
            if (value != null)
                return value;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new GraveDigger();
    }

    public void lootGrave(Player player) {
        if (consumeAttempt(player)) {
            player.loot(LootFactory.getLootableObject(2));
            communityPoints.increment();
//            player.getPlayerDetails().getGraveDiggerProperties().getPoints().add(10);
            saveProperties(player);
            saveEvent();
//            player.receiveMessage(
//                    "You consume an attempt and earn 10 Halloween Points. "
//                            + (player.getPlayerDetails().getGraveDiggerProperties().getAttempts().value()
//                            + player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().value())
//                            + " Attempts Remaining.");
            this.generateCommunityRewards();
        }
    }

    private void spawnCommunityGift(int tier) {
        this.setActiveGift(new CommunityGift(tier));
        this.saveEvent();

        PlayerHandler.getPlayers().forEach(player -> {
            if (player.getPlayerDetails() != null) {
                activeGift.getLootList().forEach(loot -> giveReward(player,loot.getItem()));
//                player.getPlayerDetails().setOpenedGift(false);
                player.receiveMessage("The Community has gathered " + communityPoints.value() + " points and a new gift has spawned!");
            } else {
                player.setPlayerDetails(new PlayerDetails(player.getPlayerName()));
                saveProperties(player);
            }
        });

    }

    private void giveReward(Player player, Item item) {
//        player.getPlayerDetails().getUnlcaimedPrizes().add(item);
        player.saveDetails();
//        final RepositoryManager<PlayerDetails, PlayerDetailsRepository> repositoryManager =
//                new PlayerDetailsRepositoryManager();
//        repositoryManager.getRepository().readAll().stream()
//                .filter(player -> player.getUnlcaimedPrizes() != null)
//                .forEach(player -> player.getUnlcaimedPrizes().add(item));
//        repositoryManager.updateRepository();
    }

    private void generateCommunityRewards() {
        if (communityPoints.value() == 5) {
            spawnCommunityGift(1);
        } else if (communityPoints.value() == 50) {
            spawnCommunityGift(2);
        } else if (communityPoints.value() == 100) {
            spawnCommunityGift(3);
        } else if (communityPoints.value() == 500) {
            spawnCommunityGift(4);
        } else if (communityPoints.value() == 1000) {
            spawnCommunityGift(5);
        }
    }

    private int getNextTier() {
        if (communityPoints.lessThan(5))
            return 5;
        else if (communityPoints.value() >= 5 && communityPoints.lessThan(50))
            return 50;
        else if (communityPoints.value() >= 50 && communityPoints.lessThan(100))
            return 100;
        else if (communityPoints.value() >= 100 && communityPoints.lessThan(500))
            return 500;
        else if (communityPoints.value() >= 500 && communityPoints.lessThan(1000))
            return 1000;
        else if (communityPoints.value() >= 1000 && communityPoints.lessThan(1500))
            return 1500;
        return 0;
    }

    public void openGift(Player player) {
//        if (!player.getPlayerDetails().hasOpenedGift()
//                && player.getPlayerDetails().getUnlcaimedPrizes() != null) {
//            player.getPlayerDetails().getUnlcaimedPrizes().forEach(item -> ItemAssistant.addItemToInventoryOrDrop(player,item.getId(),item.getAmount().value()));
//            player.receiveMessage("You open the Community Gift and receive an assortment of prizes!");
//            player.getPlayerDetails().getGraveDiggerProperties().getPoints().add(10 * activeGift.getTierMultiplier());
//            player.receiveMessage("You earned " + (10 * activeGift.getTierMultiplier()) + " points!");
//            final List<Loot> lootList = new ArrayList<>();
//
//            lootList.add(new Loot(new Item(16903,1),0.05f));
//            lootList.add(new Loot(new Item(16898,1),0.05f));
//            lootList.add(new Loot(new Item(16891,1),0.09f));
//            lootList.add(new Loot(new Item(16904,1),0.09f));
//            lootList.add(new Loot(new Item(21794,1),0.09f));
//            switch (activeGift.getTier()) {
//                case 4:
//                case 5:
//                case 6:
//                    LootableContainer lootableContainer = new LootableItem(-1,"dummy_item",lootList);
//                    player.loot(lootableContainer);
//                    break;
//            }
//
//            player.getPlayerDetails().setOpenedGift(true);
//            player.getPlayerDetails().getUnlcaimedPrizes().clear();
//            saveProperties(player);
//        } else {
//            player.receiveMessage("There is currently no gift available. Community Points: "
//                    + communityPoints.value()
//                    + " Next Reward at:"
//                    + getNextTier());
//        }
    }

    public CommunityGift getActiveGift() {
        return activeGift;
    }

    public void setActiveGift(CommunityGift activeGift) {
        this.activeGift = activeGift;
    }

    private static class DailyReset extends TimerTask {

        @Override
        public void run() {
//            final RepositoryManager<PlayerDetails, PlayerDetailsRepository> repositoryManager =
//                    new PlayerDetailsRepositoryManager();
//
//            repositoryManager.getRepository().readAll()
//                    .stream()
//                    .filter(user -> user.getGraveDiggerProperties() != null)
//                    .forEach(user -> user.getGraveDiggerProperties().getAttempts().setValue(RESET_AMOUNT_BASE));
//
//            repositoryManager.updateRepository();
        }
    }
}

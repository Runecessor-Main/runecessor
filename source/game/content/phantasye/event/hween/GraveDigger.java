package game.content.phantasye.event.hween;

import com.google.gson.Gson;
import game.content.phantasye.PlayerDetails;
import game.content.phantasye.PlayerDetailsRepository;
import game.content.phantasye.PlayerDetailsRepositoryManager;
import game.menaphos.looting.model.item.Item;
import game.menaphos.looting.model.loot.Loot;
import game.menaphos.looting.model.loot.LootableContainer;
import game.menaphos.looting.model.loot.LootableObject;
import game.menaphos.looting.model.loot.factory.LootFactory;
import game.player.Player;
import game.player.PlayerHandler;
import org.menaphos.model.math.impl.AdjustableInteger;
import org.phantasye.RepositoryManager;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class GraveDigger {

    private static final int ATTEMPT_COST_GP = 50000000;
    private static final int ATTEMPT_COST_DT = 30;
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

    private boolean consumeAttempt(Player player) {
        if (player.getPlayerDetails().getGraveDiggerProperties().getAttempts().greaterThan(0)) {
            player.getPlayerDetails().getGraveDiggerProperties().getAttempts().decrement();
            saveProperties(player);
        } else if (player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().greaterThan(0)) {
            player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().decrement();
            saveProperties(player);
        } else {
            player.receiveMessage("You do not have any attempts remaining.");
            return false;
        }
        return true;
    }

    private void saveProperties(Player player) {
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
            player.getPlayerDetails().getGraveDiggerProperties().getPoints().add(10);
            saveProperties(player);
            saveEvent();
            player.receiveMessage(
                    "You consume an attempt and earn 10 Halloween Points. "
                            + (player.getPlayerDetails().getGraveDiggerProperties().getAttempts().value()
                            + player.getPlayerDetails().getGraveDiggerProperties().getPaidAttempts().value())
                            + " Attempts Remaining.");
            this.generateCommunityRewards();
        }
    }

    private void spawnCommunityGift(int tier) {
        PlayerHandler.getPlayers().forEach(player -> {
            player.getPlayerDetails().setOpenedGift(false);
            player.receiveMessage("The Community has gathered " + communityPoints.value() + " points and a new gift has spawned!");
            saveProperties(player);
        });
        this.setActiveGift(new CommunityGift(tier));
        this.saveEvent();
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
        else if (communityPoints.greaterThan(5) && communityPoints.lessThan(50))
            return 50;
        else if (communityPoints.greaterThan(50) && communityPoints.lessThan(100))
            return 100;
        else if (communityPoints.greaterThan(100) && communityPoints.lessThan(500))
            return 500;
        else if (communityPoints.greaterThan(500) && communityPoints.lessThan(1000))
            return 1000;
        return 0;
    }

    public void openGift(Player player) {
        if (!player.getPlayerDetails().hasOpenedGift() && activeGift != null) {
            activeGift.getLootList().forEach(loot -> player.addItemToInventory(loot.getItem().getId(), loot.getItem().getAmount()));
            player.receiveMessage("You open the Community Gift and receive an assortment of prizes!");
            player.getPlayerDetails().setOpenedGift(true);
            saveProperties(player);
        } else {
            player.receiveMessage("There is currently no gift available. Community Points: "
                    + communityPoints.value()
                    + " Next Reward at:"
                    + getNextTier());
        }
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
            final RepositoryManager<PlayerDetails, PlayerDetailsRepository> repositoryManager =
                    new PlayerDetailsRepositoryManager();

            repositoryManager.getRepository().readAll()
                    .stream()
                    .filter(user -> user.getGraveDiggerProperties() != null)
                    .forEach(user -> user.getGraveDiggerProperties().getAttempts().setValue(RESET_AMOUNT_BASE));

            repositoryManager.updateRepository();
        }
    }
}

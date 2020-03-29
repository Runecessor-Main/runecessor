package game.content.phantasye;

import game.content.phantasye.skill.support.slayer.task.PlayerSlayerTask;
import org.menaphos.model.math.impl.AdjustableInteger;

import java.util.*;

public class PlayerDetails {

    private final String id;
    private final AdjustableInteger slayerPoints;
    private final AdjustableInteger preferCap;
    private final AdjustableInteger blockCap;
    private final AdjustableInteger tasksCompleted;
    private final AdjustableInteger taskStreak;
    private final List<Integer> preferredTasks;
    private final List<Integer> blockedTasks;
    private List<Integer> unlocksList;
    private PlayerSlayerTask slayerTask;
    private int slayerMaster;
    private Map<Integer,AdjustableInteger> herbSack;
    private List<Integer> paymentRunes;
    private Map<Integer,AdjustableInteger> npcKillMap;

    private AdjustableInteger mythicalCapeCharges;
    private AdjustableInteger mythicalCapeTier;

    private AdjustableInteger magicFindRating;

    public PlayerDetails(String id) {
        this.id = id;
        this.unlocksList = new ArrayList<>();
        this.preferCap = new AdjustableInteger(1);
        this.blockCap = new AdjustableInteger(1);
        this.slayerPoints = new AdjustableInteger(0);
        this.tasksCompleted = new AdjustableInteger(0);
        this.taskStreak = new AdjustableInteger(0);
        this.preferredTasks = new ArrayList<>();
        this.blockedTasks = new ArrayList<>();
        this.herbSack = new HashMap<>();
        this.paymentRunes = new ArrayList<>();
        this.magicFindRating = new AdjustableInteger(0);
        this.mythicalCapeCharges = new AdjustableInteger(0);
        this.mythicalCapeTier = new AdjustableInteger(0);
        this.npcKillMap = new HashMap<>();
    }

    public void initialize() {
        final PlayerDetails details = new PlayerDetails("-1");
        Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> field.setAccessible(true));
        Arrays.stream(details.getClass().getDeclaredFields()).forEach(field -> field.setAccessible(true));
        Arrays.stream(this.getClass().getDeclaredFields()).forEach(field -> {
            try {
                if(field.get(this) == null)
                    field.set(this,details.getClass().getDeclaredField(field.getName()).get(details));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        });
    }

    public Map<Integer, AdjustableInteger> getNpcKillMap() {
        return npcKillMap;
    }

    public AdjustableInteger getMagicFindRating() {
        return magicFindRating;
    }

    public AdjustableInteger getMythicalCapeCharges() {
        return mythicalCapeCharges;
    }

    public AdjustableInteger getMythicalCapeTier() {
        return mythicalCapeTier;
    }

    public List<Integer> getPaymentRunes() {
        return paymentRunes;
    }

    public Map<Integer, AdjustableInteger> getHerbSack() {
        return herbSack;
    }

    public int getSlayerMaster() {
        return slayerMaster;
    }

    public void setSlayerMaster(int slayerMaster) {
        this.slayerMaster = slayerMaster;
    }

    public List<Integer> getUnlocksList() {
        return unlocksList;
    }

    public AdjustableInteger getTaskStreak() {
        return taskStreak;
    }

    public AdjustableInteger getTasksCompleted() {
        return tasksCompleted;
    }

    public AdjustableInteger getBlockCap() {
        return blockCap;
    }

    public AdjustableInteger getPreferCap() {
        return preferCap;
    }

    public List<Integer> getBlockedTasks() {
        return blockedTasks;
    }

    public List<Integer> getPreferredTasks() {
        return preferredTasks;
    }

    public AdjustableInteger getSlayerPoints() {
        return slayerPoints;
    }

    public PlayerSlayerTask getSlayerTask() {
        return slayerTask;
    }

    public void setSlayerTask(PlayerSlayerTask slayerTask) {
        this.slayerTask = slayerTask;
    }

    public String getId() {
        return id;
    }
}

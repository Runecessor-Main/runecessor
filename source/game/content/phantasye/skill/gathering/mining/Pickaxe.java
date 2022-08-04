package game.content.phantasye.skill.gathering.mining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Pickaxe {

    BRONZE(1265, 1, 1, 6753),
    IRON(1267, 1, 2, 6754),
    STEEL(1269, 6, 3, 6755),
    MITHRIL(1273, 21, 4, 6757),
    ADAMANT(1271, 31, 5, 6756),
    RUNE(1275, 41, 6, 6752),
    DRAGON(11920, 61, 8, 7139),
    DRAGON_ORNAMENTAL(12797, 61, 8, 643),
    INFERNAL(13243, 75, 9, 4483),
    THIRD_AGE_PICK(20014, 80, 10, 7283);

    private int pickId, levelRequirement, power, animation;

    private Pickaxe(int pickId, int levelRequirement, int power, int animation) {
        this.pickId = pickId;
        this.levelRequirement = levelRequirement;
        this.power = power;
        this.animation = animation;
    }

    public int getPick() {
        return pickId;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public int getEfficiency() {
        return power;
    }

    public int getAnimation() {
        return animation;
    }

    public static List<Integer> getIds() {
        final List<Integer> idList = new ArrayList<>();
        Arrays.stream(Pickaxe.values()).mapToInt(Pickaxe::getPick).forEach(idList::add);
        return idList;
    }
}

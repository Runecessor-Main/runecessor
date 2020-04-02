package game.content.phantasye.item.degradable.impl;

import game.content.skilling.prayer.BuryBone;

import java.util.Optional;

public class DragonboneNecklace {

    public static final int ID = 22111;
    public static final int PRAYER_LEVEL = 80;

    public static Optional<BuryBone.Bones> getBones(int id) {
        return Bonecrusher.getBones(id);
    }

    public static int getXPForBones(BuryBone.Bones bones) {
        switch (bones) {
            case REGULAR:
            case BAT:
                return 1;
            case BIG:
                return 2;
            case BABY_DRAGON:
                return 3;
            case WYVERN:
            case DRAGON:
            case LAVA_DRAGON:
            case DAGANNOTH:
                return 4;
            case SUPERIOR:
                return 5;
            default: return 0;
        }
    }
}

package game.content.phantasye.item.degradable.impl;

import game.content.skilling.prayer.BuryBone;

import java.util.Arrays;
import java.util.Optional;

public class Bonecrusher  {

    public static final int ID = 13116;
    public static final int CHARGE_ID = 20527;

    public static Optional<BuryBone.Bones> getBones(int id) {
        return Arrays.stream(BuryBone.Bones.values()).filter(bone -> bone.getItemId() == id).findAny();
    }
}

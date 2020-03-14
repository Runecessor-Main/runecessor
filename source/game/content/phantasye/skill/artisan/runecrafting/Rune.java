package game.content.phantasye.skill.artisan.runecrafting;

import utility.Misc;

public enum Rune {

    AIR(556,5,false,11,22,33,44,55,66,77,88,99),
    MIND(558,6,false,14,28,42,56,70,84),
    WATER(555,7,false,19,38,57,76,95),
    EARTH(557,8,false,26,52,78),
    FIRE(554,9,false,35,70),
    BODY(559,10,false,46,92),
    COSMIC(564,11,true),
    CHAOS(562,12,true),
    ASTRAL(9075,13,true),
    NATURE(561,14,true),
    LAW(563,15,true),
    DEATH(560,16,true),
    BLOOD(565,24,true),
    SOUL(566,30,true),
    WRATH(-1,10,true),
    MIST(4695,15,true),
    DUST(4696,15,true),
    MUD(4698,16,true),
    SMOKE(4697,17,true),
    STEAM(4694,17,true),
    LAVA(4699,18,true);

    private final int itemId;
    private final int xp;
    private final boolean members;
    private final int[] levelUnlocks;

    private Rune(final int itemId, final int xp, final boolean members,final int...levelUnlocks) {
        this.itemId = itemId;
        this.xp = xp;
        this.members= members;
        this.levelUnlocks = levelUnlocks;
    }

    public int getItemId() {
        return itemId;
    }

    public boolean isMembers() {
        return members;
    }

    public int getXp() {
        return xp;
    }

    public int[] getLevelUnlocks() {
        return levelUnlocks;
    }

    @Override
    public String toString() {
        return Misc.capitalize(name());
    }
}


package game.content.phantasye.skill.slayer;

import utility.Misc;

public enum  Locations {

    SLAYER_TOWER(3428,3537,0),
    FREMENNIK_DUNGEON(2808,10002,0),
    WILDERNESS_VOLACANO(3351,3914,0),
    ARDOUGNE(2661,3306,0),
    VARROCK(3213,3424,0),
    EDGEVILLE_DUNGEON(3096,9867,0),
    TAVERLY_DUNGEON(2884,9798,0),
    BRIMHAVEN_DUNGEON(2713,9564,0),
    LAVA_MAZE(3202,3850,0),
    KING_BLACK_DRAGON(2271,4680,0),
    DUNGEONZONE(1748,5325,0),
    VORKATH(2272,4045,0),
    WARRIORS_GUILD(2851,3548,0),
    DAGANNOTH_KINGS(1904,4366,0),
    LUMBRIDGE(3222,3218,0),
    GENERAL_GRAARDOR(2864,5354,0),
    REVENANT_CAVES(3127,3833,0),
    EAST_DRAGONS(3348,3647,0),
    CERBERUS(1240,1226,0),
    ICE_STRYKEWYRMS(2977,3873,0),
    AL_KHARID(3276,3167,0),
    WYVERN_CAVE(3056,9563,0),
    TZHAAR(2452,5167,0),
    TORMENTED_DEMON(3260,3705,0),
    SHADOW_CAVE(2740,5111,0),
    SCORPIA(3231,3944,0),
    BARROWS(3565,3315,0),
    CALLISTO(3202,3865,0),
    CHAOS_ELEMENTAL(3307,3916,0),
    CHAOS_FANATIC(2979,3848,0),
    GIANT_MOLE(1760,5194,0),
    KRIL_TSUTSAROTH(2925,5331,0),
    KALPHITE_QUEEN(3508,9494,0),
    KREEARRA(2839,5296,0),
    VENANITIS(3308,3737,0),
    VETION(3220,3789,0),
    ZULRAH(2200,3056,0);


    private final int x;
    private final int y;
    private final int height;

    private Locations(int x, int y, int height) {
        this.x = x;
        this.y = y;
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return Misc.capitalize(name().toLowerCase().replaceAll("_", " "));
    }
}
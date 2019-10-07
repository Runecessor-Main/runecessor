package game.content.phantasye.skill.slayer.task;

public enum BossTask {

    BARROWS(70,1673,1677,1674,1676,1672,1675),
    CALLISTO(100,6503),
    CERBERUS(110,5862),
    CHAOS_ELEMENTAL(80,2084),
    CHAOS_FANATIC(90,6619),
    COMMANDER_ZILYANA(95,2205),
    CRAZY_ARCHAEOLOGIST(95,6618),
    DAGANNOTH_KINGS(85,2265,2266,2267),
    GENERAL_GRAARDOR(95,2215),
    GIANT_MOLE(65,5779),
    KRIL_TSUTSAROTH(95,3129),
    KALPHITE_QUEEN(85,965),
    KING_BLACK_DRAGON(70,239),
    KREEARRA(95,3162),
    SCORPIA(105,6615),
    VENANITIS(110,6504),
    VETION(110,6611),
    VORKATH(120,8059),
    ZULRAH(120,2042,2043,2044);

    private final int combatLevel;
    private final int[] npcs;

    private BossTask(int combatLevel,int...npcs) {
        this.combatLevel = combatLevel;
        this.npcs = npcs;
    }

    public int[] getNpcs() {
        return npcs;
    }

    public int getCombatLevel() {
        return combatLevel;
    }
}

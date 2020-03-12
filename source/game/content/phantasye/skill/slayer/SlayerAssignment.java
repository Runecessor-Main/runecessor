package game.content.phantasye.skill.slayer;

import utility.Misc;

public enum SlayerAssignment {

    ABERRANT_SPECTRES(60,new Locations[] {Locations.SHADOW_CAVE},2),
    ABYSSAL_DEMONS(85,new Locations[]{Locations.SLAYER_TOWER},415,7410,5886),
    BANSHEE(15,new Locations[]{Locations.SLAYER_TOWER},414,7390),
    BASILISKS(40,new Locations[]{Locations.FREMENNIK_DUNGEON},417,7395),
    BATS(1,new Locations[]{Locations.TAVERLY_DUNGEON},2834),
    BEARS(1,new Locations[]{Locations.VARROCK,Locations.ARDOUGNE,Locations.SHADOW_CAVE},2838),
    BLACK_DEMONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.TAVERLY_DUNGEON,Locations.EDGEVILLE_DUNGEON},2048,7144,3132),
    BLACK_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.LAVA_MAZE,Locations.TAVERLY_DUNGEON,Locations.KING_BLACK_DRAGON,Locations.DUNGEONZONE},259,7275,2642),
    BLOODVELD(50,new Locations[]{Locations.SLAYER_TOWER},484,7397,7398),
    BLUE_DRAGONS(1,new Locations[]{Locations.TAVERLY_DUNGEON,Locations.DUNGEONZONE,Locations.VORKATH},265,243,7273,8059),
    BRONZE_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON},270),
    CYCLOPES(1,new Locations[]{Locations.WARRIORS_GUILD},2463),
    DAGANNOTH(1,new Locations[]{Locations.DAGANNOTH_KINGS},3185,2265,2266,2267),
    DUST_DEVILS(65,new Locations[]{Locations.SLAYER_TOWER},423),
    CRAWLING_HANDS(5,new Locations[]{Locations.SLAYER_TOWER},448),
    COWS(1,new Locations[]{Locations.LUMBRIDGE},2805),
    COCKATRICES(25,new Locations[]{Locations.FREMENNIK_DUNGEON},419),
    DARK_BEASTS(90,new Locations[]{Locations.SLAYER_TOWER},4005),
    DWARFS(1,new Locations[]{Locations.TAVERLY_DUNGEON},291),
    FIRE_GIANTS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON},7251),
    GARGOYLES(75,new Locations[]{Locations.SLAYER_TOWER},412,7407),
    GHOSTS(1,new Locations[]{Locations.SLAYER_TOWER},6815),
    GOBLINS(1,new Locations[]{Locations.LUMBRIDGE,Locations.GENERAL_GRAARDOR,Locations.TAVERLY_DUNGEON,Locations.REVENANT_CAVES},3049,6605,3029,2216,2218,2217,3031,3032,3033,3034,3035),
    GREATER_DEMONS(1,new Locations[]{Locations.SHADOW_CAVE},2025,2028,2027,2026,3129,3130),
    GREEN_DRAGONS(1,new Locations[]{Locations.EAST_DRAGONS},260,261,264),
    HELL_HOUNDS(1,new Locations[]{Locations.TAVERLY_DUNGEON,Locations.CERBERUS},104,15256,5862),
    HILL_GIANTS(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},7261),
    ICE_GIANTS(1,new Locations[]{Locations.ICE_STRYKEWYRMS},2085),
    ICE_STRYKEWYRMS(1,new Locations[]{Locations.ICE_STRYKEWYRMS},9463),
    ICE_WARRIORS(1,new Locations[]{Locations.ICE_STRYKEWYRMS},2841),
    INFERNAL_MAGES(45,new Locations[]{Locations.SLAYER_TOWER},443),
    IRON_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON},272),
    KURASKS(70,new Locations[]{Locations.FREMENNIK_DUNGEON},410,7405),
    MOSS_GIANTS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.EDGEVILLE_DUNGEON},7262),
    NECHRYAELS(80,new Locations[]{Locations.SLAYER_TOWER},8,7411),
    PYRE__FIENDS(30,new Locations[]{Locations.FREMENNIK_DUNGEON},433,7394),
    RED_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.DUNGEONZONE,Locations.LAVA_MAZE},247,7274),
    SCORPIONS(1,new Locations[]{Locations.SCORPIA},3024),
    WYVERNS(72,new Locations[]{Locations.WYVERN_CAVE},465),
    SKELETONS(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},70),
    SPIDERS(1,new Locations[]{Locations.LUMBRIDGE,Locations.TAVERLY_DUNGEON,Locations.SHADOW_CAVE},3018,3017,3021,6504,5373),
    STEEL_DRAGONS(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},274),
    TUROTHS(55,new Locations[]{Locations.FREMENNIK_DUNGEON},428),
    TZHARR(1,new Locations[]{Locations.TZHAAR},2167),
    ZOMBIES(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},26),
    TORMENTED_DEMONS(80,new Locations[]{Locations.TORMENTED_DEMON},8366),
    BARROWS(70,new Locations[]{Locations.BARROWS},1673,1677,1674,1676,1672,1675),
    CALLISTO(100,new Locations[]{Locations.CALLISTO},6503),
    CERBERUS(110,new Locations[]{Locations.CERBERUS},5862),
    CHAOS_ELEMENTAL(80,new Locations[]{Locations.CHAOS_ELEMENTAL},2084),
    CHAOS_FANATIC(90,new Locations[]{Locations.CHAOS_FANATIC},6619),
    COMMANDER_ZILYANA(95,new Locations[]{Locations.TORMENTED_DEMON},2205),
    CRAZY_ARCHAEOLOGIST(95,new Locations[]{Locations.TORMENTED_DEMON},6618),
    DAGANNOTH_KINGS(85,new Locations[]{Locations. DAGANNOTH_KINGS},2265,2266,2267),
    GENERAL_GRAARDOR(95,new Locations[]{Locations.GENERAL_GRAARDOR},2215),
    GIANT_MOLE(65,new Locations[]{Locations.GIANT_MOLE},5779),
    KRIL_TSUTSAROTH(95,new Locations[]{Locations.KRIL_TSUTSAROTH},3129),
    KALPHITE_QUEEN(85,new Locations[]{Locations.KALPHITE_QUEEN},965),
    KING_BLACK_DRAGON(70,new Locations[]{Locations.KING_BLACK_DRAGON},239),
    KREEARRA(95,new Locations[]{Locations.KREEARRA},3162),
    SCORPIA(105,new Locations[]{Locations.SCORPIA},6615),
    VENANITIS(110,new Locations[]{Locations.VENANITIS},6504),
    VETION(110,new Locations[]{Locations.VETION},6611),
    VORKATH(120,new Locations[]{Locations.VORKATH},8059),
    ZULRAH(120,new Locations[]{Locations.ZULRAH},2042,2043,2044);


    private final int level;
    private final Locations[] locations;
    private final int[] npcs;

    private SlayerAssignment(int level, Locations[] locations, int...npcs) {
        this.level = level;
        this.locations = locations;
        this.npcs = npcs;
    }

    public int getLevel() {
        return level;
    }

    public int[] getNpcs() {
        return npcs;
    }

    public Locations[] getLocations() {
        return locations;
    }

    @Override
    public String toString() {
        return Misc.capitalize(name().replaceAll("_"," ").toLowerCase());
    }
}

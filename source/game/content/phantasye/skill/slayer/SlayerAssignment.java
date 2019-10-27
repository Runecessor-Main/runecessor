package game.content.phantasye.skill.slayer;

import utility.Misc;

public enum SlayerAssignment {

    ABERRANT_SPECTRES(60,new Locations[] {Locations.SLAYER_TOWER},69,420),
    ABYSSAL_DEMONS(85,new Locations[]{Locations.SLAYER_TOWER},415,7410,5886),
    BANSHEE(15,new Locations[]{Locations.SLAYER_TOWER},414,7390),
    BASILISKS(40,new Locations[]{Locations.FREMENNIK_DUNGEON},417,7395),
    BATS(1,new Locations[]{Locations.MORYTANIA},2834),
    BEARS(1,new Locations[]{Locations.VARROCK,Locations.ARDOUGNE},2838),
    BLACK_DEMONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.TAVERLY_DUNGEON,Locations.EDGEVILLE_DUNGEON},2048),
    BLACK_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.LAVA_MAZE,Locations.TAVERLY_DUNGEON,Locations.KING_BLACK_DRAGON,Locations.DUNGEONZONE},259,7275,2642),
    BLOODVELD(50,new Locations[]{Locations.SLAYER_TOWER},484,7397,7398),
    BLUE_DRAGONS(1,new Locations[]{Locations.TAVERLY_DUNGEON,Locations.DUNGEONZONE,Locations.VORKATH},265,243,7273,8059),
    BRONZE_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON},270),
    CYCLOPES(1,new Locations[]{Locations.WARRIORS_GUILD},2463),
    DAGANNOTH(1,new Locations[]{Locations.DAGANNOTH_KINGS},3185),
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
    GREATER_DEMONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON},2026),
    GREEN_DRAGONS(1,new Locations[]{Locations.EAST_DRAGONS},260),
    HELL_HOUNDS(1,new Locations[]{Locations.TAVERLY_DUNGEON,Locations.CERBERUS},104,15256),
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
    RED_DRAGONS(1,new Locations[]{Locations.BRIMHAVEN_DUNGEON,Locations.DUNGEONZONE},247,7274),
    SCORPIONS(1,new Locations[]{Locations.AL_KHARID},3024),
    WYVERNS(72,new Locations[]{Locations.WYVERN_CAVE},465),
    SKELETONS(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},70),
    SPIDERS(1,new Locations[]{Locations.LUMBRIDGE,Locations.TAVERLY_DUNGEON},3017,3021),
    STEEL_DRAGONS(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},274),
    TUROTHS(55,new Locations[]{Locations.FREMENNIK_DUNGEON},428),
    TZHARR(1,new Locations[]{Locations.TZHAAR},2167),
    ZOMBIES(1,new Locations[]{Locations.EDGEVILLE_DUNGEON},26),
    TORMENTED_DEMONS(80,new Locations[]{Locations.TORMENTED_DEMON},8366),
    BARROWS(70,new Locations[]{Locations.TORMENTED_DEMON},1673,1677,1674,1676,1672,1675),
    CALLISTO(100,new Locations[]{Locations.TORMENTED_DEMON},6503),
    CERBERUS(110,new Locations[]{Locations.TORMENTED_DEMON},5862),
    CHAOS_ELEMENTAL(80,new Locations[]{Locations.TORMENTED_DEMON},2084),
    CHAOS_FANATIC(90,new Locations[]{Locations.TORMENTED_DEMON},6619),
    COMMANDER_ZILYANA(95,new Locations[]{Locations.TORMENTED_DEMON},2205),
    CRAZY_ARCHAEOLOGIST(95,new Locations[]{Locations.TORMENTED_DEMON},6618),
    DAGANNOTH_KINGS(85,new Locations[]{Locations.TORMENTED_DEMON},2265,2266,2267),
    GENERAL_GRAARDOR(95,new Locations[]{Locations.TORMENTED_DEMON},2215),
    GIANT_MOLE(65,new Locations[]{Locations.TORMENTED_DEMON},5779),
    KRIL_TSUTSAROTH(95,new Locations[]{Locations.TORMENTED_DEMON},3129),
    KALPHITE_QUEEN(85,new Locations[]{Locations.TORMENTED_DEMON},965),
    KING_BLACK_DRAGON(70,new Locations[]{Locations.TORMENTED_DEMON},239),
    KREEARRA(95,new Locations[]{Locations.TORMENTED_DEMON},3162),
    SCORPIA(105,new Locations[]{Locations.TORMENTED_DEMON},6615),
    VENANITIS(110,new Locations[]{Locations.TORMENTED_DEMON},6504),
    VETION(110,new Locations[]{Locations.TORMENTED_DEMON},6611),
    VORKATH(120,new Locations[]{Locations.TORMENTED_DEMON},8059),
    ZULRAH(120,new Locations[]{Locations.TORMENTED_DEMON},2042,2043,2044);






//    ABYSSAL_DEMONS,ANKOUS,BANSHEES,BASILISKS,BATS,BEARS,BLACK_DEMONS,BLACK_DRAGONS,BLOODVELD,BLUE_DRAGONS,
//    BRONZE_DRAGONS,CYCLOPES,DAGANNOTH,CRAWLING_HANDS,COWS,CAVE_SLIMES,COCKATRICES,CAVE_HORRORS,DARK_BEASTS,DWARFS,FIRE_GIANTS,
//    GARGOYLES,GHOSTS,GOBLINS,GREATER_DEMONS,GREEN_DRAGONS,HELL_HOUNDS,HILL_GIANTS,ICE_GIANTS,ICE_STRYKEWYRMS,ICE_WARRIORS,
//    INFERNAL_MAGES,IRON_DRAGONS,KALPHITES,KURASKS,LESSER_DEMONS,MITHRIL_DRAGONS,MOSS_GIANTS,NECHRYAELS,PYRE__FIENDS,RED_DRAGONS,
//    SCORPIONS,SHADES,WYVERNS,SKELETONS,SPIDERS,STEEL_DRAGONS,TUROTHS,TZHARR,ZOMBIES,TORMENTED_DEMONS;

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

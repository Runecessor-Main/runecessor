package game.content.miscellaneous;


/**
 * The teleport data.
 * 
 * @author Adam_#6723
 */
public enum TeleportData1 {

	TRAIN( 17031, 52119, "Training Dungeon", "Training Dungeon", 0, 0, "0", "0", TeleportType1.MONSTERS, 3112, 9676, 0, 5, false, 239, new int[] {-1, -1, -1, -1, -1, -1}),
	SLAYER( 17032, 52120, "Slayer tower", "Slayer tower", 0, 0, "0", "0", TeleportType1.MONSTERS, 3428, 3537, 0, 10, false, 8095, new int[] {-1, -1, -1, -1, -1}),
	BRIM( 17033, 52121, "Brimhaven dungeon", "Brimhaven dungeon", 0, 0, "0", "0", TeleportType1.MONSTERS, 2713, 9564, 1, 0, false, 1701, new int[] {-1, -1, -1, -1}),
	BANDIT(17034, 52122, "Bandit camp", "Bandit camp", 0, 0, "0", "0", TeleportType1.MONSTERS, 3169, 2990, 0, 10, false, 2, new int[] {-1, -1, -1, -1}),
	TAV(17035, 52123, "Taverley dungeon", "Taverley dungeon", 0, 0, "0", "0", TeleportType1.MONSTERS, 2884, 9798, 0, 10, false, 2, new int[] {-1, -1, -1, -1}),

	EDG(17035, 52124, "Edgeville dungeon", "Edgeville dungeon", 0, 0, "0", "0", TeleportType1.MONSTERS, 3096, 9867, 0, 10, false, 2, new int[] {-1, -1, -1, -1}),
	FRE(17035, 52125, "Fremennik dungeon", "Fremennik dungeon", 0, 0, "0", "0", TeleportType1.MONSTERS, 2808, 10002, 0, 10, false, 2, new int[] {-1, -1, -1, -1}),
	WYV(17035, 52126, "Wyvern cave ", "Wyvern cave", 0, 0, "0", "0", TeleportType1.MONSTERS, 3056, 9564, 0, 10, false, 2, new int[] {-1, -1, -1, -1}),

	
	
	ZULRAH( 17031, 52119, "Zulrah", "", 300, 0, "Range", "@red@Hard", TeleportType1.BOSSES, 2200, 3056, 0, 10, false, 10, new int[] { 12921, 13201, 13200, 12936, 6571, 12927, 12932, 12922}),
	SIRE( 17032, 52120, "Abyssal Sire", "", 750, 0, "Range", "@red@Hard", TeleportType1.BOSSES,3105 ,4836, 0, 10, false, 10, new int[] { 13263, 13265, 7979, 13262, -1, -1, -1, -1}),
	ARCH( 17033, 52121, "Deranged Arch", "Deranged Archelogist", 250, 2, "Magic", "Extreme", TeleportType1.BOSSES, 3682, 3715, 0, 10, false, 10, new int[] { 6605, 12073, 11990, 10976,11212,9194,-1,-1}),
	VORK( 17034, 52122, "Vorkath", "Vorkath", 750, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 2272, 4045, 0, 10, false, 10, new int[] { 21992, 16356, 22109, 21907, 22003, 11284, -1, -1}),
	KRAKEN( 17035, 52123, "Kraken", "Kraken", 291, 0, "Random", "@red@Hard", TeleportType1.BOSSES,5 , 5 , 5, 10, false, 10, new int[] { 2577, 12655, 12007, 11905, 12073, 12004, -1, -1}),
	SHMAN( 17036, 52124, "Shaman", "Lizardman", 150, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 1481, 3697, 0, 10, false, 10, new int[] { 16261, 10977, 13576, 12073, -1, -1, -1, -1}),
	KEL( 17037, 52125, "Kalphite queen", "Kalphite queen", 255, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 3507, 9494, 0, 10, false, 10, new int[] { 12647, 12885, 7158, 7981, -1, -1, -1, -1}),
	MOLE( 17038, 52126, "Giant mole", "Giant mole", 200, 0, "Random", "@red@Medium", TeleportType1.BOSSES, 1760, 5194, 0, 10, false, 10, new int[] { 12646, 12073, -1, -1, -1, -1, -1, -1}),
	CORP( 17039, 52127, "Corporeal Beast", "Corporeal Beast", 2000, 0, "Random", "@red@Very Hard", TeleportType1.BOSSES, 2966, 4382, 2, 10, false, 10, new int[] { 12816, 12819, 12827, 12823, -1, -1, -1, -1}),
	CERB( 17040, 52128, "Cerberus", "Cerberus", 600, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 1240, 1226, 0, 10, false, 10, new int[] { 13247, 13245, 13233, 13227, 13229, 13231, 12073, -1}),
	KRIL( 17041, 52129, "K'ril Tsutsaroth", "K'ril Tsutsaroth", 255, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 2925, 5331, 2, 10, false, 10, new int[] { 12652, 11816, 11791, -1, -1, -1, -1, -1}),
	COMM( 17042, 52130, "Commander Zilyana", "Commander Zilyana", 254, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 2907,5265, 0, 10, false, 10, new int[] { 12651, 11814, 11785, -1, -1, -1, -1, -1}),
	KREE( 17044, 52131, "Kree'arra", "Kree'arra", 255, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 2839,5296, 2, 10, false, 10, new int[] { 12649, 11810, 11830, 11828, 11826, 11822, 11820, 11818}),
	GEN( 17045, 52132, "General Graardor", "General Graardor", 255, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 2864,5354, 2, 10, false, 10, new int[] { 12650, 11812, 11836, 11834, 11832, 11822, 11820, 11818}),
	DAG( 17046, 52133, "Dagannoth Kings", "Dagannoth Kings", 255, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 1904,4366, 0, 10, false, 10, new int[] { -1, -1, -1, -1, -1, -1, -1, -1}),
	KING(17047, 52134, "KBD", "KBD", 240, 0, "Random", "@red@Hard", TeleportType1.BOSSES, 2271,4680, 0, 10, false, 10, new int[] { 12653, 11286, 11920, 12073, -1, -1, -1, -1}),

	//Skilling
	NEW_1( 17031, 52119, "Entrana Skilling Zone", "Entrana Skilling Zone", 0, 0, "", "", TeleportType1.SKILLING,  2866, 3337, 0, 79, false, 10, new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
		}),
	NEW_2( 17031, 52120, "Dwarven Mine", "Dwarven Mine", 0, 0, "", "", TeleportType1.SKILLING, 3023, 9739, 0, 79, true, 260
			, new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
		    		}),
	NEW_3( 17031, 52121, "Agility", "Agility",  0, 0, "", "", TeleportType1.SKILLING, 2998 ,3906, 0, -1, true, -1
			, new int[] {-1, -1, -1, -1, -1, -1, -1, -1 
		    		}),
	NEW_4( 17031, 52122, "Hunter", "Hunter", 0, 0, "", "", TeleportType1.SKILLING,  2340 , 3584, 0,  -1, true, -1,
			new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	
	NEW_5( 17031, 52123, "Mining", "Mining", 0, 0, "", "", TeleportType1.SKILLING,  3023 ,9739, 0,  -1, true, -1,
			new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	NEW_6( 17031, 52124, "Smithing", "Smithing", 0, 0, "", "", TeleportType1.SKILLING,  2469, 3436, 0,  -1, true, -1,
			new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	NEW_7( 17031, 52125, "Fishing", "Fishing", 0, 0, "", "", TeleportType1.SKILLING,  2552, 3563, 0,  -1, true, -1,
			new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	//Wilderness
	NEW16(17031, 52119, "Magebank @gr3@(safe)", "Magebank @gr3@(safe)", 0, 0, "", "", TeleportType1.WILDERNESS, 2537, 4714, 0, 79, true, 260, new int[] { -1, -1, -1, -1, -1, -1, -1, -1}),
	NEW17(17031, 52120, "West Dragons@red@(10)", "West Dragons @red@(Level 10)", 0, 0, "", "", TeleportType1.WILDERNESS, 2979, 3594, 0, 79, true, 260, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1}),
	NEW18(17031, 52121, "East Dragons @red@(17)", "East Dragons @red@(Level 17)", 0, 0, "", "", TeleportType1.WILDERNESS, 3348, 3647, 0, 79, true, 260, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1}),
	NEW_20(17031, 52122, "Elder Druids@red@(15)", "Elder Chaos Druids @red@(Level 15)", 0, 0, "", "", TeleportType1.WILDERNESS, 3235, 3635, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
    		}),
	
	NEW_21(17031, 52123, "Graveyard  @red@(15)", "Graveyards", 0, 0, "", "", TeleportType1.WILDERNESS, 3146, 3671, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_22(17031, 52124, "Yveltal Dragon  @red@(19)", "Yveltal Dragon", 0, 0, "", "", TeleportType1.WILDERNESS, 3303, 3669, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_23(17031, 52125, "Demonic Gorillas  @red@(20)", "Demonic Gorillas", 0, 0, "", "", TeleportType1.WILDERNESS, 3108, 3674, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_24(17031, 52126, "Tormented Demons @red@(24)", "Demonic Gorillas", 0, 0, "", "", TeleportType1.WILDERNESS, 3260, 3705, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_25(17031, 52127, "Crazy Archaeologist @red@(25)", "Crazy Archaeologist", 0, 0, "", "", TeleportType1.WILDERNESS, 2980, 3713, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_26(17031, 52128, "Venenatis @red@(28)", "Venenatis", 0, 0, "", "", TeleportType1.WILDERNESS, 3308, 3737, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_27(17031, 52129, "Lava Dragons @red@(31)", "Lava Dragons", 0, 0, "", "", TeleportType1.WILDERNESS, 3070, 3760, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_28(17031, 52130, "Chinchompa Hill @red@(34)", "Chinchompa Hill", 0, 0, "", "", TeleportType1.WILDERNESS, 3138, 3785, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_29(17031, 52131, "Vet'ion @red@(34)", "Vet'ion", 0, 0, "", "", TeleportType1.WILDERNESS, 3220, 3789, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_30(17031, 52132, "Revenant Caves @red@(40)", "Revenant Caves", 0, 0, "", "", TeleportType1.WILDERNESS, 3127, 3833, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_31(17031, 52133, "Chaos Fanatic @red@(42)", "Chaos Fanatic", 0, 0, "", "", TeleportType1.WILDERNESS, 2979, 3848, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_32(17031, 52134, "Callisto @red@(44)", "Callisto", 0, 0, "", "", TeleportType1.WILDERNESS, 3202, 3865, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	
	NEW_33(17031, 52135, "Ice Strykewyrms @red@(45)", "Callisto", 0, 0, "", "", TeleportType1.WILDERNESS, 2977, 3873, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_34(17031, 52136, "Demonic Ruins @red@(46)", "Demonic Ruins", 0, 0, "", "", TeleportType1.WILDERNESS, 3288, 3886, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_35(17031, 52137, "Chaos Elemental @red@(50)", "Chaos Elemental", 0, 0, "", "", TeleportType1.WILDERNESS, 3285, 3917, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_36(17031, 52138, "Tekton @red@(50)", "Tekton", 0, 0, "", "", TeleportType1.WILDERNESS, 3307, 3916, 0, 129, true, 6607, new int[] {  -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	//Cities
	NEW_39( 17031, 52119, "Varrock", "Varrock", 0, 0, "", "", TeleportType1.CITIES, 3213, 3424, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	NEW_40( 17031, 52120, "Falador", "Falador", 0, 0, "", "", TeleportType1.CITIES, 2965, 3378, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
    		}),
	NEW_41( 17031, 52121, "Lumbridge", "Lumbridge", 0, 0, "", "", TeleportType1.CITIES, 3222, 3218, 0, -1, false, -1, new int[] {-1, -1, -1, -1, -1, -1, -1, -1
    		}),
	NEW_42( 17031, 52122, "Al-kharid", "Al-kharid", 0, 0, "", "", TeleportType1.CITIES, 3276, 3167, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
    		}),
	
	NEW_43( 17031, 52123, "Karamja", "Karamja", 0, 0, "", "", TeleportType1.CITIES, 2947, 3147, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_44( 17031, 52124, "Fossil Island (east)", "Fossil Island (east)", 0, 0, "", "", TeleportType1.CITIES, 3817, 3808, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_45( 17031, 52125, "Fossil Island (west)", "Fossil Island (west)", 0, 0, "", "", TeleportType1.CITIES, 3735, 3803, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_46( 17031, 52126, "Land's End", "Land's End", 0, 0, "", "", TeleportType1.CITIES, 1504, 3423, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_47( 17031, 52127, "Draynor", "Draynor", 0, 0, "", "", TeleportType1.CITIES, 3093, 3248, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_48( 17031, 52128, "Ardougne", "Ardougne", 0, 0, "", "", TeleportType1.CITIES, 2661, 3306, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_49( 17031, 52129, "Taverly", "Taverly", 0, 0, "", "", TeleportType1.CITIES, 2934, 3450, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_50( 17031, 52130, "Yanille", "Yanille", 0, 0, "", "", TeleportType1.CITIES, 2606, 3092, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_51( 17031, 52131, "Catherby", "Catherby", 0, 0, "", "", TeleportType1.CITIES, 2827, 3437, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_52( 17031, 52132, "Nardah", "Nardah", 0, 0, "", "", TeleportType1.CITIES, 3420, 2916, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_53( 17031, 52133, "Pollvineach", "Pollvineach", 0, 0, "", "", TeleportType1.CITIES, 3357, 2967, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_54( 17031, 52134, "Canifis", "Canifis", 0, 0, "", "", TeleportType1.CITIES, 3494, 3483, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	NEW_55( 17031, 52135, "Camelot", "Camelot", 0, 0, "", "", TeleportType1.CITIES, 2756, 3477, 0,  -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	
	
	
	
	NEW_58( 17031, 52119, "Duel Arena", "", 0, 0, "", "", TeleportType1.MINIGAMES, 3366, 3266, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	NEW_60( 17031, 52120, "Clan Wars", "",  0, 0, "", "", TeleportType1.MINIGAMES, 3327, 4758, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	NEW_61( 17031, 52121, "Dicing", "", 0, 0, "", "", TeleportType1.MINIGAMES, 1690, 4250, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
    		}),
	NEW_62( 17031, 52122, "Barrows", "", 0, 0, "", "", TeleportType1.MINIGAMES, 3565, 3315, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1 
    		}),
	NEW_63( 17031, 52123, "Warrior's Guild", "", 0, 0, "", "", TeleportType1.MINIGAMES, 2851, 3548, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_64( 17031, 52124, "Recipe For Disaster", "", 0, 0, "", "", TeleportType1.MINIGAMES, 1900, 5346, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	NEW_65( 17031, 52125, "FightCave", "", 0, 0, "", "", TeleportType1.MINIGAMES, 2452, 5167, 0, -1, false, -1, new int[] { -1, -1, -1, -1, -1, -1, -1, -1
	}),
	;
	

	/** The name of the teleport. */
	private final String name;
	
	private final String fullname;

	/** The type of the teleport. */
	private final TeleportType1 type;

	/** The position of the teleport. */

	public final int buttonId;

	private int cblvl;

	private boolean wildy;

	private int npcId;

	private int index;

	private int clickingid;

	private int x;

	private int y;

	private int z;
	
	private int health;
	
	public int getHealth() {
		return health;
	}

	public int getTeamsize() {
		return teamsize;
	}

	public String getAttackstyles() {
		return attackstyles;
	}

	public String getDifficulty() {
		return difficulty;
	}

	private int teamsize;
	
	private String attackstyles;
	
	private String difficulty;
	
	private int item[];


	public int[] getItem() {
		return item;
	}

	public void setItem(int[] item) {
		this.item = item;
	}

	/** Creates a new <code>Teleport<code>. */
	TeleportData1( int buttonId, int clickingid, String name, String fullname, int health, int teamsize, String attackstyles,
			String difficulty, TeleportType1 type, int x, int y, int z,
			int cblvl, boolean wildy, int npcId,  int[] item) {
		//this.index = (index);
		this.buttonId = (buttonId);
		this.clickingid = (clickingid);
		this.name = (name);
		this.fullname = (fullname);
		this.health = (health);
		this.teamsize = (teamsize);
		this.attackstyles = (attackstyles);
		this.difficulty = (difficulty);
		this.type = (type);
		this.x = (x);
		this.y = (y);
		this.z = (z);
		this.cblvl = (cblvl);
		this.wildy = (wildy);
		this.npcId = (npcId);
		this.item = (item);
	}

	public String getName() {
		return name;
	}
	
	public String getFullName() {
		return fullname;
	}

	public TeleportType1 getType() {
		return type;
	}

	public int getCblvl() {
		return cblvl;
	}

	public int getClickingId() {
		return clickingid;
	}

	public boolean getWildy() {
		return wildy;
	}

	public int getButtonId() {
		return buttonId;
	}

	public int getIndex() {
		return index;
	}

	public int getZ() {
		return z;
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public int getNpcId() {
		return npcId;
	}

}
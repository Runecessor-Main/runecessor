package game.content.phantasye.skill.gathering.mining;

public enum Ore {
	
	ESSENCE(1, 18, 2, 7936, 7471),
    COPPER(1, 18, 3, 436, 7484, 7453),
    TIN(1, 18, 3, 438, 7485, 7486),
    CLAY(1, 10, 3, 434, 7454, 7487),
    BLURITE(12, 18, 5, 668, 10583, 2110, 10584),
    IRON(15, 35, 7, 440, 7455, 7488),
    SILVER(20, 40, 78, 442, 7457, 7490),
    COAL(30, 50, 38, 453, 7489, 7456),
    GOLD(40, 65, 98, 444, 7491, 7458),
    MITHRIL(55, 80, 155, 447, 7492, 7459),
    ADAMANT(70, 95, 315, 449, 7493, 7460),
    RUNITE(85, 125, 970, 451, 7494, 7461);

    private int levelRequirements, xp, respawnTime, itemId;
    private int[] oreId;

    private Ore(int levelRequirements, int xp, int respawnTime, int itemId, int...oreId) {
        this.levelRequirements = levelRequirements;
        this.xp = xp;
        this.respawnTime = respawnTime;
        this.itemId = itemId;
        this.oreId = oreId;
    }

    public int getLevelRequirements() {
        return levelRequirements;
    }

    public int getXp() {
        return xp;
    }

    public int getRespawnTime() {
        return respawnTime;
    }

    public int getItemId() {
        return itemId;
    }

    public int[] getOreId() {
        return oreId;
    }
}

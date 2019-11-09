package game.content.phantasye.skill.slayer;

import utility.Misc;

public enum SlayerUnlocks {

    BOSS_SLAYER(200,"The ability to receive Boss tasks."),
    SAFE_BOSS_INSTANCES(500, "Coming Soon"),
    REMOTE_TASKS(100, "The ability to remote contact your Slayer Master"),
    AUTO_SMASH_VIALS(200, "The ability to smash vials after finishing them."),
    RING_BLING(300, "The ability to craft Slayer Rings"),
    BIGGER_AND_BADDER(150, "The ability to fight Superior slayer monsters.");

    private final int cost;
    private final String description;

    private SlayerUnlocks(int cost, String description) {
        this.cost = cost;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return Misc.capitalize(name().toLowerCase().replaceAll("_", " ") + " ( " + cost + " Points.)");
    }
}

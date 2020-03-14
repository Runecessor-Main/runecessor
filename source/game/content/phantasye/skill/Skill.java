package game.content.phantasye.skill;

public enum Skill {

    ATTACK,
    DEFENSE,
    STRENGTH,
    HITPOINTS,
    RANGED,
    PRAYER,
    MAGIC,
    COOKING,
    WOODCUTTING,
    FLETCHING,
    FISHING,
    FIREMAKING,
    CRAFTING,
    SMITHING,
    MINING,
    HERBLORE,
    AGILITY,
    THIEVING,
    SLAYER,
    FARMING,
    RUNECRAFTING,
    HUNTER;

    public int getId() {
        return this.ordinal();
    }

}

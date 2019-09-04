package game.player.sets;

import game.content.skilling.Skill;

import java.util.Arrays;
import java.util.Optional;

/**
 * Enum containing items that give an xp bonus
 *
 * Name the value the item name
 *
 * skill should be the skill being boosted
 *
 * itemId the itemId of the equipment/item
 *
 * modifier a double value representing the xp bonus (2.0 = x2)
 */
public enum  XPBoostingItems {
	
	LUMBERJACK_HAT(Skill.WOODCUTTING,10941,1.0),
    LUMBERJACK_TOP(Skill.WOODCUTTING,10939,.8),
    LUMBERJACK_PANTS(Skill.WOODCUTTING,10940,.7),
    LUMBERJACK_SHOES(Skill.WOODCUTTING,10933,.5),
    PRYROMANCER_HOOD(Skill.FIREMAKING,20708,.8),
    PRYROMANCER_TOP(Skill.FIREMAKING,20704,.8),
    PRYROMANCER_PANTS(Skill.FIREMAKING,20706,.3),
    PRYROMANCER_BOOTS(Skill.FIREMAKING,20710,.3),
    FARMING_HAT(Skill.FARMING,13646,.8),
    FARMING_TOP(Skill.FARMING,13642,.8),
    FARMING_PANTS(Skill.FARMING,13640,.3),
    FARMING_SHOES(Skill.FARMING,13644,.3),
    FISHING_HAT(Skill.FISHING,13258,.8),
    FISHING_TOP(Skill.FISHING,13259,.8),
    FISHING_PANTS(Skill.FISHING,13260,.3),
    FISHING_SHOES(Skill.FISHING,13261,.3),
    MINING_HAT(Skill.MINING,12013,.5),
    MINING_TOP(Skill.MINING,12014,.5),
    MINING_PANTS(Skill.MINING,12015,.3),
    MINING_SHOES(Skill.MINING,12016,.3),
    ROGUE_MASK(Skill.THIEVING,5554,.5),
	ROGUE_TOP(Skill.THIEVING,5553,.5),
	ROGUE_PANTS(Skill.THIEVING,5555,.3),
	ROGUE_SHOES(Skill.THIEVING,5557,.3),
	CLUE_HUNTER_CAPE(Skill.HUNTER,19697,2.5),
	GENERAL_CAPE(Skill.THIEVING,22114,1.5),
	GENERAL_CAPE1(Skill.MINING,22114,2.5),
	GENERAL_CAPE2(Skill.SMITHING,22114,1.5),
	GENERAL_CAPE3(Skill.FISHING,22114,2.5),
	GENERAL_CAPE4(Skill.FLETCHING,22114,1.5),
	GENERAL_CAPE5(Skill.WOODCUTTING,22114,1.5),
	GENERAL_CAPE6(Skill.CRAFTING,22114,1.5),
	GENERAL_CAPE7(Skill.RUNECRAFTING,22114,1.5),
	GENERAL_CAPE8(Skill.HUNTER,22114,1.5),
	GENERAL_CAPE9(Skill.COOKING,22114,1.5),
	GENERAL_CAPE10(Skill.SLAYER,22114,1.5),
	GENERAL_CAPE11(Skill.HERBLORE,22114,1.5),
	GENERAL_CAPE12(Skill.AGILITY,22114,1.5),
	GENERAL_CAPE13(Skill.FARMING,22114,1.5);
	
	
    private final Skill skill;
    private final int itemId;
    private final double modifier;

    private XPBoostingItems(Skill skill, int itemId, double modifier) {
        this.skill = skill;
        this.itemId =itemId;
        this.modifier = modifier;
    }

    public double getModifier() {
        return modifier;
    }

    public int getItemId() {
        return itemId;
    }

    public Skill getSkill() {
        return skill;
    }

    public static Optional<XPBoostingItems> forId(int id) {
        return Arrays.stream(values()).filter(item->item.getItemId() == id).findAny();
    }
}

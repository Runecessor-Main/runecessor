package game.content.phantasye.item;

import game.content.phantasye.skill.gathering.mining.Pickaxe;
import game.content.skilling.Skill;
import game.player.Player;

public final class ItemFinder {

    public static final Pickaxe findBestPickaxe(Player player) {
        for (Pickaxe pickaxe : Pickaxe.values()) {
            if (player.hasItem(pickaxe.getPick(), 1)
                    || player.getWieldedWeapon() == pickaxe.getPick()) {
                if (pickaxe.getLevelRequirement() <= player.baseSkillLevel[Skill.MINING
                        .getId()]) {
                    return pickaxe;
                }
            }
        }
        return null;
    }
}

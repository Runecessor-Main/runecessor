package game.content.combat.effect;

import core.ServerConstants;
import game.content.combat.damage.EntityDamage;
import game.content.combat.damage.EntityDamageEffect;
import game.content.skilling.agility.AgilityAssistant;
import game.npc.Npc;
import game.player.Player;

public class BloodRapierEffect implements EntityDamageEffect {
    @Override
    public EntityDamage onCalculation(EntityDamage damage) {
        return damage;
    }

    @Override
    public void onApply(EntityDamage damage) {
        Player attacker = (Player) damage.getSender();
        Npc victim = (Npc) damage.getTarget();
        victim.gfx100(1176);
        int heal = (int) (damage.getDamage() * 0.25);
    }

    public static void applyForNpc(Player player, Npc npc, int damage) {
        npc.gfx100(1176);
        int heal = (int) (damage * 0.25) == 0 ? 1 : (int) (damage * 0.25);
        int restore = (int) (damage * 0.10) == 0 ? 1 : (int) (damage * 0.10);
        if (canHeal(player))
            heal(player,heal);
        if(canRestore(player))
            restore(player,restore);
    }

    private static boolean canHeal(Player player) {
        return player.getCurrentCombatSkillLevel(ServerConstants.HITPOINTS) < 120;
    }

    private static boolean canRestore(Player player) {
        return player.getCurrentCombatSkillLevel(ServerConstants.PRAYER) < 99;
    }

    private static void heal(Player player, int amount) {
        int totalHealed = player.currentCombatSkillLevel[ServerConstants.HITPOINTS] + amount;
        if (totalHealed > 120)
            player.currentCombatSkillLevel[ServerConstants.HITPOINTS] = 120;
        else
            player.currentCombatSkillLevel[ServerConstants.HITPOINTS] += amount;
        player.skillTabMainToUpdate.add(ServerConstants.HITPOINTS);
    }

    private static void restore(Player player, int amount) {
        int restored = player.currentCombatSkillLevel[ServerConstants.PRAYER] + amount;
        if (restored > 120)
            player.currentCombatSkillLevel[ServerConstants.PRAYER] = 99;
        else
            player.currentCombatSkillLevel[ServerConstants.PRAYER] += amount;
        player.skillTabMainToUpdate.add(ServerConstants.PRAYER);
    }
}

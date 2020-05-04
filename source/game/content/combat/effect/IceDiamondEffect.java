package game.content.combat.effect;

import game.content.combat.damage.EntityDamage;
import game.content.combat.damage.EntityDamageEffect;
import game.npc.Npc;
import game.player.Player;

public class IceDiamondEffect implements EntityDamageEffect {

    @Override
    public EntityDamage onCalculation(EntityDamage damage) {
        Npc victim = (Npc) damage.getTarget();
        Player attacker = (Player) damage.getSender();
        if(victim.isFrozen()) {
            damage.setDamage((int) (damage.getDamage() + damage.getDamage() * 0.75));
        }
        System.out.println(damage.getDamage());
        return damage;
    }

    @Override
    public void onApply(EntityDamage damage) {
        Npc victim = (Npc) damage.getTarget();
        Player attacker = (Player) damage.getSender();
        if(!victim.isFrozen()) {
            victim.gfx0(363);
            victim.setFrozenLength(10000);
            attacker.receiveMessage("Your attack freezes your enemy solid.");
        }
    }
}

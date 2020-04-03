package game.npc.impl.vampyre;

import core.Server;
import core.ServerConstants;
import game.content.combat.Combat;
import game.content.prayer.Prayer;
import game.entity.Entity;
import game.entity.EntityType;
import game.entity.combat_strategy.impl.NpcCombatStrategy;
import game.npc.Npc;
import game.npc.combat.Damage;
import game.npc.combat.DamageQueue;
import game.npc.data.NpcDefinition;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;
import utility.Misc;

import java.util.concurrent.ThreadLocalRandom;

public class VampyreCombatStrategy extends NpcCombatStrategy {

    private int currentAttack;
    private boolean inRange;

    /**
     * Determines the attack type of the entity, representing what style of combat is being used.
     *
     * @param attacker
     * @param defender
     * @return the attack type, or -1 if none can be found.
     */
    @Override
    public int calculateAttackType(Entity attacker, Entity defender) {
        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
            Npc npc = (Npc) attacker;

            Player player = (Player) defender;

            NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];

            if (definition != null) {
                if (player.distanceToPoint(npc.getX(), npc.getY()) <= definition.size + 1) {
                    if (ThreadLocalRandom.current().nextInt(0, 100) <= 80) {
                        return ServerConstants.MELEE_ICON;
                    }
                }
            }
        }
        if (ThreadLocalRandom.current().nextInt(0, 100) <= 40) {
            return ServerConstants.RANGED_ICON;
        }
        return ServerConstants.MAGIC_ICON;
    }

    /**
     * The custom damage that should be dealt, or -1 if the parent damage should be taken into consideration.
     *
     * @param attacker         the attacker dealing the damage.
     * @param defender         the defender taking the damage.
     * @param entityAttackType
     * @return the custom calculation of damage, or -1 if the parent damage should be used instead.
     */
    @Override
    public int calculateCustomDamage(Entity attacker, Entity defender, int entityAttackType) {
//        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
//            Npc npc = (Npc) attacker;
//
//            Player player = (Player) defender;
//
//            NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
//
//            if (definition != null) {
//                if (entityAttackType == ServerConstants.MAGIC_ICON) {
//                    int dmg = Misc.random(20) + 5;
//                    return dmg;
//                }
//            }
//        }
        return -1;
    }

    /**
     * References when {@link #isCustomAttack()} returns true, in which case we're responsible the
     * regular combat process.
     *
     * @param attacker the attacker.
     * @param defender the defender.
     */
    @Override
    public void onCustomAttack(Entity attacker, Entity defender) {
        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
            Npc npc = (Npc) attacker;

            Player player = (Player) defender;

            NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
            final Region meleeRegion =
                    new Region(new Location(npc.getX() - 1, npc.getY() - 1), new Location(npc.getX() + 1, npc.getY() + 1));
            if (definition != null) {
                this.setInRange(meleeRegion.contains(player.getLocation()));
                if (npc.attackType == ServerConstants.MELEE_ICON && this.isInRange()) {
                    int attackType = Misc.random(10);
                    this.setCurrentAttack(attackType);
                    switch (attackType) {
                        case 10:
                            DamageQueue.add(new Damage(player, npc, npc.attackType, 1, definition.maximumDamage, Misc.random(40)));
                            break;
                        default:
                            DamageQueue.add(new Damage(player, npc, npc.attackType, 1, definition.maximumDamage, -1));
                            break;
                    }
                } else if(npc.attackType == ServerConstants.RANGED_ICON) {
                    final Damage damage = new Damage(player, npc, npc.attackType, 3, 40, -1);
                    player.gfxDelay(83, 0, player.getHeight());
                    DamageQueue.add(damage);
                } else {
                    int dmg = Misc.random(20) + 5;
                    final Damage damage = new Damage(player, npc, npc.attackType, 3, 65, -1);
                    player.gfxDelay(1176, 0, player.getHeight());
                    int drained = (int) (player.currentCombatSkillLevel[ServerConstants.PRAYER] - dmg * 0.25);
                    if (drained > 0) {
                        player.currentCombatSkillLevel[ServerConstants.PRAYER] = drained;
                    } else {
                        player.currentCombatSkillLevel[ServerConstants.PRAYER] = 0;
                    }
                    player.skillTabMainToUpdate.add(ServerConstants.PRAYER);
                    this.applyForNpc(npc, dmg);
                    DamageQueue.add(damage);
                }
            }
        }
    }

    /**
     * Retrieves the custom emote for the attacking entity, or -1 if there is no custom emote.
     *
     * @param attacker the entity making the attack animation.
     * @return the attack emote, or -1 by default for no custom attack emote.
     */
    @Override
    public int getCustomAttackAnimation(Entity attacker) {
        if (attacker.getType() == EntityType.NPC) {
            Npc npc = (Npc) attacker;
            if (npc.attackType == ServerConstants.RANGED_ICON) {
                return 1978;
            }
            if (npc.attackType == ServerConstants.MELEE_ICON && this.isInRange()) {
                if (this.getCurrentAttack() == 10) {
                    return 408;
                }
                return 393;
            }
            return 1979;
        }
        return -1;
    }

    /**
     * Determines if we're going to handle the entire attack process our self.
     *
     * @return {@code true} if it's a custom attack, by default, false.
     */
    @Override
    public boolean isCustomAttack() {
        return true;
    }

    /**
     * Determines if a boss should perform a block operation.
     *
     * @return true by default.
     */
    @Override
    public boolean performsBlockAnimation() {
        return true;
    }

    public int getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(int currentAttack) {
        this.currentAttack = currentAttack;
    }

    public void applyForNpc(Npc npc, int damage) {
        int heal = (int) (damage * 0.25) == 0 ? 1 : (int) (damage * 0.25);
        if (canHeal(npc))
            heal(npc, heal);
    }

    private boolean canHeal(Npc npc) {
        return npc.getCurrentHitPoints() < npc.getDefinition().hitPoints;
    }

    private void heal(Npc npc, int amount) {
        int totalHealed = npc.getCurrentHitPoints() + amount;
        if (totalHealed > npc.getDefinition().hitPoints)
            npc.setCurrentHitPoints(npc.getDefinition().hitPoints);
        else
            npc.setCurrentHitPoints(npc.getCurrentHitPoints() + amount);
    }

    public boolean isInRange() {
        return inRange;
    }

    public void setInRange(boolean inRange) {
        this.inRange = inRange;
    }
}

package game.npc.impl.ice;

import core.ServerConstants;
import game.content.phantasye.RegionUtils;
import game.entity.Entity;
import game.entity.EntityType;
import game.entity.combat_strategy.impl.NpcCombatStrategy;
import game.npc.Npc;
import game.npc.NpcWalkToEvent;
import game.npc.combat.Damage;
import game.npc.combat.DamageQueue;
import game.npc.data.NpcDefinition;
import game.player.Player;
import game.position.Position;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.Timer;
import java.util.TimerTask;

public class IceLordCombatStrategy extends NpcCombatStrategy {

    private Location targetedLocation;

    @Override
    public void onCustomAttack(Entity attacker, Entity defender) {
        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
            final Npc npc = (Npc) attacker;
            final Player player = (Player) defender;
            final NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
            this.throwIcicle(npc,player);
        }
    }

    @Override
    public int calculateCustomDamage(Entity attacker, Entity defender, int entityAttackType) {
        return -1;
    }

    private void throwIcicle(Npc npc, Player target) {
        //sw loc = 2852 9920 2878 9965
        final Location targetedLocation = new Location(target.getX(), target.getHeight(), target.getY());
        int delay = 43;
        int targetDistance = target.getPA().distanceToPoint(npc.getX(), npc.getY());
        delay += targetDistance * 11;
        setTargetedLocation(targetedLocation);
        target.getPA().createPlayersProjectile(
                new Position(npc.getX() + 1, npc.getY() + 1, npc.getHeight()),
                target.getPosition()
                , 50, 40, 1017, 50, 45, target.getId(), 0, 0);
        target.gfxDelay(1017,delay,target.getHeight());
        DamageQueue.add(new Damage(target, npc, npc.attackType, 1, npc.getDefinition().maximumDamage, -1));
    }

    @Override
    public boolean isCustomAttack() {
        return true;
    }

    public void setTargetedLocation(Location targetedLocation) {
        this.targetedLocation = targetedLocation;
    }
}

package game.npc.impl.ice;

import core.ServerConstants;
import game.content.combat.Combat;
import game.content.miscellaneous.OverlayTimer;
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
import game.player.movement.Movement;
import game.position.Position;
import org.menaphos.model.math.Range;
import org.menaphos.model.math.impl.AdjustableInteger;
import org.menaphos.model.world.location.Location;
import org.menaphos.model.world.location.region.Region;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class IceQueenCombatStrategy extends NpcCombatStrategy {

    public Attack getCurrentAttack() {
        return currentAttack;
    }

    public void setCurrentAttack(Attack currentAttack) {
        this.currentAttack = currentAttack;
    }

    public Location getTargetedLocation() {
        return targetedLocation;
    }

    public void setTargetedLocation(Location targetedLocation) {
        this.targetedLocation = targetedLocation;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    private static enum Attack {
        HEAL(ServerConstants.NO_ICON), BASIC_MAGIC(ServerConstants.MAGIC_ICON), BASIC_RANGE(ServerConstants.RANGED_ICON), SPECIAL_MAGIC(ServerConstants.MAGIC_ICON), GROUND(ServerConstants.NO_ICON);

        private final int icon;

        private Attack(int icon) {
            this.icon = icon;
        }

        public int getIcon() {
            return icon;
        }
    }

    private Attack currentAttack;
    private final AdjustableInteger stage = new AdjustableInteger(0);
    private Location targetedLocation;
    private List<Location> icicleLocations = new ArrayList<>();
    private boolean frozen;

    @Override
    public int calculateAttackType(Entity attacker, Entity defender) {
        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
            Npc npc = (Npc) attacker;
            Player player = (Player) defender;
            NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
            if (npc.getCurrentHitPoints() <= npc.maximumHitPoints * .25 && stage.lessThan(1)) {
                this.setCurrentAttack(Attack.HEAL);
                stage.increment();
                return this.getCurrentAttack().getIcon();
            }
            final Range basicRangeRange = new Range(0, 30);
            final Range groundRange = new Range(30, 50);
            final Range specialMagicRange = new Range(50, 60);
            int roll = ThreadLocalRandom.current().nextInt(0, 100);
            if (Range.within(roll, basicRangeRange)) {
                this.setCurrentAttack(Attack.BASIC_RANGE);
            } else if (Range.within(roll, groundRange)) {
                this.setCurrentAttack(Attack.GROUND);
            } else if (Range.within(roll, specialMagicRange)) {
                this.setCurrentAttack(Attack.SPECIAL_MAGIC);
            } else {
                this.setCurrentAttack(Attack.BASIC_MAGIC);
            }
        }
        return this.getCurrentAttack().getIcon();
    }

    @Override
    public void onCustomAttack(Entity attacker, Entity defender) {
        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
            final Npc npc = (Npc) attacker;
            final Player player = (Player) defender;
            final NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
            final Region meleeRegion =
                    new Region(new Location(npc.getX() - 1, npc.getY() - 1), new Location(npc.getX() + 1, npc.getY() + 1));
            final Region walkable =
                    new Region(new Location(npc.getX() - 2, npc.getY() - 2), new Location(npc.getX() + 2, npc.getY() + 2));
            switch (currentAttack) {
                case HEAL:
                    this.setFrozen(true);
                    npc.forceChat("I must sleep!");
                    npc.setCurrentHitPoints(npc.maximumHitPoints);
                    final Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            setFrozen(false);
                        }

                    }, 1200);
                    break;
                case BASIC_MAGIC:
                    attacker.getLocalPlayers().forEach(p -> {
                        this.freezePlayer(npc,p);
                        if(meleeRegion.contains(p.getLocation())) {
                            Location walkTo = RegionUtils.getLocationInRegion(walkable);
                            npc.getEventHandler().addEvent(npc, new NpcWalkToEvent(20, new Position(walkTo.getXCoordinate(),walkTo.getYCoordinate(), walkTo.getZCoordinate()), 0),1);
                        }
                    });
                    break;
                case BASIC_RANGE:
                    attacker.getLocalPlayers().forEach(p -> this.throwIcicle(npc, p));
                    break;
                case SPECIAL_MAGIC:
                    npc.forceChat("Sleep now in the bitter cold... ");
                    attacker.getLocalPlayers().forEach(p -> {
                        this.freezePlayer(npc,p);
                        if(meleeRegion.contains(p.getLocation())) {
                            Location walkTo = RegionUtils.getLocationInRegion(walkable);
                            npc.getEventHandler().addEvent(npc, new NpcWalkToEvent(20, new Position(walkTo.getXCoordinate(),walkTo.getYCoordinate(), walkTo.getZCoordinate()), 0),1);
                        }
                    });

                    break;
                case GROUND:


                    attacker.getLocalPlayers().forEach(p -> {
                        final Region targetedRegion = new Region(new Location(p.getX() - 1,p.getHeight(), p.getY() - 1), new Location(p.getX() + 1,p.getHeight(), p.getY() + 1));
                        while (icicleLocations.size() < 5 * attacker.getLocalPlayers().size()) {
                            Location potentialLocation = RegionUtils.getLocationInRegion(targetedRegion);
                            if (!icicleLocations.contains(potentialLocation))
                                icicleLocations.add(potentialLocation);
                        }
                        icicleLocations.add(p.getCurrentLocation());
                        this.crashIcicle(npc, p);
                    });
                    break;
            }
        }
    }

    @Override
    public int calculateCustomDamage(Entity attacker, Entity defender, int entityAttackType) {
        if (attacker.getType() == EntityType.NPC && defender.getType() == EntityType.PLAYER) {
            Npc npc = (Npc) attacker;
            Player player = (Player) defender;
            NpcDefinition definition = NpcDefinition.getDefinitions()[npc.npcType];
            switch (currentAttack) {
                case HEAL:
                    return 0;
                case GROUND:
                    icicleLocations.clear();
                    return -1;
                default:
                    return -1;
            }

        }
        return -1;
    }

    @Override
    public int calculateCustomDamageTaken(Entity attacker, Entity defender, int damage, int attackType) {
        Player player = (Player) attacker;
        if (this.isFrozen()) {
            player.receiveMessage("Your attack fails to crack through the thick ice.");
            return 0;
        } else if (attackType == ServerConstants.RANGED_ICON) {
            player.receiveMessage("Your projectiles seem to have little effect against the Ice Queen's Icy Armor.");
            return damage/2;
        }
        return -1;
    }


    @Override
    public int getCustomAttackAnimation(Entity attacker) {
        Npc npc = (Npc) attacker;
        if (attacker.getType() == EntityType.NPC) {
            switch (currentAttack) {
                case HEAL:
                    npc.gfx0(539);
                    return 812;
                case BASIC_MAGIC:
                    return 1978;
                case BASIC_RANGE:
                    return 711;
                case SPECIAL_MAGIC:
                    return 1979;
                case GROUND:
                    return 811;
                default:
                    return -1;
            }
        }
        return -1;
    }

    private void freezePlayer(Npc npc,Player player) {
        int gfx = 0;
        long freeze = 0;
        int id = 0;
        int maxDmg = 0;
        int hitDelay = 1;
        int delay = 43;
        int targetDistance = player.getPA().distanceToPoint(npc.getX(), npc.getY());
        delay += targetDistance * 11;
        player.gfxDelay(gfx, delay, player.getHeight());
        if(currentAttack == Attack.BASIC_MAGIC) {
            gfx = 363;
            freeze = 8000;
            id = 12871;
            hitDelay = 2;
            maxDmg = npc.getDefinition().maximumDamage;
        } else if(currentAttack == Attack.SPECIAL_MAGIC) {
            gfx = 369;
            freeze = 20000;
            id = 12891;
            maxDmg = 90;
            hitDelay = 3;
        }


        if (player.canBeFrozen() && !player.isMagicSplash()) {
            Movement.stopMovement(player);
            player.setFrozenLength(freeze);
            player.frozenBy = -1;
            OverlayTimer.sendOverlayTimer(player, Combat.getFreezeSpriteId(id), (int) freeze / 1000);
            player.getPA().sendMessage("<col=ff0000>You have been frozen!");
            DamageQueue.add(new Damage(player, npc, npc.attackType, hitDelay, maxDmg, -1));
        } else {
            DamageQueue.add(new Damage(player, npc, npc.attackType, hitDelay, maxDmg/2, -1));
        }
    }

    private void throwIcicle(Npc npc, Player target) {
        final Location targetedLocation = new Location(target.getX(), target.getHeight(), target.getY());
        int delay = 43;
        int targetDistance = target.getPA().distanceToPoint(npc.getX(), npc.getY());
        delay += targetDistance * 11;
        setTargetedLocation(targetedLocation);
        target.getPA().createPlayersProjectile(
                new Position(npc.getX() + 1, npc.getY() + 1, npc.getHeight()),
                target.getPosition()
                , 50, 70, 1017, 50, 45, target.getId(), 0, 0);
        target.getPA().stillGfx(1017,target.getX(),target.getY(),target.getHeight(),delay);
        this.icicleHitsTarget(npc, target);
    }

    private void crashIcicle(Npc npc, Player target) {
        icicleLocations.forEach(icicle -> {
            target.getPA().stillGfx(1014, icicle.getXCoordinate(), icicle.getYCoordinate(), icicle.getZCoordinate(), 60);
        });
        this.icicleFallsOnTarget(npc, target);
    }

    private void icicleHitsTarget(Npc npc, Player target) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (new Location(target.getX(), target.getHeight(), target.getY()).matches(targetedLocation)) {
                    DamageQueue.add(new Damage(target, npc, npc.attackType, 1, npc.getDefinition().maximumDamage, -1));
                }
            }

        }, 800);
    }

    private void icicleFallsOnTarget(Npc npc, Player target) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(icicleLocations.stream()
                .anyMatch(location -> location.getYCoordinate() == target.getY() && location.getXCoordinate() == target.getX())) {
                    DamageQueue.add(new Damage(target, npc, npc.attackType, 1, -1, 25));
                }
            }

        }, 3200);
    }

    @Override
    public boolean isCustomAttack() {
        return true;
    }
}

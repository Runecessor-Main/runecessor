package game.content.phantasye.skill.artisan.runecrafting.impl;

import game.content.miscellaneous.Teleport;
import game.content.phantasye.skill.artisan.runecrafting.Runecrafting;
import game.content.skilling.Mining;
import game.content.skilling.Skill;
import game.content.skilling.Woodcutting;
import game.item.ItemAssistant;
import game.player.Player;
import org.menaphos.model.world.location.Location;

import java.util.Arrays;
import java.util.Random;

public class AbyssRunecrafting extends Runecrafting {

    public static enum Rift {
        AIR(25378, Altar.AIR),
        MIND(25379, Altar.MIND),
        WATER(25376, Altar.WATER),
        EARTH(24972, Altar.EARTH),
        FIRE(24971, Altar.FIRE),
        BODY(24973, Altar.BODY),
        COSMIC(24974, Altar.COSMIC),
        CHAOS(24976, Altar.CHAOS),
        NATURE(24975, Altar.NATURE),
        LAW(25034, Altar.LAW),
        DEATH(25035, Altar.DEATH);

        private final int riftId;
        private final Altar altar;

        private Rift(final int riftId, final Altar altar) {
            this.riftId = riftId;
            this.altar = altar;
        }

        public Altar getAltar() {
            return altar;
        }

        public int getRiftId() {
            return riftId;
        }
    }

    public AbyssRunecrafting(Player player) {
        super(player);
    }

    private boolean successCheck(Skill skill, String failureMessage) {
        final Random random = new Random();
        final int level = this.getPlayer().baseSkillLevel[skill.ordinal()];
        final int roll = random.nextInt(100) + 1;
        if ((roll + level) >= 100) {
            return true;
        } else {
            this.getPlayer().receiveMessage(failureMessage);
        }
        return false;
    }

    private boolean passObject(int objectId) {
        switch (objectId) {
            case 26190:
                if (this.getPlayer().hasItem(590, 1)) {
                    this.getPlayer().performAnimation(733);
                    return this.hasLevel(Skill.FIREMAKING, 30) && this.successCheck(Skill.FIREMAKING, "You burn yourself!");
                } else {
                    this.getPlayer().receiveMessage("You need a " + ItemAssistant.getItemName(590) + ".");
                }
                return false;
            case 26189:
                Woodcutting.performAnimation(this.getPlayer());
                return Woodcutting.hasCorrectHatchet(this.getPlayer()) && this.hasLevel(Skill.WOODCUTTING, 70) && this.successCheck(Skill.WOODCUTTING, "The tendrils snap back!");
            case 26191:
                this.getPlayer().performAnimation(401);
                return this.hasLevel(Skill.THIEVING, 70) && this.successCheck(Skill.THIEVING, "You trip and fall!");
            case 26192:
            case 26208:
                this.getPlayer().performAnimation(828);
                return true;
            case 26250:
                this.getPlayer().performAnimation(828);
                return this.hasLevel(Skill.AGILITY, 70) && this.successCheck(Skill.AGILITY, "You trip and fall!");
            case 26252:
                if (this.getPlayer().hasItem(590, 1)) {
                    this.getPlayer().performAnimation(733);
                    return this.hasLevel(Skill.FIREMAKING, 30) && this.successCheck(Skill.FIREMAKING, "You burn yourself!");
                } else {
                    this.getPlayer().receiveMessage("You need a " + ItemAssistant.getItemName(590) + ".");
                }
                return false;
            case 26251:
                this.getPlayer().performAnimation(401);
                return this.hasLevel(Skill.THIEVING, 30) && this.successCheck(Skill.THIEVING, "You trip and fall!");
            case 26253:
                Woodcutting.performAnimation(this.getPlayer());
                return Woodcutting.hasCorrectHatchet(this.getPlayer()) && this.hasLevel(Skill.WOODCUTTING, 30) && this.successCheck(Skill.WOODCUTTING, "The tendrils snap back!");
            case 26574:
                this.getPlayer().performAnimation(Mining.pickAxeAnimation(this.getPlayer()));
                return Mining.hasUseAblePickaxe(this.getPlayer()) && this.hasLevel(Skill.MINING, 70) && this.successCheck(Skill.MINING, "The rock shatters in your face!");
        }
        return false;
    }


    public boolean clickedObject(int objectId) {
        switch (objectId) {
            case 26190:
            case 26189:
            case 26191:
                return this.passObject(objectId) && this.getPlayer().moveTo(new Location(3027, 0,4831));
            case 26192:
                return this.getPlayer().moveTo(new Location(3031, 0,4842));
            case 26208:
                return this.getPlayer().moveTo(new Location(3042, 0,4844));
            case 26250:
                return this.passObject(objectId) && this.getPlayer().moveTo(new Location(3038, 0,4853));
            case 26252:
            case 26251:
                return this.passObject(objectId) && this.getPlayer().moveTo(new Location(3052,0, 4836));
            case 26253:
            case 26574:
                return this.passObject(objectId) && this.getPlayer().moveTo(new Location(3050,0, 4824));
        }
        return clickedRift(objectId);
    }

    public boolean clickedRift(int objectId) {
        if (Arrays.stream(Rift.values()).anyMatch(rift -> rift.getRiftId() == objectId))
            return teleportToAltar(Arrays.stream(Rift.values()).filter(rift -> rift.getRiftId() == objectId).findAny().get().getAltar());
        return false;
    }

    public static void teleportToAbyss(Player player) {
        Teleport.spellTeleport(player,3018,4821,0,true);
    }
}

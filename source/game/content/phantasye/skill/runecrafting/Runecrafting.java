package game.content.phantasye.skill.runecrafting;

import game.content.miscellaneous.Teleport;
import game.content.music.SoundSystem;
import game.content.skilling.Skill;
import game.content.skilling.Skilling;
import game.item.ItemAssistant;
import game.player.Player;
import org.menaphos.model.world.location.Location;
import utility.Misc;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Runecrafting {

    public static final int DEFAULT_PORTAL = 14841;
    public static final int RUNE_ESSENCE = 1436;
    public static final int PURE_ESSENCE = 7936;

    public static enum Altar {
        AIR(1438, 5527, new Location(2841, 4829), -1, 1, Rune.AIR, 14897),
        MIND(1448, 5529, new Location(2793, 4828), 14842, 2, Rune.MIND, 14898),
        WATER(1444, 5531, new Location(2726, 4832), 14843, 5, Rune.WATER, 14899),
        EARTH(1440, 5535, new Location(2655, 4830), 14844, 9, Rune.EARTH, 14900),
        FIRE(1442, 5537, new Location(2574, 4849), 14845, 14, Rune.FIRE, 14901),
        BODY(1446, 5533, new Location(1726, 3825), 14846, 20, Rune.BODY, 14902),
        COSMIC(1454, 5539, new Location(1838, 2, 5087), DEFAULT_PORTAL, 27, Rune.COSMIC, 14903),
        CHAOS(1452, 5543, new Location(2281, 4837), 14893, 35, Rune.CHAOS, 14906),
        ASTRAL(-1, 9106, new Location(2149, 3864), DEFAULT_PORTAL, 40, Rune.ASTRAL, 14911),
        NATURE(1462, 5541, new Location(2400, 4835), 14892, 44, Rune.NATURE, 14905),
        LAW(1458, 5545, new Location(2464, 4818), 14848, 54, Rune.LAW, 14904),
        DEATH(1456, 5547, new Location(2208, 4830), 14894, 65, Rune.DEATH, 14907),
        BLOOD(16006, -1, new Location(1726, 3825), DEFAULT_PORTAL, 77, Rune.BLOOD, 27978),
        SOUL(16005, -1, new Location(1820, 3861), DEFAULT_PORTAL, 90, Rune.SOUL, 27980),
        WRATH(-1, -1, new Location(-1, -1), DEFAULT_PORTAL, 95, Rune.WRATH, -1),
        OURANIA(-1, -1, new Location(3015, 5629), DEFAULT_PORTAL, 1, Rune.BLOOD, 29631);

        private final int talismanId;
        private final int tiaraId;
        private final Location entryLocation;
        private final int portalId;
        private final int levelRequired;
        private final Rune rune;
        private final int objectId;

        private Altar(final int talismanId, final int tiaraId, final Location entryLocation, final int portalId, final int levelRequired, Rune rune, int objectId) {
            this.talismanId = talismanId;
            this.tiaraId = tiaraId;
            this.entryLocation = entryLocation;
            this.portalId = portalId;
            this.levelRequired = levelRequired;
            this.rune = rune;
            this.objectId = objectId;
        }

        public Rune getRune() {
            return rune;
        }

        public int getLevelRequired() {
            return levelRequired;
        }

        public int getPortalId() {
            return portalId;
        }

        public int getTalismanId() {
            return talismanId;
        }

        public int getTiaraId() {
            return tiaraId;
        }

        public Location getEntryLocation() {
            return entryLocation;
        }

        public int getObjectId() {
            return objectId;
        }
    }

    public boolean rollForPet(Altar altar) {
        final Random random = new Random();
        int baseChance = 1795758;
        switch (altar) {
            case BLOOD:
                baseChance = 804984;
                break;
            case SOUL:
                baseChance = 782999;
                break;
            case OURANIA:
                baseChance = 1487213;
                break;
        }
        final int roll = random.nextInt(baseChance - (player.baseSkillLevel[Skill.RUNECRAFTING.ordinal()] * 25));
        return roll == 1;
    }

    private void givePet(Rune rune) {
        if (!player.addItemToInventory(getPetItemId(rune), 1)) {
            ItemAssistant.itemDropsOnFloorAndOnlyAppearsToOwner(getPetItemId(rune));
        }
    }

    private int getPetItemId(Rune rune) {
        int baseId = 20665;
        switch (rune) {
            case AIR:
                baseId = 20667;
                break;
            case MIND:
                baseId = 20669;
                break;
            case WATER:
                baseId = 20671;
                break;
            case EARTH:
                baseId = 20673;
                break;
            case FIRE:
                baseId = 20675;
                break;
            case BODY:
                baseId = 20677;
                break;
            case COSMIC:
                baseId = 20679;
                break;
            case CHAOS:
                baseId = 20681;
                break;
            case ASTRAL:
                baseId = 20683;
                break;
            case NATURE:
                baseId = 20685;
                break;
            case LAW:
                baseId = 20687;
                break;
            case DEATH:
                baseId = 20689;
                break;
            case BLOOD:
                baseId = 20691;
                break;
        }
        return baseId;
    }

    public boolean craftRuneOn(Altar altar) {
        final int runes = getEssenceTotal() * getMultiplier(altar.getRune());
        if (altar != Altar.OURANIA && hasEssenceFor(altar.getRune())) {
            final int xp = getEssenceTotal() * altar.getRune().getXp();
            ItemAssistant.deleteItemFromInventory(player, RUNE_ESSENCE, getEssenceTotal());
            ItemAssistant.deleteItemFromInventory(player, PURE_ESSENCE, getEssenceTotal());
            player.addItemToInventory(altar.getRune().getItemId(), runes);
            Skilling.addSkillExperience(player, xp, Skill.RUNECRAFTING.getId(), false);
            for (int i = 0; i < runes; i++) {
                if (rollForPet(altar)) {
                    givePet(altar.getRune());
                    break;
                }
            }
            this.doAnimation();
            player.receiveMessage("You have run out of essence!");
            return true;
        } else if (altar == Altar.OURANIA && hasEssenceFor(altar.getRune())) {

            ItemAssistant.deleteItemFromInventory(player, PURE_ESSENCE, getEssenceTotal());
            for (int i = 0; i < runes; i++) {
                Rune rune = getRuneForOuraniaAltar();
                if (rollForPet(altar)) {
                    givePet(rune);
                    break;
                }
                player.addItemToInventory(rune.getItemId(), getMultiplier(rune));
                Skilling.addSkillExperience(player, rune.getXp() * 2, Skill.RUNECRAFTING.getId(), false);
            }
            this.doAnimation();
            player.receiveMessage("You have run out of essence!");
        }
        return false;
    }

    private Rune getRuneForOuraniaAltar() {
        final List<Rune> runeList = Arrays.stream(Rune.values())
                .filter(rune -> rune != Rune.WRATH)
                .filter(rune -> rune != Rune.MUD)
                .filter(rune -> rune != Rune.STEAM)
                .filter(rune -> rune != Rune.LAVA)
                .filter(rune -> rune != Rune.DUST)
                .filter(rune -> rune != Rune.MIST)
                .filter(rune -> rune != Rune.SMOKE)
                .collect(Collectors.toList());
        final int levelModifier = Math.abs(player.baseSkillLevel[Skill.RUNECRAFTING.ordinal()] / 10) + 1;
        int index = new Random().nextInt(runeList.size());
        if(index + levelModifier >= runeList.size())
            return getRuneForOuraniaAltar();
        return runeList.get(index + new Random().nextInt(levelModifier));
    }

    private void doAnimation() {
        player.turnPlayerTo(player.getObjectX(), player.getObjectY());
        player.startAnimation(791);
        SoundSystem.sendSound(player, 481, 0);
        player.gfx100(186);
    }

    private int getEssenceTotal() {
        final int essenceTotal = ItemAssistant.getItemAmount(player, RUNE_ESSENCE) + ItemAssistant.getItemAmount(player, PURE_ESSENCE);
        return essenceTotal;
    }

    private int getMultiplier(Rune rune) {
        int multiplier = 1;
        if (rune.getLevelUnlocks().length > 0) {
            for (int i = 0; i < rune.getLevelUnlocks().length; i++) {
                if (!hasLevel(rune.getLevelUnlocks()[i])) {
                    multiplier = i + 1;
                } else if (i == rune.getLevelUnlocks().length - 1 && hasLevel(rune.getLevelUnlocks()[i])) {
                    multiplier = i + 1;
                }
            }
        }
        return multiplier;
    }

    private boolean hasEssenceFor(Rune rune) {
        if (!rune.isMembers()) {
            return player.hasItem(RUNE_ESSENCE, 1) || player.hasItem(PURE_ESSENCE, 1);
        } else {
            return player.hasItem(PURE_ESSENCE, 1);
        }
    }

    public boolean clickedAltar(int objectId) {
        return Arrays.stream(Altar.values()).anyMatch(altar -> altar.getObjectId() == objectId) && craftRuneOn(Arrays.stream(Altar.values()).filter(altar -> altar.getObjectId() == objectId).findAny().get());
    }

    public boolean teleportToAltar(Altar altar) {
        return Teleport.spellTeleport(player, altar.getEntryLocation().getXCoordinate(), altar.getEntryLocation().getZCoordinate(),
                altar.getEntryLocation().getYCoordinate(), true);
    }

    public boolean hasLevel(Skill skill, int level) {
        if (player.baseSkillLevel[skill.ordinal()] < level) {
            player.receiveMessage("You need at least " + level + " " + Misc.capitalize(skill.toString()) + ".");
            return false;
        }
        return true;
    }

    public boolean hasLevel(int level) {
        return hasLevel(Skill.RUNECRAFTING, level);
    }

    public Runecrafting(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    private final Player player;
}

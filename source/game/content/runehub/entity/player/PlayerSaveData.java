package game.content.runehub.entity.player;

import org.runehub.api.model.math.impl.AdjustableDouble;
import org.runehub.api.model.math.impl.AdjustableInteger;

import java.util.HashMap;
import java.util.Map;

public class PlayerSaveData {

    public long getPlayerId() {
        return playerId;
    }

    public long getJoinTimestamp() {
        return joinTimestamp;
    }

    public long getLoginTimestamp() {
        return loginTimestamp;
    }

    public long getLogoutTimestamp() {
        return logoutTimestamp;
    }

    public void setJoinTimestamp(long joinTimestamp) {
        this.joinTimestamp = joinTimestamp;
    }

    public void setLoginTimestamp(long loginTimestamp) {
        this.loginTimestamp = loginTimestamp;
    }

    public void setLogoutTimestamp(long logoutTimestamp) {
        this.logoutTimestamp = logoutTimestamp;
    }

    public AdjustableDouble getMagicFind() {
        return magicFind;
    }

    public Map<Integer, Integer> getSkillAnimationOverrideMap() {
        return skillAnimationOverrideMap;
    }

    public void setLastHomeTeleportTimestamp(long lastHomeTeleportTimestamp) {
        this.lastHomeTeleportTimestamp = lastHomeTeleportTimestamp;
    }

    public long getLastHomeTeleportTimestamp() {
        return lastHomeTeleportTimestamp;
    }

    public AdjustableInteger getInstantTeleportCharges() {
        return instantTeleportCharges;
    }

    public boolean isHomeUnlocked() {
        return homeUnlocked;
    }

    public int getAltarSpace() {
        return altarSpace;
    }

    public int getCrystalBallSpace() {
        return crystalBallSpace;
    }

    public int getPoolSpace() {
        return poolSpace;
    }

    public int getHeraldrySpace() {
        return heraldrySpace;
    }

    public int getJewelleryBoxSpace() {
        return jewelleryBoxSpace;
    }

    public int getLecternSpace() {
        return lecternSpace;
    }

    public int getPortalSpace() {
        return portalSpace;
    }

    public int getSpellSpace() {
        return spellSpace;
    }

    public int getTelescopeSpace() {
        return telescopeSpace;
    }

    public void setAltarSpace(int altarSpace) {
        this.altarSpace = altarSpace;
    }

    public void setCrystalBallSpace(int crystalBallSpace) {
        this.crystalBallSpace = crystalBallSpace;
    }

    public void setHeraldrySpace(int heraldrySpace) {
        this.heraldrySpace = heraldrySpace;
    }

    public void setHomeUnlocked(boolean homeUnlocked) {
        this.homeUnlocked = homeUnlocked;
    }

    public void setJewelleryBoxSpace(int jewelleryBoxSpace) {
        this.jewelleryBoxSpace = jewelleryBoxSpace;
    }

    public void setLecternSpace(int lecternSpace) {
        this.lecternSpace = lecternSpace;
    }

    public void setPoolSpace(int poolSpace) {
        this.poolSpace = poolSpace;
    }

    public void setPortalSpace(int portalSpace) {
        this.portalSpace = portalSpace;
    }

    public void setSpellSpace(int spellSpace) {
        this.spellSpace = spellSpace;
    }

    public void setTelescopeSpace(int telescopeSpace) {
        this.telescopeSpace = telescopeSpace;
    }

    public int getExchangeSlots() {
        return exchangeSlots;
    }

    public void setExchangeSlots(int exchangeSlots) {
        this.exchangeSlots = exchangeSlots;
    }

    public boolean isInitiativePackageClaimed() {
        return initiativePackageClaimed;
    }

    public void setInitiativePackageClaimed(boolean initiativePackageClaimed) {
        this.initiativePackageClaimed = initiativePackageClaimed;
    }

    public PlayerSaveData(long playerId) {
        this.playerId = playerId;
        this.magicFind = new AdjustableDouble(0.0D);
        this.skillAnimationOverrideMap = new HashMap<>();
        this.instantTeleportCharges = new AdjustableInteger(3);
        this.heraldrySpace = 15450;
        this.crystalBallSpace = 15422;
        this.jewelleryBoxSpace = 29142;
        this.poolSpace = 29122;
        this.telescopeSpace = 15424;
        this.lecternSpace = 15420;
        this.altarSpace = 15270;
        this.portalSpace = 15406;
        this.spellSpace = 29140;
        this.exchangeSlots = 3;
    }

    private final long playerId;
    private final AdjustableDouble magicFind;
    private final AdjustableInteger instantTeleportCharges;
    private final Map<Integer,Integer> skillAnimationOverrideMap;
    private long joinTimestamp;
    private long logoutTimestamp;
    private long loginTimestamp;
    private long lastHomeTeleportTimestamp;
    private boolean homeUnlocked,initiativePackageClaimed;
    private int heraldrySpace,crystalBallSpace,jewelleryBoxSpace,poolSpace,telescopeSpace,lecternSpace,altarSpace,portalSpace,spellSpace,exchangeSlots;

}

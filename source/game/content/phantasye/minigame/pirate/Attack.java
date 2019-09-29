package game.content.phantasye.minigame.pirate;

import game.entity.Entity;
import game.npc.Npc;
import game.player.Player;

public abstract class Attack {

    private final String voiceLine;
    private final int anim;
    private final int gfx;
    private final int hitDelay;
    private final int maxHit;
    private final int minHit;
    private final int style;
    private final int cooldownSeconds;

    public Attack(String voiceLine, int anim, int gfx, int hitDelay, int maxHit, int minHit,int style, int cooldownSeconds) {
        this.voiceLine = voiceLine;
        this.anim = anim;
        this.gfx = gfx;
        this.hitDelay = hitDelay;
        this.maxHit = maxHit;
        this.minHit = minHit;
        this.style = style;
        this.cooldownSeconds = cooldownSeconds;
    }

    public abstract void attack(Player target, Npc attacker);

    public int getCooldownSeconds() {
        return cooldownSeconds;
    }

    public int getAnim() {
        return anim;
    }

    public int getGfx() {
        return gfx;
    }

    public int getHitDelay() {
        return hitDelay;
    }

    public int getMaxHit() {
        return maxHit;
    }

    public int getMinHit() {
        return minHit;
    }

    public int getStyle() {
        return style;
    }

    public String getVoiceLine() {
        return voiceLine;
    }
}

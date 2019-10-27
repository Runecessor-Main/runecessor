package game.content.phantasye.skill.slayer;

import utility.Misc;

public enum SlayerUnlocks {

    BOSS_SLAYER,SAFE_BOSS_INSTANCES,REMOTE_TASKS;

    @Override
    public String toString() {
        return Misc.capitalize(name().toLowerCase().replaceAll("_",  " "));
    }
}

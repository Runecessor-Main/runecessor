package game.content.phantasye.skill;

import game.player.Player;

public final class SkillContext {

    private final Skill skill;
    private final Player player;

    public SkillContext(Skill skill, Player player) {
        this.skill = skill;
        this.player = player;
    }

    public Skill getSkill() {
        return skill;
    }

    public Player getPlayer() {
        return player;
    }
}

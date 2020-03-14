package game.content.phantasye.skill.chain.impl;

import game.content.phantasye.skill.Skill;
import game.content.phantasye.skill.chain.SkillChain;
import game.player.Player;

public abstract class AbstractPrerequisiteChain implements SkillChain {

    private final Player player;
    private final Skill skill;
    private SkillChain nextChain;

    public AbstractPrerequisiteChain(Player player, Skill skill) {
        this.player = player;
        this.skill = skill;
    }

    public boolean execute() {
        return nextChain.prerequisites();
    }

    public Skill getSkill() {
        return skill;
    }

    public SkillChain getNextChain() {
        return nextChain;
    }

    @Override
    public void setNextChain(SkillChain nextChain) {
        this.nextChain = nextChain;
    }

    public Player getPlayer() {
        return player;
    }
}

package game.content.phantasye.skill.chain.impl;

import game.content.phantasye.skill.Skill;
import game.player.Player;

public class LevelPrerequisiteChain extends AbstractPrerequisiteChain  {

    private final int levelRequirement;

    public LevelPrerequisiteChain(Player player, Skill skill, int levelRequirement) {
        super(player,skill);
        this.levelRequirement = levelRequirement;
    }


    @Override
    public boolean prerequisites() {
        return this.getPlayer().baseSkillLevel[this.getSkill().getId()] >= levelRequirement && this.getNextChain().prerequisites();
    }
}

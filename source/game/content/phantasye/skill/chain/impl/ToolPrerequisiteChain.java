package game.content.phantasye.skill.chain.impl;

import game.content.phantasye.actions.skill.gathering.AbstractGatheringSkillAction;
import game.content.phantasye.skill.Skill;
import game.player.Player;

import java.util.List;

public class ToolPrerequisiteChain extends AbstractPrerequisiteChain {

    private final List<Integer> toolList;

    public ToolPrerequisiteChain(Player player, Skill skill, List<Integer> toolList) {
        super(player, skill);
        this.toolList = toolList;
    }

    @Override
    public boolean prerequisites() {
        return toolList.stream().anyMatch(tool -> this.getPlayer().hasItem(tool,1) || this.getPlayer().hasItemEquipped(tool)) && this.getNextChain().prerequisites();
    }
}

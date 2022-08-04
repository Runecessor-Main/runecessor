package game.content.phantasye.skill.chain.impl;

import game.content.phantasye.actions.skill.gathering.AbstractGatheringSkillAction;
import game.content.phantasye.skill.Skill;
import game.content.phantasye.skill.chain.SkillChain;
import game.player.Player;

public class PrerequisiteChain implements SkillChain {

    private final SkillChain prerequisiteChain;

    public PrerequisiteChain(Player player, Skill skill, AbstractGatheringSkillAction<?> action) {
        this.prerequisiteChain = new LevelPrerequisiteChain(player,skill,action.getLevelRequired());

        final SkillChain toolprerequisite = new ToolPrerequisiteChain(player,skill,action.getToolList());
        final SkillChain inventoryPreReq = new InventoryPrerequisiteChain(player,skill);

        prerequisiteChain.setNextChain(toolprerequisite);
        toolprerequisite.setNextChain(inventoryPreReq);
        inventoryPreReq.setNextChain(this);

    }

    @Override
    public void setNextChain(SkillChain chain) {

    }

    @Override
    public boolean prerequisites() {
        return true;
    }
}

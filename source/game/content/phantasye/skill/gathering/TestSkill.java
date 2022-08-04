package game.content.phantasye.skill.gathering;

import game.content.phantasye.actions.skill.gathering.AbstractGatheringSkillAction;
import game.content.phantasye.actions.skill.gathering.impl.MineRockAction;
import game.content.phantasye.skill.SkillContext;

public class TestSkill<T extends MineRockAction> extends GatheringSkillBase<T> {

    public TestSkill(SkillContext context) {
        super(context);
    }

    @Override
    public boolean perform(MineRockAction action) {
        action.getActor().turnPlayerTo(action.getNode().getLocation());
        if (hasTool(action)) {
            if(this.getPrerequisiteChain().execute()) {
//                miningTimer = getTimer(oreId, getPick(),
//                        player.baseSkillLevel[Skill.MINING.getId()]);
//                player.startAnimation(anim);
//                this.startMiningEvent(x, y, oreId);
                return true;
            } else {

            }
        } else {
            action.getActor().receiveMessage("You do not have a pickaxe.");
        }
        return false;
    }

    @Override
    protected boolean hasTool(AbstractGatheringSkillAction action) {
        return false;
    }

}

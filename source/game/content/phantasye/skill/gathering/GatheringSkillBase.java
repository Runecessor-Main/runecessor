package game.content.phantasye.skill.gathering;

import game.content.phantasye.actions.skill.gathering.AbstractGatheringSkillAction;
import game.content.phantasye.actions.skill.gathering.impl.GatherNodeAction;
import game.content.phantasye.skill.AbstractSkillBase;
import game.content.phantasye.skill.SkillContext;

public abstract class GatheringSkillBase<G extends AbstractGatheringSkillAction> extends AbstractSkillBase<G> {

    protected GatheringSkillBase(SkillContext context) {
        super(context);
    }

    protected abstract boolean hasTool(AbstractGatheringSkillAction action);
}

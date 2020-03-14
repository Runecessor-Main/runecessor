package game.content.phantasye.skill;

import game.content.phantasye.actions.skill.AbstractSkillAction;
import game.content.phantasye.skill.chain.impl.AbstractPrerequisiteChain;

public abstract class AbstractSkillBase<A extends AbstractSkillAction> {

    private boolean performing;

    private final SkillContext context;
    private AbstractPrerequisiteChain prerequisiteChain;

    protected AbstractSkillBase(SkillContext context) {
        this.context = context;
    }

    public abstract boolean perform(A action);

    public AbstractPrerequisiteChain getPrerequisiteChain() {
        return prerequisiteChain;
    }

    public void setPrerequisiteChain(AbstractPrerequisiteChain prerequisiteChain) {
        this.prerequisiteChain = prerequisiteChain;
    }

    public boolean isPerforming() {
        return performing;
    }

    public SkillContext getContext() {
        return context;
    }

    public void setPerforming(boolean performing) {
        this.performing = performing;
    }
}

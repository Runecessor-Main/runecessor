package game.content.phantasye.skill.chain;

public interface SkillChain {

    void setNextChain(SkillChain chain);

    boolean prerequisites();
}

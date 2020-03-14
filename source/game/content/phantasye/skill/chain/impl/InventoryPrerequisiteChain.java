package game.content.phantasye.skill.chain.impl;

import game.content.phantasye.skill.Skill;
import game.item.ItemAssistant;
import game.player.Player;

public class InventoryPrerequisiteChain extends AbstractPrerequisiteChain {

    public InventoryPrerequisiteChain(Player player, Skill skill) {
        super(player, skill);
    }

    @Override
    public boolean prerequisites() {
        return ItemAssistant.getFreeInventorySlots(this.getPlayer()) < 1 && this.getNextChain().prerequisites();
    }
}

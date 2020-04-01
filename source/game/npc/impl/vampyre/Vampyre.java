package game.npc.impl.vampyre;

import core.GameType;
import game.entity.combat_strategy.EntityCombatStrategy;
import game.npc.CustomNpcComponent;
import game.npc.Npc;
import game.npc.impl.dragon.DragonCombatStrategy;
import game.npc.impl.dragon.DragonNpc;
import game.type.GameTypeIdentity;

@CustomNpcComponent(identities = @GameTypeIdentity(type= GameType.OSRS, identity = { 4487,4486 }))
public class Vampyre extends Npc {
    private final EntityCombatStrategy combatStrategy = new VampyreCombatStrategy();

    public Vampyre(int npcId, int npcType) {
        super(npcId, npcType);
    }

    /**
     * Creates a new instance of this npc with the given index.
     *
     * @param index the new index of this npc.
     * @return the new instance.
     */
    @Override
    public Npc copy(int index) {
        return new Vampyre(index, npcType);
    }

    @Override
    public EntityCombatStrategy getCombatStrategyOrNull() {
        return combatStrategy;
    }
}

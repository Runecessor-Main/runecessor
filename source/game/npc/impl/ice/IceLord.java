package game.npc.impl.ice;

import core.GameType;
import game.entity.Entity;
import game.entity.combat_strategy.EntityCombatStrategy;
import game.npc.CustomNpcComponent;
import game.npc.Npc;
import game.npc.NpcHandler;
import game.player.Player;
import game.player.event.CycleEventAdapter;
import game.player.event.CycleEventContainer;
import game.type.GameTypeIdentity;

@CustomNpcComponent(identities = @GameTypeIdentity(type= GameType.OSRS, identity = { 852 }))
public class IceLord extends Npc {
    private final EntityCombatStrategy combatStrategy = new IceLordCombatStrategy();

    public IceLord(int npcId, int npcType) {
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
        return new IceLord(index, npcType);
    }

    @Override
    public EntityCombatStrategy getCombatStrategyOrNull() {
        return combatStrategy;
    }
}

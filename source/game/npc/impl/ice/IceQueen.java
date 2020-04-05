package game.npc.impl.ice;

import core.GameType;
import game.entity.Entity;
import game.entity.MovementState;
import game.entity.combat_strategy.EntityCombatStrategy;
import game.npc.CustomNpcComponent;
import game.npc.Npc;
import game.npc.NpcHandler;
import game.npc.impl.vampyre.VampyreCombatStrategy;
import game.npc.impl.zulrah.Zulrah;
import game.npc.impl.zulrah.ZulrahLocation;
import game.npc.player_pet.NpcModelSection;
import game.player.Player;
import game.player.event.CycleEventAdapter;
import game.player.event.CycleEventContainer;
import game.type.GameTypeIdentity;

import java.util.Objects;

@CustomNpcComponent(identities = @GameTypeIdentity(type= GameType.OSRS, identity = { 4922 }))
public class IceQueen extends Npc {
    private final EntityCombatStrategy combatStrategy = new IceQueenCombatStrategy();

    public IceQueen(int npcId, int npcType) {
        super(npcId, npcType);
    }

    public void spawn(Player attacker) {

    }

    public static void createInstance(Player player) {
        IceQueen result = (IceQueen) NpcHandler.spawnNpc(player, 4922, player.getX() + 1,
                player.getY(), player.getHeight(), false, false);
        player.getEventHandler().addEvent(player, new CycleEventAdapter<Entity>() {
            @Override
            public void execute(CycleEventContainer<Entity> container) {
                container.stop();
//                player.getPA().sendItemToNpc(result.npcIndex, 4214, NpcModelSection.WEAPON.getSlot());
            }
        }, 2);
    }

    /**
     * Creates a new instance of this npc with the given index.
     *
     * @param index the new index of this npc.
     * @return the new instance.
     */
    @Override
    public Npc copy(int index) {
        return new IceQueen(index, npcType);
    }

    @Override
    public EntityCombatStrategy getCombatStrategyOrNull() {
        return combatStrategy;
    }
}
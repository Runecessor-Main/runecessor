package game.content.phantasye;

import game.content.phantasye.skill.support.slayer.SlayerSkill;
import game.player.Player;
import org.menaphos.action.impl.item.BaseItemOnItemAction;

public final class ItemOnItemActionManager {

    public static boolean performAction(Player player, int sourceId, int targetId) {
        return
                SlayerSkill.enchantItem(player, sourceId, targetId)
                        || SlayerSkill.dyeSlayerHelm(player, sourceId, targetId)
                        || player.getActionInvoker().perform(new BaseItemOnItemAction(player, 22315, 6570, 21295))
                        || player.getActionInvoker().perform(new BaseItemOnItemAction(player, 22315, 6570, 21295));
    }
}

package game.content.runehub.entity.item;

import game.player.Player;

public class ItemReactionProcessor {

    public void process(ItemInteractionContext context, ItemInteraction interaction, ItemReaction reaction, Player player) {
//        if (reaction.getUuid() == -4605300025780253777L) {
//        if (reaction instanceof ArtisanSkillItemReaction) {
//            final ArtisanSkillItemReaction artisanSkillItemReaction = (ArtisanSkillItemReaction) reaction;
//            if (artisanSkillItemReaction.getActionId() == 1) {
//                player.getPA().sendFrame164(1743);
//                player.getPA().sendFrame246(13716, 190, ((ArtisanSkillItemReaction) reaction).getProductItemId());
//                player.getPA().sendFrame126("\\n\\n\\n\\n\\n" + ItemAssistant.getItemName(((ArtisanSkillItemReaction) reaction).getProductItemId()) + "", 13717);
//
//                player.getSkillController().getCooking().train(new CookOnNodeAction(
//                        player,
//                        (CookingItemReaction) reaction,
//                        context
//                ));
//            } else if (artisanSkillItemReaction.getActionId() == 2) {
//                player.getSkillController().getHerblore().train(new MixUnfinishedPotionAction(
//                        player,
//                        (HerbloreItemReaction) reaction,
//                        context
//                ));
//            } else if (artisanSkillItemReaction.getActionId() == 3) {
//                player.getSkillController().getHerblore().train(new CrushItemAction(
//                        player,
//                        (HerbloreItemReaction) reaction,
//                        context
//                ));
//            } else if (artisanSkillItemReaction.getActionId() == 4) { //smelting
//                player.getSkillController().getHerblore().train(new CrushItemAction(
//                        player,
//                        (HerbloreItemReaction) reaction,
//                        context
//                ));
//            }
//        } else {
//            System.out.println(reaction.getClass());
//        }

    }
}

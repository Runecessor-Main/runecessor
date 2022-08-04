package game.content.runehub.entity.item;

public class ItemReactionFactory {

    public static ItemReaction getItemReaction(int reactionKey, long reactionUuid) {
        switch (reactionKey) {
//            case 7:
//                return CookingItemReactionLoader.getInstance().read(reactionUuid);
//            case 13:
//                return SmeltingItemReactionLoader.getInstance().read(reactionUuid);
//            case 15:
//                return HerbloreItemReactionLoader.getInstance().read(reactionUuid);
            default:
                throw new NullPointerException("No Reactions Available: " + reactionKey);
        }
    }
}

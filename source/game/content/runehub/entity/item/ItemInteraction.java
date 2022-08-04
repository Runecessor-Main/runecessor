package game.content.runehub.entity.item;

import org.runehub.api.io.data.QueryParameter;
import org.runehub.api.io.data.SqlDataType;
import org.runehub.api.io.data.StoredObject;
import org.runehub.api.io.data.StoredValue;

@StoredObject(tableName = "item_interactions")
public class ItemInteraction {

    public static final int OBJECT_INTERACTION = 1;
    public static final int ITEM_INTERACTION = 2;
    public static final int NPC_INTERACTION = 3;

    public int getUsedId() {
        return usedId;
    }

    public int getUsedOnId() {
        return usedOnId;
    }

    public long getReactionUuid() {
        return reactionUuid;
    }

    public long getUuid() {
        return uuid;
    }

    public int getReactionKey() {
        return reactionKey;
    }

    public int getInteractionTypeId() {
        return interactionTypeId;
    }

    public ItemInteraction(long uuid, int usedId, int usedOnId, int reactionKey, long reactionUuid, int interactionTypeId) {
        this.uuid = uuid;
        this.usedId = usedId;
        this.usedOnId = usedOnId;
        this.reactionKey = reactionKey;
        this.reactionUuid = reactionUuid;
        this.interactionTypeId = interactionTypeId;
    }

    @StoredValue(type = SqlDataType.BIGINT, parameter = QueryParameter.PRIMARY_KEY, id = true)
    private final long uuid;
    @StoredValue(type = SqlDataType.INTEGER)
    private final int usedId;
    @StoredValue(type = SqlDataType.INTEGER)
    private final int usedOnId;
    @StoredValue(type = SqlDataType.INTEGER)
    private final int reactionKey;
    @StoredValue(type = SqlDataType.BIGINT)
    private final long reactionUuid;
    @StoredValue(type = SqlDataType.INTEGER)
    private final int interactionTypeId;
}

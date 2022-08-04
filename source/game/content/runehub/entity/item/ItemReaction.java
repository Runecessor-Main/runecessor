package game.content.runehub.entity.item;

import org.runehub.api.io.data.QueryParameter;
import org.runehub.api.io.data.SqlDataType;
import org.runehub.api.io.data.StoredObject;
import org.runehub.api.io.data.StoredValue;

@StoredObject(tableName = "item_reactions")
public class ItemReaction {

    public long getUuid() {
        return uuid;
    }

    public ItemReaction(long uuid) {
        this.uuid = uuid;
    }

    @StoredValue(type = SqlDataType.BIGINT, parameter = QueryParameter.PRIMARY_KEY, id = true)
    private final long uuid;

}

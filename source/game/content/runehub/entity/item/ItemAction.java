package game.content.runehub.entity.item;

import org.runehub.api.io.data.QueryParameter;
import org.runehub.api.io.data.SqlDataType;
import org.runehub.api.io.data.StoredObject;
import org.runehub.api.io.data.StoredValue;

@StoredObject(tableName = "item_actions")
public class ItemAction {

    public int getItemId() {
        return itemId;
    }

    public int getActionId() {
        return actionId;
    }

    public ItemAction(int itemId, int actionId) {
    this.itemId = itemId;
    this.actionId = actionId;
    }

    @StoredValue(type = SqlDataType.INTEGER, parameter = QueryParameter.PRIMARY_KEY, id = true)
    private final int itemId;
    @StoredValue(type = SqlDataType.INTEGER)
    private final int actionId;
}

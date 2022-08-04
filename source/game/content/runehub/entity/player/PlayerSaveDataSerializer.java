package game.content.runehub.entity.player;


import org.rhd.api.io.json.JsonSerializer;

public class PlayerSaveDataSerializer extends JsonSerializer<PlayerSaveData> {

    public PlayerSaveDataSerializer() {
        super(PlayerSaveData.class);
    }
}

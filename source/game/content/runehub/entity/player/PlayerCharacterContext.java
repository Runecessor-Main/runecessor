package game.content.runehub.entity.player;


import org.rhd.api.entity.EntityContext;

public class PlayerCharacterContext extends EntityContext {

    private PlayerSaveData playerSaveData;

    public PlayerSaveData getPlayerSaveData() {
        return playerSaveData;
    }

    public void setPlayerSaveData(String json) {
        this.playerSaveData = new PlayerSaveDataSerializer().deserialize(json);
    }

    private PlayerCharacterContext(PlayerCharacterContextBuilder builder) {
        super(builder.id);
        this.playerSaveData = builder.playerSaveData;
    }

    @Override
    public Long getId() {
        return super.getId().longValue();
    }

    public static class PlayerCharacterContextBuilder {

        private final Long id;
        private PlayerSaveData playerSaveData;

        public PlayerCharacterContextBuilder(Long id) {
            this.id = id;
            this.playerSaveData = new PlayerSaveData(id);
        }

        public PlayerCharacterContextBuilder withPlayerSaveData(String json) {
            this.playerSaveData = new PlayerSaveDataSerializer().deserialize(json);
            return this;
        }

        public PlayerCharacterContext build() {
            return new PlayerCharacterContext(this);
        }
    }
}

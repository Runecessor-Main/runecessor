package game.content.phantasye.minigame.instance;

public final class InstanceKey {

    private final int instance;
    private final int id;

    public InstanceKey(int instance, Instancable instancable) {
        this.id = instancable.getId();
        this.instance = instance;
    }

    public int getId() {
        return id;
    }

    public int getInstance() {
        return instance;
    }

    public static InstanceKey forInstance(Instancable instancable) {
        return new InstanceKey(instancable.startingInstance(),instancable);
    }
}

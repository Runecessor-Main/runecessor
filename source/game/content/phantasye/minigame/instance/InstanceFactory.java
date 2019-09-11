package game.content.phantasye.minigame.instance;

import java.util.*;

public class InstanceFactory {

    private static final List<InstanceKey> instanceKeys = new ArrayList<>();

    public static InstanceKey getKeyForInstance(Instancable instancable) {
        if(!instanceKeys.isEmpty()) {
            if(instanceKeys.stream().anyMatch(instanceKey -> instanceKey.getId() == instancable.getId())) {
                OptionalInt currentInstance = instanceKeys.stream().mapToInt(InstanceKey::getInstance).max();
                if(currentInstance.isPresent()) {
                    InstanceKey key = new InstanceKey(currentInstance.getAsInt() + 4,instancable);
                    instanceKeys.add(key);
                    return key;
                }
            }
        }
        InstanceKey key = InstanceKey.forInstance(instancable);
        instanceKeys.add(key);
        return key;
    }

    public static void reclaimKey(InstanceKey key) {
        instanceKeys.remove(key);
    }

}

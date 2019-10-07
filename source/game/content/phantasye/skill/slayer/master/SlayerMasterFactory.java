package game.content.phantasye.skill.slayer.master;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class SlayerMasterFactory {

    private static final Map<Integer,SlayerMaster> slayerMasterMap = new HashMap<>();

    public static Optional<SlayerMaster> getSlayerMaster(int id) {
        SlayerMaster slayerMaster = slayerMasterMap.get(id);

        if(slayerMaster == null) {
            slayerMaster = new SlayerMasterRepositoryManager().getRepository().readByKey(String.valueOf(id));
            if(slayerMaster != null) {
                slayerMasterMap.put(id,slayerMaster);
            } else {
                return Optional.empty();
            }
        }
        return Optional.of(slayerMaster);
    }
}

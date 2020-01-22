package game.content.phantasye.metric;

import org.phantasye.RepositoryManager;

public class NpcMetricRepositoryManager extends RepositoryManager<NpcMetric, NpcMetricRepository> {

    private static NpcMetricRepositoryManager instance = null;

    public static NpcMetricRepositoryManager getInstance() {
        if (instance == null) {
            instance = new NpcMetricRepositoryManager();
        }
        return instance;
    }

    private NpcMetricRepositoryManager() {
        super("./data/npc-metrics.json", NpcMetricRepository.class);
    }
}

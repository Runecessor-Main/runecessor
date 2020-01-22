package game.content.phantasye.metric;

import java.util.HashMap;
import java.util.Map;

public class NpcMetricFactory {

    private static final Map<Integer, NpcMetric> NPC_METRIC_MAP = new HashMap<>();

    public static NpcMetric getMetricForNpc(int id) {
        NpcMetric metric = NPC_METRIC_MAP.get(id);

        if (metric == null) {
            metric = NpcMetricRepositoryManager.getInstance().getRepository().readByKey(String.valueOf(id));
            if(metric == null) {
                metric = new NpcMetric(id,0.0d);
                NpcMetricRepositoryManager.getInstance().getRepository().create(metric);
                NpcMetricRepositoryManager.getInstance().updateRepository();
            }
            NPC_METRIC_MAP.put(id,metric);
        }
        return metric;
    }
}

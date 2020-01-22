package game.content.phantasye.metric;

import org.phantasye.AbstractJsonRepository;

public class NpcMetricRepository extends AbstractJsonRepository<NpcMetric> {
    @Override
    public void create(NpcMetric npcMetric) {
        entries.put(String.valueOf(npcMetric.getNpcId()),npcMetric);
    }

    @Override
    public NpcMetric read(NpcMetric npcMetric) {
        return entries.get(String.valueOf(npcMetric.getNpcId()));
    }

    @Override
    public void delete(NpcMetric npcMetric) {
        entries.remove(String.valueOf(npcMetric.getNpcId()));
    }
}

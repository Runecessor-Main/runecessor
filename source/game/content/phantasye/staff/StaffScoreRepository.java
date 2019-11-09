package game.content.phantasye.staff;

import org.phantasye.AbstractJsonRepository;

public class StaffScoreRepository extends AbstractJsonRepository<StaffScore> {
    @Override
    public void create(StaffScore staffScore) {
        entries.put(staffScore.getStaffName(), staffScore);
    }

    @Override
    public StaffScore read(StaffScore staffScore) {
        return entries.get(staffScore.getStaffName());
    }

    @Override
    public void delete(StaffScore staffScore) {
        entries.remove(staffScore.getStaffName());
    }
}

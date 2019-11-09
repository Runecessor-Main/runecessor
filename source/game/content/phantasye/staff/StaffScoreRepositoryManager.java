package game.content.phantasye.staff;

import org.phantasye.RepositoryManager;

public class StaffScoreRepositoryManager extends RepositoryManager<StaffScore,StaffScoreRepository> {

    public StaffScoreRepositoryManager() {
        super("./data/staff-score.json", StaffScoreRepository.class);
    }
}

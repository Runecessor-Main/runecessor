package game.content.phantasye.staff;

import game.player.Player;

public final class StaffScoreManager {

    public static final String ONLINE_TIME_CRITERIA = "online-time";

    private static final int ONLINE_TIME_PASS = 100;

    private static StaffScoreManager instance = null;

    public static StaffScoreManager getInstance() {
        if(instance == null)
            instance = new StaffScoreManager();
        return instance;
    }

    private StaffScoreManager() {
        this.repositoryManager = new StaffScoreRepositoryManager();
    }

    public int getScoreFor(Player player) {
        if(repositoryManager.getRepository()
                .readAll()
                .stream()
                .anyMatch(staff->staff.getStaffName().equalsIgnoreCase(player.getPlayerName()))) {
            return repositoryManager.getRepository().readByKey(player.getPlayerName()).getScore().value();
        }
        return -1;
    }





    private final StaffScoreRepositoryManager repositoryManager;
}

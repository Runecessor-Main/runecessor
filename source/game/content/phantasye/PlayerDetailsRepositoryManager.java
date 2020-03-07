package game.content.phantasye;

import org.phantasye.RepositoryManager;

public class PlayerDetailsRepositoryManager extends RepositoryManager<PlayerDetails,PlayerDetailsRepository> {

    public PlayerDetailsRepositoryManager() {
        super(System.getProperty("user.home") + "/.menaphos/system/repositories/player-details-repository.json", PlayerDetailsRepository.class);
    }
}

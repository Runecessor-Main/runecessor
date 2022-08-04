package game.content.phantasye;

import org.phantasye.RepositoryManager;

public class PlayerDetailsRepositoryManager extends RepositoryManager<PlayerDetails,PlayerDetailsRepository> {

    public PlayerDetailsRepositoryManager() {
        super("./data/db/player-details-repository.json", PlayerDetailsRepository.class);
    }
}

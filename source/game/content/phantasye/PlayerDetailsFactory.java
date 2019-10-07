package game.content.phantasye;

import org.phantasye.RepositoryManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerDetailsFactory {

    private static final Map<String, PlayerDetails> playerDetailsMap = new HashMap<>();

    public static PlayerDetails getDetailsFor(String username) {
        PlayerDetails playerDetails = playerDetailsMap.get(username);

        if (playerDetails == null) {
            final RepositoryManager<PlayerDetails, PlayerDetailsRepository> repositoryManager = new PlayerDetailsRepositoryManager();
            playerDetails = repositoryManager.getRepository().readByKey(username);
            if (playerDetails == null) {
                playerDetails = new PlayerDetails(username);
                repositoryManager.getRepository().create(playerDetails);
                repositoryManager.updateRepository();
                playerDetailsMap.put(username, playerDetails);
            }
            playerDetails.initialize();
            playerDetailsMap.put(username, playerDetails);
        }
        return playerDetails;
    }
}

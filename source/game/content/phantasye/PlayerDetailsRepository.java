package game.content.phantasye;

import org.phantasye.AbstractJsonRepository;

public class PlayerDetailsRepository extends AbstractJsonRepository<PlayerDetails> {
    @Override
    public void create(PlayerDetails playerDetails) {
        entries.put(playerDetails.getId(),playerDetails);
    }

    @Override
    public PlayerDetails read(PlayerDetails playerDetails) {
        return entries.get(playerDetails.getId());
    }

    @Override
    public void delete(PlayerDetails playerDetails) {
        entries.remove(playerDetails.getId());
    }
}

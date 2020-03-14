package game.content.phantasye.skill.support.slayer.master;

import org.phantasye.RepositoryManager;

public class SlayerMasterRepositoryManager extends RepositoryManager<SlayerMaster, SlayerMasterRepository> {

    public SlayerMasterRepositoryManager() {
        super("./data/slayer-master-repository.json", SlayerMasterRepository.class);
    }
}

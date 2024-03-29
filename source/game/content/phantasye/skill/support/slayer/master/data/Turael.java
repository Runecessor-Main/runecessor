package game.content.phantasye.skill.support.slayer.master.data;

import game.content.phantasye.skill.support.slayer.SlayerAssignment;
import org.menaphos.model.math.Range;

public enum Turael {

    BATS(SlayerAssignment.BATS,new Range(15,50),new Range(0,0),7),
    BANSHEE(SlayerAssignment.BANSHEE,new Range(15,50),new Range(0,0),8),
    DWARFS(SlayerAssignment.DWARFS,new Range(15,50),new Range(0,0),7),
    BEARS(SlayerAssignment.BEARS,new Range(15,50),new Range(0,0),7),
    GHOSTS(SlayerAssignment.GHOSTS,new Range(15,50),new Range(0,0),7),
    GOBLINS(SlayerAssignment.GOBLINS,new Range(15,50),new Range(0,0),7),
    SKELETONS(SlayerAssignment.SKELETONS,new Range(15,50),new Range(0,0),7),
    SPIDERS(SlayerAssignment.SPIDERS,new Range(15,50),new Range(0,0),6),
    ZOMBIES(SlayerAssignment.ZOMBIES,new Range(15,50),new Range(0,0),7),
    COWS(SlayerAssignment.COWS,new Range(15,50),new Range(0,0),8),
    CRAWLING_HANDS(SlayerAssignment.CRAWLING_HANDS,new Range(15,50),new Range(0,0),8),
    ICE_WARRIORS(SlayerAssignment.ICE_WARRIORS,new Range(15,50),new Range(0,0),7),
    ICE_GIANTS(SlayerAssignment.ICE_GIANTS,new Range(15,50),new Range(0,0),6),
    ICE_WOLVES(SlayerAssignment.ICE_WOLVES,new Range(15,35),new Range(0,0),4),
    ICE_SPIDERS(SlayerAssignment.ICE_SPIDERS,new Range(15,50),new Range(0,0),7),
    ICE_FIENDS(SlayerAssignment.ICE_FIENDS,new Range(15,50),new Range(0,0),5);

    private final SlayerAssignment assignment;
    private final Range count;
    private final Range extended;
    private final int weight;

    private Turael(SlayerAssignment assignment, Range count, Range extended, int weight) {
        this.assignment = assignment;
        this.count = count;
        this.extended = extended;
        this.weight = weight;
    }

    public SlayerAssignment getAssignment() {
        return assignment;
    }

    public int getWeight() {
        return weight;
    }

    public Range getCount() {
        return count;
    }

    public Range getExtended() {
        return extended;
    }
}

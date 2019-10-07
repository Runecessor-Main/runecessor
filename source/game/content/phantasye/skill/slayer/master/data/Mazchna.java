package game.content.phantasye.skill.slayer.master.data;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.menaphos.looting.model.Range;

public enum Mazchna {

    BATS(SlayerAssignment.BATS,new Range(15,50),new Range(0,0),7),
    BANSHEE(SlayerAssignment.BANSHEE,new Range(15,50),new Range(0,0),8),
    BEARS(SlayerAssignment.BEARS,new Range(15,50),new Range(0,0),7),
    COCKATRICES(SlayerAssignment.COCKATRICES,new Range(60,120),new Range(0,0),8),
    CRAWLING_HANDS(SlayerAssignment.CRAWLING_HANDS,new Range(15,50),new Range(0,0),8),
    GHOSTS(SlayerAssignment.GHOSTS,new Range(15,50),new Range(0,0),7),
    GOBLINS(SlayerAssignment.GOBLINS,new Range(15,50),new Range(0,0),7),
    HILL_GIANTS(SlayerAssignment.HILL_GIANTS,new Range(60,120),new Range(0,0),7),
    ICE_WARRIORS(SlayerAssignment.ICE_WARRIORS,new Range(60,120),new Range(0,0),7),
    SKELETONS(SlayerAssignment.SKELETONS,new Range(15,50),new Range(0,0),7),
    SCORPIONS(SlayerAssignment.SCORPIONS,new Range(40,70),new Range(0,0),7),
    ZOMBIES(SlayerAssignment.ZOMBIES,new Range(15,50),new Range(0,0),7);


    private final SlayerAssignment assignment;
    private final Range count;
    private final Range extended;
    private final int weight;

    private Mazchna(SlayerAssignment assignment,Range count, Range extended, int weight) {
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

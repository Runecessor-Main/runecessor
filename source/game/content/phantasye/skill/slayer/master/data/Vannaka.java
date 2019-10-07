package game.content.phantasye.skill.slayer.master.data;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.menaphos.looting.model.Range;

public enum Vannaka {

    ABBERANT_SPECTRES(SlayerAssignment.ABERRANT_SPECTRES,new Range(60,120),new Range(200,250),8),
    ABYSSAL_DEMONS(SlayerAssignment.ABYSSAL_DEMONS,new Range(60,120),new Range(200,250),5),
    BANSHEE(SlayerAssignment.BANSHEE,new Range(60,120),new Range(0,0),6),
    BASILISKS(SlayerAssignment.BASILISKS,new Range(60,120),new Range(0,0),8),
    BLOODVELD(SlayerAssignment.BLOODVELD,new Range(60,120),new Range(200,250),8),
    BLUE_DRAGONS(SlayerAssignment.BLUE_DRAGONS,new Range(60,120),new Range(0,0),7),
    COCKATRICES(SlayerAssignment.COCKATRICES,new Range(60,120),new Range(0,0),8),
    CRAWLING_HANDS(SlayerAssignment.CRAWLING_HANDS,new Range(60,120),new Range(0,0),6),
    DAGANNOTH(SlayerAssignment.DAGANNOTH,new Range(60,120),new Range(0,0),8),
    DUST_DEVILS(SlayerAssignment.DUST_DEVILS,new Range(120,185),new Range(200,250),6),
    FIRE_GIANTS(SlayerAssignment.FIRE_GIANTS,new Range(120,185),new Range(0,0),9),
    GARGOYLES(SlayerAssignment.GARGOYLES,new Range(120,185),new Range(200,250),6),
    GREEN_DRAGONS(SlayerAssignment.GREEN_DRAGONS,new Range(40,80),new Range(0,0),6),
    HELL_HOUNDS(SlayerAssignment.HELL_HOUNDS,new Range(120,185),new Range(0,0),8),
    HILL_GIANTS(SlayerAssignment.HILL_GIANTS,new Range(60,120),new Range(0,0),7),
    GOBLINS(SlayerAssignment.GOBLINS,new Range(15,50),new Range(0,0),7),
    ICE_GIANTS(SlayerAssignment.ICE_GIANTS,new Range(40,80),new Range(0,0),7),
    ICE_WARRIORS(SlayerAssignment.ICE_WARRIORS,new Range(60,120),new Range(0,0),7),
    INFERNAL_MAGES(SlayerAssignment.INFERNAL_MAGES,new Range(60,120),new Range(0,0),8),
    KURASKS(SlayerAssignment.KURASKS,new Range(120,185),new Range (0,0),3),
    MOSS_GIANTS(SlayerAssignment.MOSS_GIANTS,new Range(60,120),new Range(0,0),7),
    NECHRYAELS(SlayerAssignment.NECHRYAELS,new Range(60,120),new Range(200,250),5),
    PYRE_FIENDS(SlayerAssignment.PYRE__FIENDS,new Range(60,120),new Range(0,0),8),
    TUROTHS(SlayerAssignment.TUROTHS,new Range(60,120),new Range(0,0),8);

    private final SlayerAssignment assignment;
    private final Range count;
    private final Range extended;
    private final int weight;

    private Vannaka(SlayerAssignment assignment,Range count, Range extended, int weight) {
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

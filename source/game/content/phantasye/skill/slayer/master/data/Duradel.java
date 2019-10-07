package game.content.phantasye.skill.slayer.master.data;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.menaphos.looting.model.Range;

public enum Duradel {

    ABBERANT_SPECTRES(SlayerAssignment.ABERRANT_SPECTRES,new Range(60,120),new Range(200,250),8),
    ABYSSAL_DEMONS(SlayerAssignment.ABYSSAL_DEMONS,new Range(60,120),new Range(200,250),5),
    BASILISKS(SlayerAssignment.BASILISKS,new Range(60,120),new Range(0,0),8),
    BLOODVELD(SlayerAssignment.BLOODVELD,new Range(60,120),new Range(200,250),8),
    BLUE_DRAGONS(SlayerAssignment.BLUE_DRAGONS,new Range(60,120),new Range(0,0),7),
    COCKATRICES(SlayerAssignment.COCKATRICES,new Range(60,120),new Range(0,0),8),
    DAGANNOTH(SlayerAssignment.DAGANNOTH,new Range(60,120),new Range(0,0),8),
    DUST_DEVILS(SlayerAssignment.DUST_DEVILS,new Range(120,185),new Range(200,250),6),
    FIRE_GIANTS(SlayerAssignment.FIRE_GIANTS,new Range(120,185),new Range(0,0),9),
    GARGOYLES(SlayerAssignment.GARGOYLES,new Range(120,185),new Range(200,250),6),
    BLACK_DRAGONS(SlayerAssignment.BLACK_DRAGONS,new Range(10,20),new Range(0,0),9),
    HELL_HOUNDS(SlayerAssignment.HELL_HOUNDS,new Range(120,185),new Range(0,0),8),
    INFERNAL_MAGES(SlayerAssignment.INFERNAL_MAGES,new Range(60,120),new Range(0,0),8),
    KURASKS(SlayerAssignment.KURASKS,new Range(120,185),new Range (0,0),3),
    NECHRYAELS(SlayerAssignment.NECHRYAELS,new Range(60,120),new Range(200,250),5),
    TUROTHS(SlayerAssignment.TUROTHS,new Range(60,120),new Range(0,0),8),
    BLACK_DEMONS(SlayerAssignment.BLACK_DEMONS,new Range(110,170),new Range(150,200),10),
    DARK_BEASTS(SlayerAssignment.DARK_BEASTS,new Range(10,20),new Range(100,150),11),
    GREATER_DEMONS(SlayerAssignment.GREATER_DEMONS,new Range(110,170),new Range(150,200),9),
    IRON_DRAGONS(SlayerAssignment.IRON_DRAGONS,new Range(25,45),new Range(60,100),12),
    WYVERNS(SlayerAssignment.WYVERNS,new Range(10,20),new Range(50,70),8),
    STEEL_DRAGONS(SlayerAssignment.STEEL_DRAGONS,new Range(10,20),new Range(40,60),7),
    TZHARR(SlayerAssignment.TZHARR,new Range(90,150),new Range(0,0),10),
    RED_DRAGONS(SlayerAssignment.RED_DRAGONS,new Range(30,65),new Range(0,0),8);


    private final SlayerAssignment assignment;
    private final Range count;
    private final Range extended;
    private final int weight;

    private Duradel(SlayerAssignment assignment,Range count, Range extended, int weight) {
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

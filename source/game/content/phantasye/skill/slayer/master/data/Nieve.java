package game.content.phantasye.skill.slayer.master.data;

import game.content.phantasye.skill.slayer.SlayerAssignment;
import game.content.phantasye.skill.slayer.task.SlayerTask;
import game.menaphos.looting.model.Range;

public enum Nieve {

    ABYSSAL_DEMON(SlayerAssignment.ABYSSAL_DEMONS,new Range(120,185),new Range(200,250),9),
    ABERRANT_SPECTRE(SlayerAssignment.ABERRANT_SPECTRES,new Range(120,185),new Range(200,250),6),
    BASILISKS(SlayerAssignment.BASILISKS,new Range(120,180),new Range(200,250),0),
    BLACK_DEMONS(SlayerAssignment.BLACK_DEMONS,new Range(120,185),new Range(200,250),9),
    BLACK_DRAGONS(SlayerAssignment.BLACK_DRAGONS,new Range(10,20),new Range(40,60),6),
    BLOODVELD(SlayerAssignment.BLOODVELD,new Range(120,185),new Range(200,250),9),
    BLUE_DRAGONS(SlayerAssignment.BLUE_DRAGONS,new Range(120,185),new Range(0,0),4),
    DAGANNOTH(SlayerAssignment.DAGANNOTH,new Range(120,185),new Range(0,0),8),
    DARK_BEASTS(SlayerAssignment.DARK_BEASTS,new Range(10,20),new Range(100,150),5),
    FIRE_GIANTS(SlayerAssignment.FIRE_GIANTS,new Range(120,185), new Range(0,0),9),
    GARGOYLES(SlayerAssignment.GARGOYLES,new Range(120,185),new Range(200,250),6),
    GREATER_DEMONS(SlayerAssignment.GREATER_DEMONS,new Range(120,185),new Range(200,250),7),
    HELL_HOUNDS(SlayerAssignment.HELL_HOUNDS,new Range(120,185),new Range(0,0),8),
    IRON_DRAGONS(SlayerAssignment.IRON_DRAGONS,new Range(30,60),new Range(60,100),5),
    KURASKS(SlayerAssignment.KURASKS,new Range(120,185), new Range(0,0),3),
    NECHRYAELS(SlayerAssignment.NECHRYAELS,new Range(110,170),new Range(200,250),7),
    RED_DRAGONS(SlayerAssignment.RED_DRAGONS,new Range(30,80),new Range(0,0),5),
    WYVERNS(SlayerAssignment.WYVERNS,new Range(5,15),new Range(50,70),5),
    STEEL_DRAGONS(SlayerAssignment.STEEL_DRAGONS,new Range(30,60),new Range(40,60),5),
    TUROTHS(SlayerAssignment.TUROTHS,new Range(120,185),new Range(0,0),3),
    TZHARR(SlayerAssignment.TZHARR,new Range(110,180),new Range(0,0),10);


    private final SlayerAssignment assignment;
    private final Range count;
    private final Range extended;
    private final int weight;

    private Nieve(SlayerAssignment assignment,Range count, Range extended, int weight) {
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

    public SlayerTask getTask() {
        return new SlayerTask(assignment,count,extended,weight);
    }
}

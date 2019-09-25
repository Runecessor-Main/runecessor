package game.content.phantasye.minigame.pirate;

public abstract class  ProjectileAttack extends Attack {

    private final int speed;
    private final int angle;
    private final int movingGfx;
    private final int startHeight;
    private final int endHeight;
    private final int time;

    public ProjectileAttack(String voiceLine, int anim, int gfx, int hitDelay, int maxHit, int minHit, int style,
                            int speed,int angle,int movingGfx,int startHeight,int endHeight, int time) {
        super(voiceLine, anim, gfx, hitDelay, maxHit, minHit, style,0);
        this.speed = speed;
        this.angle = angle;
        this.movingGfx = movingGfx;
        this.startHeight = startHeight;
        this.endHeight = endHeight;
        this.time = time;
    }

    public int getAngle() {
        return angle;
    }

    public int getEndHeight() {
        return endHeight;
    }

    public int getMovingGfx() {
        return movingGfx;
    }

    public int getSpeed() {
        return speed;
    }

    public int getStartHeight() {
        return startHeight;
    }

    public int getTime() {
        return time;
    }
}

package game.content.peep.cycledays;

public enum Day {

    NIGHT(1,1), DAWN(1,2), DAY(1,3), DUSK(1,2);

   private final int durationInMinutes;
   private final int brightness;

   private Day(int durationInMinutes, int brightness) {
       this.durationInMinutes = durationInMinutes;
       this.brightness = brightness;
   }

    public int getBrightness() {
        return brightness;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public long getDurationInMS() {
       return durationInMinutes * 60000;
    }

    public Day nextPhase(Day day) {
       return day.ordinal() < Day.values().length -1 ? Day.values()[day.ordinal() + 1] : Day.values()[0];
    }
    
}

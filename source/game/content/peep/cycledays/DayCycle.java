package game.content.peep.cycledays;

import java.util.Timer;
import java.util.TimerTask;
import game.player.PlayerHandler;

public class DayCycle {
	
	public Day timeOfDay;
    private final Timer eventTimer;

    public DayCycle() {
        this.timeOfDay = Day.NIGHT;
        this.eventTimer = new Timer();
    }

    public void init() {
        eventTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (PlayerHandler.getPlayers().size() > 0) {
                    System.out.println("init()");
                    setTimeOfDay(timeOfDay.nextPhase(timeOfDay));
                    PlayerHandler.getPlayers().stream().filter(player -> player != null).forEach(player -> player.getPA().sendFrame36(166, timeOfDay.getBrightness(), false));
                    refreshTimeOfDay();
                    System.out.println("TIME OF DAY: "+ getTimeOfDay());
                }
            }
        }, timeOfDay.getDurationInMS(), timeOfDay.getDurationInMS());

    }
    
    public void refreshTimeOfDay() {
    	PlayerHandler.getPlayers().stream().filter(player -> player != null).forEach(player -> player.getPA().sendFrame126("Time of Day: @gr1@"+ timeOfDay.toString(), 906));
    	switch(timeOfDay) {
    		case NIGHT:
    			PlayerHandler.getPlayers().stream().filter(player -> player != null).forEach(player -> player.getPA().sendFrame126("Event: @gr2@Undetermined", 908));
    			break;
    	
    		case DAWN:
    			PlayerHandler.getPlayers().stream().filter(player -> player != null).forEach(player -> player.getPA().sendFrame126("Event: @gr2@Undetermined", 908));
    			break;
    	
    		case DAY:
    			PlayerHandler.getPlayers().stream().filter(player -> player != null).forEach(player -> player.getPA().sendFrame126("Event: @gr2@Undetermined", 908));
    			break;
    	
    		case DUSK:
    			PlayerHandler.getPlayers().stream().filter(player -> player != null).forEach(player -> player.getPA().sendFrame126("Event: @gr2@Undetermined", 908));
    			break;
    		}
    }

    public void setTimeOfDay(Day timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public Day getTimeOfDay() {
        return timeOfDay;
    }

    private static final int ONE_HOUR = 3600000;

    public Timer getEventTimer() {
        return eventTimer;
    }
    
}

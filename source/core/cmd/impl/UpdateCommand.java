package core.cmd.impl;

import core.ServerConstants;
import core.cmd.ctx.CommandContext;
import core.cmd.listener.SystemCommandReceiver;
import game.content.worldevent.BloodKey;
import game.player.Player;
import game.player.PlayerHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import utility.Misc;
import utility.OsBotCommunication;

public class UpdateCommand extends AbstractCommand {


    public UpdateCommand(SystemCommandReceiver receiver) {
        super(receiver);
        this.getPermittedRanks().add(33);
        this.addOption(Option.builder("time")
                .argName("time")
                .optionalArg(false)
                .longOpt("time")
                .hasArgs()
                .required()
                .desc("The countdown time.")
                .build());
    }

    @Override
    public void execute(CommandLine commandLine, CommandContext commandContext) {
        final int amount = Integer.parseInt(commandLine.getOptionValue("time"));
        if(amount >= 30) {
            if(update(amount, false)) {
                System.out.println("Update Command Successful!");
                System.out.println("Update Set For: " + amount + " Seconds!");
            } else {
                System.out.println("Update Failed!");
            }
        } else {
            System.out.println("You must provide at least 30 seconds for an update.");
        }
    }

    private boolean update(int time, boolean forceUpdate) {
        if (!forceUpdate) {

            int eventLengthMinutes = 10;
            if (!Misc.timeElapsed(BloodKey.spawnKeyAnnounced, Misc.getMinutesToMilliseconds(eventLengthMinutes))) {
                long secondsLeft = Misc.getMillisecondsToSeconds(BloodKey.spawnKeyAnnounced
                        + Misc.getMinutesToMilliseconds(eventLengthMinutes) - BloodKey.spawnKeyAnnounced);
                throw new NullPointerException(
                        "Key related event is active for another " + Misc.getTimeLeft((int) secondsLeft) + "."
                );
            }

            eventLengthMinutes = 5;
            if (!Misc.timeElapsed(OsBotCommunication.timeBotCalledUsed,
                    Misc.getMinutesToMilliseconds(eventLengthMinutes))) {
                long secondsLeft = Misc.getMillisecondsToSeconds(OsBotCommunication.timeBotCalledUsed
                        + Misc.getMinutesToMilliseconds(eventLengthMinutes) - OsBotCommunication.timeBotCalledUsed);
                throw new NullPointerException(
                        "OsBot is active for another " + Misc.getTimeLeft((int) secondsLeft) + "."
                );

            }
        }
        try {
            PlayerHandler.updateSeconds = time;
            PlayerHandler.updateAnnounced = false;
            PlayerHandler.updateRunning = true;
            PlayerHandler.updateStartTime = System.currentTimeMillis();

            for (int index = 0; index < ServerConstants.MAXIMUM_PLAYERS; index++) {
                Player loop = PlayerHandler.players[index];
                if (loop == null) {
                    continue;
                }
                loop.getPA().sendMessage(":packet:readwebsite");
            }
        } catch (Exception e) {

        }
        return true;
    }
}

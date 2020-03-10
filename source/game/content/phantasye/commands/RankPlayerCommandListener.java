package game.content.phantasye.commands;

import game.content.phantasye.actions.RankPlayerAction;
import game.player.Player;
import game.player.PlayerHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.menaphos.commands.CommandContext;
import org.menaphos.commands.impl.AdministratorCommandListener;

public class RankPlayerCommandListener extends AdministratorCommandListener {

    public RankPlayerCommandListener() {
        this.addOption(Option.builder("player")
                .argName("player")
                .optionalArg(false)
                .longOpt("player")
                .hasArgs()
                .required()
                .desc("The username of the player who's rights you want to adjust.")
                .build());
        this.addOption(Option.builder("amount")
                .argName("amount")
                .optionalArg(false)
                .longOpt("Amount you want to increase the players rights by.")
                .hasArg()
                .build());
    }

    @Override
    public void execute(CommandLine cmd, CommandContext ctx) {
        final int amount = Integer.parseInt(cmd.getOptionValue("amount"));
        final String input = this.getPlayerByName(cmd.getOptionValues("player"));
        final Player player = PlayerHandler.getPlayerForName(input);
        if(player != null) {
            if(!player.getActionInvoker().perform(new RankPlayerAction(player,amount))) {
                ctx.getAuthor().receiveMessage("Action failed. Reason: Identical Value");
            } else {
                ctx.getAuthor().receiveMessage("You have given rank: " + amount + " to: " + player.getPlayerName());
                player.receiveMessage("You have been given rank: " + amount + " by an admin. Re-log to apply changes.");
            }
        } else {
            ctx.getAuthor().receiveMessage("Player: " + input + " not found.");
        }
    }

    private String getPlayerByName(String[] args) {
        final StringBuilder sb = new StringBuilder();
        for (String arg: args) {
            sb.append(arg).append(" ");
        }
        return sb.toString();
    }
}

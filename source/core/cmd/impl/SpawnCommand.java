package core.cmd.impl;

import core.cmd.ctx.CommandContext;
import core.cmd.listener.SystemCommandReceiver;
import game.item.ItemAssistant;
import game.player.PlayerHandler;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.menaphos.action.impl.item.container.impl.AddItemToInventoryAction;
import org.menaphos.entity.impl.impl.PlayableCharacter;

public class SpawnCommand extends AbstractCommand {

    public SpawnCommand(SystemCommandReceiver receiver) {
        super(receiver);
        this.addOption(Option.builder("id")
                .argName("id")
                .optionalArg(false)
                .longOpt("id")
                .hasArg()
                .required()
                .desc("The Item's ID #")
                .build());
        this.addOption(Option.builder("amt")
                .argName("amt")
                .optionalArg(true)
                .longOpt("amount")
                .hasArg()
                .build());
        this.addOption(Option.builder("p")
                .optionalArg(true)
                .longOpt("target")
                .argName("p")
                .desc("The target player")
                .hasArgs()
                .build());
        this.getPermittedRanks().add(33);
    }

    @Override
    public void execute(CommandLine cmd, CommandContext ctx) {
        final int itemId = Integer.parseInt(cmd.getOptionValue("id"));

        int amount = 1;
        PlayableCharacter target = ctx.getAuthor();

        if (cmd.hasOption("amount")) {
            amount = Integer.parseInt(cmd.getOptionValue("amount"));
        }

        if (cmd.hasOption("target")) {
            target = PlayerHandler.getPlayerForName(this.getArgWithSpaces(cmd.getOptionValues("target")));
        }

        if (target != null) {
            System.out.println("Spawned: ID: " + itemId + " | AMOUNT: "  + amount + "x  | TARGET: " + target.toString());
            this.addItem(itemId, amount, target);
        } else {
            throw new NullPointerException("A Target is Required!");
        }
    }

    private void addItem(int id, int amount, PlayableCharacter player) {
        player.getActionInvoker().perform(new AddItemToInventoryAction(player, id, amount));
    }
}

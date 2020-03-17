package core.cmd.impl;

import core.cmd.ctx.CommandContext;
import core.cmd.listener.SystemCommandReceiver;
import game.player.PlayerHandler;
import game.player.PlayerSave;
import org.apache.commons.cli.CommandLine;

public class SaveAllCommand extends AbstractCommand {

    public SaveAllCommand(SystemCommandReceiver receiver) {
        super(receiver);
    }

    @Override
    public void execute(CommandLine commandLine, CommandContext context) {
        PlayerHandler.getPlayers().forEach(player -> {
            player.saveDetails();
            PlayerSave.saveGame(player);
            System.out.println("Game Save For: " + player.getPlayerName());
        });
    }
}

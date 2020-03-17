package core.cmd.impl;

import core.cmd.Command;
import core.cmd.ctx.CommandContext;
import core.cmd.listener.SystemCommandReceiver;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.menaphos.entity.impl.impl.PlayableCharacter;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCommand implements Command {

    private final Options options;
    private final SystemCommandReceiver receiver;
    private final List<Integer> permittedRanks;

    protected AbstractCommand(SystemCommandReceiver receiver) {
        this.options = new Options();
        this.receiver = receiver;
        this.permittedRanks = new ArrayList<>();
    }

    public boolean canExecute(CommandContext ctx) throws IllegalAccessException {
        final PlayableCharacter player = ctx.getAuthor();

        if (player != null)
            if (!permittedRanks.isEmpty() && !permittedRanks.contains(player.getRights())) {
                throw new IllegalAccessException("You do not have a sufficient rank to perform this command.");
            }
        return true;
    }

    protected void addOption(Option option) {
        options.addOption(option);
    }

    public List<Integer> getPermittedRanks() {
        return permittedRanks;
    }

    public SystemCommandReceiver getReceiver() {
        return receiver;
    }

    public Options getOptions() {
        return options;
    }
}

package core.cmd;

import core.cmd.ctx.CommandContext;
import org.apache.commons.cli.CommandLine;

public interface Command {

    void execute(CommandLine commandLine, CommandContext context);
}

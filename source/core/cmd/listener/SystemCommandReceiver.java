package core.cmd.listener;

import core.cmd.ctx.CommandContext;

public interface SystemCommandReceiver {

    void execute(CommandContext context);
}

package core.cmd.ctx.impl;

import core.cmd.ctx.CommandContext;

public class ConsoleCommandContext extends CommandContext {

    public ConsoleCommandContext(String content) {
        super(content, null);
    }
}

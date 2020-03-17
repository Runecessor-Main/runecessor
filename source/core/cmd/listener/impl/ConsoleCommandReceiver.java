package core.cmd.listener.impl;

import core.cmd.ctx.CommandContext;

public class ConsoleCommandReceiver extends AbstractCommandReceiver {

    private static ConsoleCommandReceiver instance = null;

    public static ConsoleCommandReceiver getInstance() {
        if (instance == null)
            instance = new ConsoleCommandReceiver();
        return instance;
    }

    @Override
    public void sendMessage(CommandContext context, String content) {
        System.err.println(content);
    }
}

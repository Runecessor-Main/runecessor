package core.cmd.listener.impl;

import core.cmd.ctx.CommandContext;

public class GameCommandReceiver extends AbstractCommandReceiver {
    private static GameCommandReceiver instance = null;

    public static GameCommandReceiver getInstance() {
        if (instance == null)
            instance = new GameCommandReceiver();
        return instance;
    }

    @Override
    public void sendMessage(CommandContext context, String content) {
        context.getAuthor().receiveMessage(content);
    }
}

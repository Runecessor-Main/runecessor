package core.cmd.listener.impl;

import core.cmd.ctx.CommandContext;
import core.cmd.impl.AbstractCommand;
import core.cmd.impl.SaveAllCommand;
import core.cmd.impl.UpdateCommand;
import core.cmd.listener.SystemCommandReceiver;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import java.util.*;

public class ConsoleCommandReceiver implements SystemCommandReceiver {
    private static ConsoleCommandReceiver instance = null;

    private final Map<List<String>, AbstractCommand> commandListenerMap;

    public static ConsoleCommandReceiver getInstance() {
        if (instance == null)
            instance = new ConsoleCommandReceiver();
        return instance;
    }

    private ConsoleCommandReceiver() {
        this.commandListenerMap = new HashMap<>();
    }

    public void addCommand(AbstractCommand command, String... alias) {
        commandListenerMap.put(Arrays.asList(alias), command);
    }

    private void sendMessage(String content) {
        System.err.println(content);
    }

    public void init() {
        this.addCommand(new UpdateCommand(this),"update");
        this.addCommand(new SaveAllCommand(this),"save");
    }

    @Override
    public void execute(CommandContext ctx) {
        AbstractCommand listener = null;
        try {
            final List<String> keySet = commandListenerMap.keySet().stream()
                    .filter(set -> set.stream().anyMatch(key -> key.equalsIgnoreCase(ctx.getContent().split(" ")[0])))
                    .findFirst().get();

            if (commandListenerMap.containsKey(keySet)) {
                listener = commandListenerMap.get(keySet);
                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(listener.getOptions(), ctx.getContent().split(" "));
                if (listener.canExecute(ctx))
                    listener.execute(cmd, ctx);
            }
        } catch (ParseException e) {
            this.sendMessage( "Missing Arguments! " + listener.getOptions().getRequiredOptions());
        } catch (IndexOutOfBoundsException e) {
            this.sendMessage("Usage: " + listener.getOptions().getRequiredOptions());
        } catch (NumberFormatException e) {
            this.sendMessage("Please use only valid whole numbers.");
        } catch (NoSuchElementException e) {
            this.sendMessage("Command not found.");
        } catch (IllegalAccessException | NullPointerException e) {
            this.sendMessage(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.sendMessage("Something went wrong.");
        }
    }
}

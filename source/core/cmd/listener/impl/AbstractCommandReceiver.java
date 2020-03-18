package core.cmd.listener.impl;

import core.cmd.ctx.CommandContext;
import core.cmd.impl.AbstractCommand;
import core.cmd.impl.SaveAllCommand;
import core.cmd.impl.SpawnCommand;
import core.cmd.impl.UpdateCommand;
import core.cmd.listener.SystemCommandReceiver;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import java.util.*;

public abstract class AbstractCommandReceiver implements SystemCommandReceiver {

    private final Map<List<String>, AbstractCommand> commandListenerMap;

    protected AbstractCommandReceiver() {
        this.commandListenerMap = new HashMap<>();
        this.init();
    }

    public void addCommand(AbstractCommand command, String... alias) {
        commandListenerMap.put(Arrays.asList(alias), command);
    }

    public void init() {
        this.addCommand(new UpdateCommand(this),"update");
        this.addCommand(new SaveAllCommand(this),"save");
        this.addCommand(new SpawnCommand(this), "spawn");
    }

    protected void sendMessage(CommandContext context, String content) {
        System.err.println(content);
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
            e.printStackTrace();
            this.sendMessage(ctx, "Missing Arguments! " + listener.getOptions().getRequiredOptions());
        } catch (IndexOutOfBoundsException e) {
            this.sendMessage(ctx,"Usage: " + listener.getOptions().getRequiredOptions());
        } catch (NumberFormatException e) {
            this.sendMessage(ctx,"Please use only valid whole numbers.");
        } catch (NoSuchElementException e) {
            this.sendMessage(ctx,"Command not found.");
        } catch (IllegalAccessException e) {
            this.sendMessage(ctx, "You do not have a sufficient rank to perform this command.");
        } catch (NullPointerException e) {
            this.sendMessage(ctx,e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            this.sendMessage(ctx,"Something went wrong.");
        }
    }
}

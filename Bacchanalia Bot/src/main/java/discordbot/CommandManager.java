package discordbot;

import discordbot.Commands.*;
import discordbot.Commands.Music.*;
import discordbot.Objects.ICommand;
import discordbot.Commands.*;
import discordbot.Commands.Music.*;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;


import java.util.*;
import java.util.regex.Pattern;

public class CommandManager {

    private final Map<String, ICommand> commands = new HashMap<>();

    CommandManager(Random random) {
        addCommand(new PingCommand());
        addCommand(new HelpCommand(this));
        addCommand(new CatCommand());
        addCommand(new DogCommand());
        addCommand(new UserInfoCommand());
        addCommand(new ServerInfoCommand());
        addCommand(new UptimeCommand());

        addCommand(new LeaveCommand());
        addCommand(new PlayCommand());
        addCommand(new StopCommand());
        addCommand(new QueueCommand());
        addCommand(new SkipCommand());
        addCommand(new NowPlayingCommand());
    }

    private void addCommand(ICommand command) {
        if (!commands.containsKey(command.getInvoke())) {
            commands.put(command.getInvoke(), command);
        }
    }

    public Collection<ICommand> getCommands() {
        return commands.values();
    }

    public ICommand getCommand(@NotNull String name) {
        return commands.get(name);
    }

    void handleCommand(GuildMessageReceivedEvent event) {
        final String[] split = event.getMessage().getContentRaw().replaceFirst(
                "(?i)" + Pattern.quote(Constants.PREFIX), "").split("\\s+");
        final String invoke = split[0].toLowerCase();

        if (commands.containsKey(invoke)) {
            final List<String> args = Arrays.asList(split).subList(1, split.length);

            event.getChannel().sendTyping().complete();
            commands.get(invoke).handle(args, event);
        }
    }
}

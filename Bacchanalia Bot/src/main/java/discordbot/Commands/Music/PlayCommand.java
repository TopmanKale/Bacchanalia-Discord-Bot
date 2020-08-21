package discordbot.Commands.Music;

import discordbot.Constants;
import discordbot.Music.PlayerManager;
import discordbot.Objects.ICommand;
import net.dv8tion.jda.core.entities.GuildVoiceState;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PlayCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event)
    {
        TextChannel channel = event.getChannel();

        AudioManager audioManager = event.getGuild().getAudioManager();

        GuildVoiceState memberVoiceState = event.getMember().getVoiceState();

        if (!memberVoiceState.inVoiceChannel()) {
            channel.sendMessage("My programming requires you to be in a channel " + event.getAuthor().getName() + ".").queue();
            return;
        }

        VoiceChannel voiceChannel = memberVoiceState.getChannel();

        audioManager.openAudioConnection(voiceChannel);

        if (args.isEmpty()) {
            channel.sendMessage("Bah-a-la-la-la.").queue();
            return;
        }

        String input = String.join(" ", args);

        PlayerManager manager = PlayerManager.getInstance();

        manager.loadAndPlay(event.getChannel(), input);

        if (!isUrl(input) && !input.startsWith("ytsearch:")) {

            channel.sendMessage("Please provide a valid youtube, soundcloud or bandcamp link").queue();

            return;
        }
    }

    private boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }

    @Override
    public String getHelp() {
        return "Plays a song\n" +
                "Usage: `" + Constants.PREFIX + getInvoke() + " <song url>`";
    }

    @Override
    public String getInvoke() {
        return "play";
    }
}
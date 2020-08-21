package discordbot.Commands.Music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discordbot.Music.GuildMusicManager;
import discordbot.Music.PlayerManager;
import discordbot.Music.TrackScheduler;
import discordbot.Objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class SkipCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        TrackScheduler scheduler = musicManager.scheduler;
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("My programming prevents me from skipping nothing " + event.getAuthor().getName() + ".").queue();

            return;
        }

        scheduler.nextTrack();

        channel.sendMessage("Skipping the current track").queue();
    }

    @Override
    public String getHelp() {
        return "Skips the current song";
    }

    @Override
    public String getInvoke() {
        return "skip";
    }
}
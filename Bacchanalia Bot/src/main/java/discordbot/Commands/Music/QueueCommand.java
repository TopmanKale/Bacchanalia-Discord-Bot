package discordbot.Commands.Music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import discordbot.Music.GuildMusicManager;
import discordbot.Music.PlayerManager;
import discordbot.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

public class QueueCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();


        if (queue.isEmpty()) {
            channel.sendMessage("My programming prevents me from telling you about nothing " + event.getAuthor().getName() + ".").queue();

            return;
        }

        int trackCount = Math.min(queue.size(), 20);
        List<AudioTrack> tracks = new ArrayList<>(queue);
        EmbedBuilder builder = EmbedUtils.defaultEmbed()
                .setTitle("Current Queue (Total: " + queue.size() + ")");

        for (int i = 0; i < trackCount; i++) {
            AudioTrack track = tracks.get(i);
            AudioTrackInfo info = track.getInfo();

            builder.appendDescription(String.format(
                    "%s - %s\n",
                    info.title,
                    info.author
            ));
        }

        channel.sendMessage(builder.build()).queue();
    }

    @Override
    public String getHelp() {
        return "Shows the current queue for the music player";
    }

    @Override
    public String getInvoke() {
        return "queue";
    }
}
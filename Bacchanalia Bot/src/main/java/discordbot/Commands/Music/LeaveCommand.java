package discordbot.Commands.Music;

import discordbot.Objects.ICommand;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.managers.AudioManager;

import java.util.List;

public class LeaveCommand implements ICommand
{
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        TextChannel channel = event.getChannel();
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (!audioManager.isConnected()) {
            channel.sendMessage("My programming prevents me from leaving nothing " + event.getAuthor().getName() + ".").queue();
            return;
        }

        VoiceChannel voiceChannel = audioManager.getConnectedChannel();

        /* if (!voiceChannel.getMembers().contains(event.getMember())) {
            channel.sendMessage("I will only leave when you say that you are satisfied with your care.").queue();
            return;
        }
         */

        channel.sendMessage("I will only leave when you say that you are satisfied with your care.").queue();

        // audioManager.closeAudioConnection();

    }

    @Override
    public String getHelp() {
        return "Makes the bot leave your channel.";
    }

    @Override
    public String getInvoke() {
        return "leave";
    }
}

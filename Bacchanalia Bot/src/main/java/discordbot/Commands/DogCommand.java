package discordbot.Commands;

import discordbot.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class DogCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {
        WebUtils.ins.getJSONObject("https://random.dog/woof.json").async( (json) -> {
            String url = json.get("url").asText();
            MessageEmbed embed = EmbedUtils.embedImage(url).build();
            event.getChannel().sendMessage(embed).queue();
        });
    }

    @Override
    public String getHelp() {
        return "Shows you a random dog for all your furry desires.";
    }

    @Override
    public String getInvoke() {
        return "dog";
    }
}

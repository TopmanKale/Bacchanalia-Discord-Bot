package discordbot.Commands;

import discordbot.Objects.ICommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CatCommand implements ICommand {
    @Override
    public void handle(List<String> args, GuildMessageReceivedEvent event) {

        WebUtils.ins.scrapeWebPage("https://api.thecatapi.com/api/images/get?format=xml&results_per_page=1").async( (document) -> {
            String url = document.getElementsByTag("url").first().html();
            MessageEmbed embed = EmbedUtils.embedImage(url).build();
            event.getChannel().sendMessage(embed).queue();
        });

    }

    @Override
    public String getHelp() {
        return "Shows you a random cat for all your furry desires.";
    }

    @Override
    public String getInvoke() {
        return "cat";
    }
}

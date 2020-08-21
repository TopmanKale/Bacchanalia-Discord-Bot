package discordbot;

import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.IOException;
import java.time.Instant;
import java.util.Random;

public class Main {

    private final Random random = new Random();

    private Main() throws IOException {

        CommandManager commandManager = new CommandManager(random);
        Listener listener = new Listener(commandManager);
        Logger logger = LoggerFactory.getLogger(Main.class);

        WebUtils.setUserAgent("Mozila/5.0 Bacchanalia Bot/mikail#3202");
        EmbedUtils.setEmbedBuilder(
                () -> new EmbedBuilder()
                        .setColor(getRandomColor())
                        .setFooter("{Bacchanalia Bot}", null)
                        .setTimestamp(Instant.now()));

        try {
            logger.info("Booting");
            new JDABuilder(AccountType.BOT)
                    .setToken(Secrets.TOKEN)
                    .setGame(Game.playing("Valorant"))
                    .addEventListener(listener)
                    .build().awaitReady();
            logger.info("Running");
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Color getRandomColor() {
        float r = random.nextFloat();
        float g = random.nextFloat();
        float b = random.nextFloat();

        return new Color(r, g, b);
    }
    public static void main(String[] args) throws IOException {
        new Main();
    }

}
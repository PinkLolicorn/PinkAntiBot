package pl.daffit.pinkantibot;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.concurrent.TimeUnit;

public class PinkAntiBotListener implements Listener {

    private static final long SAME_NAME_LENGTH_COOOLDOWN = TimeUnit.SECONDS.toMillis(60);
    private static final int MAX_SAME_NAME_LENGTH_SERIES = 10;
    private static final String SAME_NAME_LENGTH_MESSAGE = ChatColor.translateAlternateColorCodes('&',
            "&cNie mozesz teraz wejsc na serwer. Sprobuj ponownie za chwile.");

    private int sameNameLengthCount = 0;
    private int lastNameLength = 0;
    private long cooldown = 0L;

    @EventHandler
    public void onLogin(LoginEvent event) {

        String nick = event.getConnection().getName();
        int nickLength = nick.length();

        // same name length
        if (nickLength == this.lastNameLength) {

            // increase counter
            this.sameNameLengthCount += 1;

            // when counter reached MAX_SAME_NAME_LENGTH_SERIES apply cooldown and reset
            if (this.sameNameLengthCount >= MAX_SAME_NAME_LENGTH_SERIES) {
                this.cooldown = System.currentTimeMillis() + SAME_NAME_LENGTH_COOOLDOWN;
                this.sameNameLengthCount = 0;
            }

            // if cooldown still lasts cancel event
            if (this.cooldown > System.currentTimeMillis()) {
                event.setCancelReason(TextComponent.fromLegacyText(SAME_NAME_LENGTH_MESSAGE));
                event.setCancelled(true);
            }

            return;
        }

        // name length changed, reset
        this.sameNameLengthCount = 0;
        this.lastNameLength = nickLength;
    }
}

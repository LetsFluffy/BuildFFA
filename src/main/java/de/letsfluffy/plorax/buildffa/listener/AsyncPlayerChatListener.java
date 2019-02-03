package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 12:42
 */
public class AsyncPlayerChatListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public AsyncPlayerChatListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(PloraxAPI.getSpecPlayers().containsKey(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(getBuildFFA().getPrefix() + "§cDu kannst im SpecModus nichts schreiben.");
        }
    }

}

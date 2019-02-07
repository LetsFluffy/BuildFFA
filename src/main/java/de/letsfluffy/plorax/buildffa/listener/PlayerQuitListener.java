package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 16:12
 */
public class PlayerQuitListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public PlayerQuitListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        if(getBuildFFA().getCombatLog().containsKey(event.getPlayer())) {
            getBuildFFA().getCombatLog().remove(event.getPlayer());
        }
        if(getBuildFFA().getOnlinePlayers().containsKey(event.getPlayer())) {
            getBuildFFA().getOnlinePlayers().remove(event.getPlayer());
        }
     }
}

package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 13:35
 */
public class EntityChangeBlockListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public EntityChangeBlockListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        event.setCancelled(true);
    }

}

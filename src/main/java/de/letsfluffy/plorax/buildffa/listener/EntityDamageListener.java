package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 13:41
 */
public class EntityDamageListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;


    public EntityDamageListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if((event.getCause().equals(EntityDamageEvent.DamageCause.PROJECTILE) || event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_ATTACK))) {
            event.setCancelled(false);
        } else if((event.getCause().equals(EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||
                event.getCause().equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION))) {
            event.setDamage(0);
        } else {
            event.setCancelled(true);
        }
        if(event.getEntity().getLocation().getY() >= getBuildFFA().getMapImporter().getMap().getSpawnHigh()) {
            event.setCancelled(true);
        }
    }
}

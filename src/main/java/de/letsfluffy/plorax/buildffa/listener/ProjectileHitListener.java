package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-11, 09:24
 */
public class ProjectileHitListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public ProjectileHitListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if(event.getEntity() instanceof EnderPearl) {
            if(getBuildFFA().getProjectiles().containsKey(event.getEntity().getShooter())) {
                getBuildFFA().getProjectiles().remove(event.getEntity().getShooter());
            }
        }
    }
}

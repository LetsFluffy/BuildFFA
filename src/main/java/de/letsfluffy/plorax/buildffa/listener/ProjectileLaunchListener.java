package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-10, 21:12
 */
public class ProjectileLaunchListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public ProjectileLaunchListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if(event.getEntity() instanceof EnderPearl) {
            getBuildFFA().getProjectiles().put((Player) event.getEntity().getShooter(), event.getEntity());
        }
    }
}

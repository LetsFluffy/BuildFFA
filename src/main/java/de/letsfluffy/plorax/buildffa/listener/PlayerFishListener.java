package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Fish;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.util.Vector;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 15:00
 */
public class PlayerFishListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public PlayerFishListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onPlayerFish(PlayerFishEvent event) {
        Player p = event.getPlayer();
        Fish h = event.getHook();
        if (p.getLocation().getY() < getBuildFFA().getMapImporter().getMap().getSpawnHigh()) {
            if (event.getState().equals(PlayerFishEvent.State.IN_GROUND) || event.getState().equals(PlayerFishEvent.State.FAILED_ATTEMPT)) {
                if (Bukkit.getWorld(event.getPlayer().getWorld().getName()).getBlockAt(h.getLocation().getBlockX(), h.getLocation().getBlockY() - 1, h.getLocation().getBlockZ()).getType()
                        != Material.STATIONARY_WATER && Bukkit.getWorld(event.getPlayer().getWorld().getName()).getBlockAt(h.getLocation().getBlockX(), h.getLocation().getBlockY() - 1, h.getLocation().getBlockZ()).getType() != Material.AIR) {
                    Location lc = p.getLocation();
                    Location to = event.getHook().getLocation();

                    lc.setY(lc.getY() + 0.5D);
                    p.teleport(lc);

                    double g = -0.08D;
                    double d = to.distance(lc);
                    double t = d;
                    double v_x = (1.0D + 0.70000000000000001D * t) * (to.getX() - lc.getX()) / t;
                    double v_y = (1.0D + 0.1D * t) * (to.getY() - lc.getY()) / t - 0.5D * g * t;
                    double v_z = (1.0D + 0.70000000000000001D * t) * (to.getZ() - lc.getZ()) / t;

                    Vector v = p.getVelocity();
                    v.setX(v_x);
                    v.setY(v_y);
                    v.setZ(v_z);
                    p.setVelocity(v);

                }

            }
        }
    }
}

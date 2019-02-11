package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 13:36
 */
public class EntityDamageByEntityListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public EntityDamageByEntityListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }



    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(event.getDamager() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player player = (Player) event.getEntity();
            if (player.getLocation().getY() >= getBuildFFA().getMapImporter().getMap().getSpawnHigh() &&
                    damager.getLocation().getY() >= getBuildFFA().getMapImporter().getMap().getSpawnHigh()) {
                event.setCancelled(true);
                return;
            }
            if (PloraxAPI.getSpecPlayers().containsKey(damager) || PloraxAPI.getSpecPlayers().containsKey(player)) {
                event.setCancelled(true);
                return;
            }
            if (getBuildFFA().getCombatLog().containsKey(player)) {
                getBuildFFA().getCombatLog().remove(player);
            }
            if (getBuildFFA().getCombatLog().containsKey(damager)) {
                getBuildFFA().getCombatLog().remove(damager);
            }
            getBuildFFA().getCombatLog().put(player, damager);
            getBuildFFA().getCombatLog().put(damager, player);
            Bukkit.getScheduler().runTaskLaterAsynchronously(getBuildFFA(), new Runnable() {
                @Override
                public void run() {
                    if (getBuildFFA().getCombatLog().containsKey(player)) {
                        getBuildFFA().getCombatLog().remove(player);
                    }
                    if (getBuildFFA().getCombatLog().containsKey(damager)) {
                        getBuildFFA().getCombatLog().remove(damager);
                    }
                }
            }, 20 * 8);
        } else if(event.getDamager() instanceof Projectile) {
            Player damager = (Player) ((Projectile) event.getDamager()).getShooter();
            Player player = (Player) event.getEntity();
            if (player.getLocation().getY() >= getBuildFFA().getMapImporter().getMap().getSpawnHigh() &&
                    damager.getLocation().getY() >= getBuildFFA().getMapImporter().getMap().getSpawnHigh()) {
                event.setCancelled(true);
                return;
            }
            if (PloraxAPI.getSpecPlayers().containsKey(damager) || PloraxAPI.getSpecPlayers().containsKey(player)) {
                event.setCancelled(true);
                return;
            }
            if (getBuildFFA().getCombatLog().containsKey(player)) {
                getBuildFFA().getCombatLog().remove(player);
            }
            if (getBuildFFA().getCombatLog().containsKey(damager)) {
                getBuildFFA().getCombatLog().remove(damager);
            }
            getBuildFFA().getCombatLog().put(player, damager);
            getBuildFFA().getCombatLog().put(damager, player);
            Bukkit.getScheduler().runTaskLaterAsynchronously(getBuildFFA(), new Runnable() {
                @Override
                public void run() {
                    if (getBuildFFA().getCombatLog().containsKey(player)) {
                        getBuildFFA().getCombatLog().remove(player);
                    }
                    if (getBuildFFA().getCombatLog().containsKey(damager)) {
                        getBuildFFA().getCombatLog().remove(damager);
                    }
                }
            }, 20 * 8);
        } else {
            event.setCancelled(true);
        }
    }
}

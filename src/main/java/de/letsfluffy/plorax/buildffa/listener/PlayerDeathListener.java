package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.ItemStackBuilder;
import de.letsfluffy.plorax.buildffa.utils.PacketScoreboard;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import net.plorax.api.StatsAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 14:31
 */
public class PlayerDeathListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public PlayerDeathListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();
        event.setDeathMessage(null);
        player.spigot().respawn();

        player.teleport(getBuildFFA().getMapImporter().getMap().getSpawn());

        event.setKeepInventory(true);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItem(4, ItemStackBuilder.getSpawnItems()[0]);
        player.getInventory().setItem(8, ItemStackBuilder.getSpawnItems()[1]);
        getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
            long[] l = new long[2];
            l[0] = 0;
            l[1] = 1;
            PloraxAPI.getStatsAPI().addStats(player.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA, l);
            PloraxAPI.getCoinAPI().subtractCoins(player.getUniqueId(), 1);
        });
        if (getBuildFFA().getCombatLog().containsKey(player)) {
            getBuildFFA().getCombatLog().remove(player);
        }
        if (getBuildFFA().getCombatLog().containsKey(killer)) {
            getBuildFFA().getCombatLog().remove(killer);
        }
        getBuildFFA().getKillstreak().remove(player);
        getBuildFFA().getKillstreak().put(player, 0);
        if (killer != null) {
            int kills = getBuildFFA().getKillstreak().get(killer);
            kills++;
            getBuildFFA().getKillstreak().remove(killer);
            getBuildFFA().getKillstreak().put(killer, kills);
            getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
                PloraxAPI.getCoinAPI().addCoins(killer.getUniqueId(), 5);
                long[] l = new long[2];
                l[0] = 1;
                l[1] = 0;
                PloraxAPI.getStatsAPI().addStats(killer.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA, l);
            });
            killer.sendMessage(getBuildFFA().getPrefix() + "§7Du hast " + player.getDisplayName() + " §7getötet.");
            player.sendMessage(getBuildFFA().getPrefix() + "§7Du wurdest von " + killer.getDisplayName() + " §7getötet.");
        } else {
            player.sendMessage(getBuildFFA().getPrefix() + "§7Du bist gestorben.");
        }
        PacketScoreboard.updateScoreboard(player);
    }

}

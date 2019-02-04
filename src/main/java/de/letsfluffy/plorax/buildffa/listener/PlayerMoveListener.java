package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.ItemStackBuilder;
import de.letsfluffy.plorax.buildffa.utils.PacketScoreboard;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import net.plorax.api.StatsAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 15:37
 */
public class PlayerMoveListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public PlayerMoveListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getLocation().getY() <= 0) {
            player.teleport(getBuildFFA().getMapImporter().getMap().getSpawn());
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

            if(getBuildFFA().getCombatLog().containsKey(player)) {
                Player killer = getBuildFFA().getCombatLog().get(player);
                getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
                    PloraxAPI.getCoinAPI().addCoins(killer.getUniqueId(), 5);
                    long[] l = new long[2];
                    l[0] = 1;
                    l[1] = 0;
                    PloraxAPI.getStatsAPI().addStats(killer.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA, l);
                });
                getBuildFFA().getCombatLog().remove(player);
                getBuildFFA().getKillstreak().remove(player);
                getBuildFFA().getKillstreak().put(player, 0);
                int kills = getBuildFFA().getKillstreak().get(killer);
                kills++;
                if((kills == 3) || ((kills % 5) == 0)) {
                    for(Player player1 : Bukkit.getOnlinePlayers()) {
                        player1.sendMessage(getBuildFFA().getPrefix() + killer.getDisplayName() + " §7hat eine §a§l" + kills + "§r §7Killstreak!");
                    }
                }
                getBuildFFA().getKillstreak().remove(killer);
                getBuildFFA().getKillstreak().put(killer, kills);
                killer.sendMessage(getBuildFFA().getPrefix() + "§7Du hast " + player.getDisplayName() + " §7getötet.");
                killer.setHealth(20);
                player.sendMessage(getBuildFFA().getPrefix() + "§7Du wurdest von " + killer.getDisplayName() + " §7getötet.");
            } else {
                player.sendMessage(getBuildFFA().getPrefix() + "§7Du bist gestorben.");
            }
        } else if(player.getLocation().getY() == getBuildFFA().getMapImporter().getMap().getSpawnHigh()) {
            player.getInventory().clear();
            player.getInventory().setHelmet(ItemStackBuilder.getHelmet());
            player.getInventory().setChestplate(ItemStackBuilder.getChestplate());
            player.getInventory().setLeggings(ItemStackBuilder.getLeggings());
            player.getInventory().setBoots(ItemStackBuilder.getBoots());
            for(int i = 0; i < getBuildFFA().getInventorys().get(player).length; i++) {
                player.getInventory().setItem(i, getBuildFFA().getInventorys().get(player)[i]);
            }
        }
        PacketScoreboard.updateScoreboard(player);
    }
}

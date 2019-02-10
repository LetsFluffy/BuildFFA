package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.GamePlayer;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import de.letsfluffy.plorax.buildffa.utils.PacketScoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 15:31
 */
public class PlayerJoinListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public PlayerJoinListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if(getBuildFFA().getGameManager().isJoinable()) {
            Player player = event.getPlayer();
            event.setJoinMessage(null);
            player.teleport(getBuildFFA().getMapImporter().getMap().getSpawn());
            player.getInventory().clear();
            player.getInventory().setArmorContents(null);
            player.getInventory().setItem(0, ItemStackBuilder.getSpawnItems()[0]);
            player.getInventory().setItem(4, ItemStackBuilder.getSpawnItems()[1]);
            player.getInventory().setItem(8, ItemStackBuilder.getSpawnItems()[2]);
            player.setHealth(20);
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(getBuildFFA().getPrefix() + "§a§lTeaming §r§7ist bis zu einer §a§lGröße §r§7von §a§l3er Teams §r§7erlaubt.");
            PacketScoreboard.updateScoreboard(player);
            for(Player player1 : Bukkit.getOnlinePlayers()) {
                player1.hidePlayer(player);
                player1.showPlayer(player);
            }
            player.getActivePotionEffects().forEach(potionEffect -> {
                player.removePotionEffect(potionEffect.getType());
            });
            getBuildFFA().getKillstreak().put(player, 0);
            if(getBuildFFA().getCurrentEvent() != null) {
                if (getBuildFFA().getCurrentEvent().equals(getBuildFFA().getEventRegistry().get("Power"))) {
                    player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3));
                }
            }
            getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
                if (!getBuildFFA().getStatsSQL().isExisting(player.getUniqueId())) {
                    getBuildFFA().getStatsSQL().insertUser(player.getUniqueId());
                }
                getBuildFFA().getOnlinePlayers().put(player, new GamePlayer(player));
            });
        } else {
            event.getPlayer().kickPlayer("§cServer is not ready! Please wait a short time");
        }
    }
}

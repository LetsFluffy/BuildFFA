package de.letsfluffy.plorax.buildffa.events;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.PacketScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.events
 * Class created: 2019-02-07, 17:49
 */
public class PowerEvent implements Event {
    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public void start() {
        BuildFFA.getBuildFFA().setCurrentEvent(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Das §a§l" + getName() + " §7wurde gestartet.");
            player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 3));
            PacketScoreboard.updateScoreboard(player);
        }
    }

    @Override
    public void stop() {
        BuildFFA.getBuildFFA().setCurrentEvent(null);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Das §a§l" + getName() + " §7wurde beendet.");
            player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
            PacketScoreboard.updateScoreboard(player);
        }
    }
}

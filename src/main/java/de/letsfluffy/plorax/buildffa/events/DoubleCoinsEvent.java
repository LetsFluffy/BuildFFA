package de.letsfluffy.plorax.buildffa.events;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.PacketScoreboard;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.events
 * Class created: 2019-02-06, 16:43
 */
@Getter
public class DoubleCoinsEvent implements Event {

    @Override
    public String getName() {
        return "DoubleCoins";
    }

    @Override
    public void start() {
        BuildFFA.getBuildFFA().setKillCoins(BuildFFA.getBuildFFA().getKillCoins() * 2);
        BuildFFA.getBuildFFA().setCurrentEvent(this);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Das §a§l" + getName() + " §7Event wurde gestartet.");
            PacketScoreboard.updateScoreboard(player);
        }

    }

    @Override
    public void stop() {
        BuildFFA.getBuildFFA().setKillCoins(5);
        BuildFFA.getBuildFFA().setCurrentEvent(null);
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Das §a§l" + getName() + " §7Event wurde beendet.");
            PacketScoreboard.updateScoreboard(player);
        }

    }
}

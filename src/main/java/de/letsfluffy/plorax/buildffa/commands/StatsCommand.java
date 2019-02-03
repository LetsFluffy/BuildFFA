package de.letsfluffy.plorax.buildffa.commands;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import net.plorax.api.StatsAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.commands
 * Class created: 2019-02-03, 16:22
 */
public class StatsCommand implements CommandExecutor {

    @Getter
    private final BuildFFA buildFFA;

    public StatsCommand(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
        getBuildFFA().getCommand("stats").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Long[] stats = PloraxAPI.getStatsAPI().getStats(player.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA);
            player.sendMessage(getBuildFFA().getPrefix() + "§7Deine §a§lStats");
            player.sendMessage(getBuildFFA().getPrefix() + "§7Kills§8: §a§l" + stats[0]);
            player.sendMessage(getBuildFFA().getPrefix() + "§7Deaths§8: §a§l" + stats[1]);
        }
        return true;
    }
}

package de.letsfluffy.plorax.buildffa.commands;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.commands
 * Class created: 2019-02-03, 16:14
 */
public class ForcemapCommand implements CommandExecutor {

    @Getter
    private final BuildFFA buildFFA;

    public ForcemapCommand(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
        getBuildFFA().getCommand("forcemap").setExecutor(this);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player) {
            Player player  = (Player) commandSender;
            if(player.hasPermission("BuildFFA.Forcemap")) {
                player.openInventory(getBuildFFA().getMapImporter().getForceMapInventory());
            } else {
                player.sendMessage(getBuildFFA().getPrefix() + "§cDu bist nicht dazu berechtigt.");
            }
        }
        return true;
    }
}

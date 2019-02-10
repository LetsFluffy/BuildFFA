package de.letsfluffy.plorax.buildffa.commands;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.events.Event;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.commands
 * Class created: 2019-02-06, 17:09
 */
public class EventCommand implements CommandExecutor {

    @Getter
    private final BuildFFA buildFFA;

    public EventCommand(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
        getBuildFFA().getCommand("event").setExecutor(this);
    }
    private String supportGroupManager = "LetsFluffy";

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if(player.hasPermission("BuildFFA.Events")) {
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("stop")) {
                        if(getBuildFFA().getCurrentEvent() != null) {
                            getBuildFFA().getCurrentEvent().stop();
                            player.sendMessage(getBuildFFA().getPrefix() + "§7Du hast das Event gestoppt.");
                        } else {
                            player.sendMessage(getBuildFFA().getPrefix() + "§cAktuell läuft kein Event.");
                        }
                    } else if(args[0].equalsIgnoreCase("list")) {
                        for(String name : getBuildFFA().getEventRegistry().keySet()) {
                            player.sendMessage(getBuildFFA().getPrefix() + "§7- §a§l" + name);
                        }
                    } else {
                        sendHelp(player);
                    }
                } else if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("start")) {
                        if(getBuildFFA().getCurrentEvent() == null) {
                            if(getBuildFFA().getEventRegistry().containsKey(args[1])) {
                                 getBuildFFA().getEventRegistry().get(args[1]).start();
                                 player.sendMessage(getBuildFFA().getPrefix() + "§7Du hast das Event gestartet.");
                            } else {
                                player.sendMessage(getBuildFFA().getPrefix() + "§cDieses Event existiert nicht.");
                            }
                        } else {
                            player.sendMessage(getBuildFFA().getPrefix() + "§cEs läuft bereit das §a§l" + getBuildFFA().getCurrentEvent().getName() + " §cEvent.");
                        }
                    } else {
                        sendHelp(player);
                    }
                } else {
                    sendHelp(player);
                }
            } else {
                player.sendMessage(getBuildFFA().getPrefix() + "§cDazu bist du nicht berechtigt.");
            }
        }
        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage(getBuildFFA().getPrefix() + "§a§lEvent Selbsthilfe §8- §7Diese Hilfe reicht nicht aus?");
        player.sendMessage(getBuildFFA().getPrefix() + "§7Für die §a§lEvent Selbsthilfegruppe §7wenden Sie sich an §a§l" + supportGroupManager);
        player.sendMessage(getBuildFFA().getPrefix() + "§a§l/event start <Eventname>");
        player.sendMessage(getBuildFFA().getPrefix() + "§a§l/event stop");
        player.sendMessage(getBuildFFA().getPrefix() + "§a§l/event list");
    }
}

package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 14:05
 */
public class InventoryCloseListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public InventoryCloseListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        if(inventory.getTitle().equalsIgnoreCase("§8» §aInventar sortieren")) {
            getBuildFFA().getStatsSQL().getExecutorService().execute( () -> {
            boolean allSlotsUsed = true;
            for(int i = 0; i < 8; i++) {
                if(inventory.getItem(i) == null) {
                    allSlotsUsed = false;
                    break;
                }
            }
            if(!allSlotsUsed) {
                player.sendMessage(getBuildFFA().getPrefix() + "§cDu musst alle Items in das Inventar packen.");
                return;
            } else {
                getBuildFFA().getStatsSQL().updateInventory(player.getUniqueId(), getBuildFFA().getOnlinePlayers().get(player).getSelectedKit(), inventory);
                player.sendMessage(getBuildFFA().getPrefix() +"§aDein Inventar wurde erfolgreich geupdatet.");
                getBuildFFA().getOnlinePlayers().get(player).setItemStacks(getBuildFFA().getStatsSQL().getInventoryItems(player.getUniqueId(),
                        getBuildFFA().getOnlinePlayers().get(player).getSelectedKit()));
            }
            });
        }
    }

}

package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
            boolean allSlotsUsed;
            int airSlots = 0;
            for(int i = 0; i < getBuildFFA().getOnlinePlayers().get(player).getSelectedKit().getDefaultItemsSorted().length; i++) {
                ItemStack itemStack = getBuildFFA().getOnlinePlayers().get(player).getSelectedKit().getDefaultItemsSorted()[i];
                if(itemStack.getTypeId() == 0) {
                    airSlots++;
                }
            }

            int airSlotsInInventory = 0;
            for(int i = 0; i < 8; i++) {
                if(inventory.getItem(i) == null) {
                    airSlotsInInventory++;
                }
            }
            if(airSlotsInInventory == airSlots) {
                allSlotsUsed = true;
            } else {
                allSlotsUsed = false;
            }

            if(!allSlotsUsed) {
                player.sendMessage(getBuildFFA().getPrefix() + "§cDu musst alle Items in das Inventar packen.");
                player.getInventory().clear();
                player.getInventory().setArmorContents(null);
                player.getInventory().setItem(0, ItemStackBuilder.getSpawnItems()[0]);
                player.getInventory().setItem(4, ItemStackBuilder.getSpawnItems()[1]);
                player.getInventory().setItem(8, ItemStackBuilder.getSpawnItems()[2]);
                return;
            } else {
                getBuildFFA().getStatsSQL().updateInventory(player.getUniqueId(), getBuildFFA().getOnlinePlayers().get(player).getSelectedKit(), inventory);
                player.sendMessage(getBuildFFA().getPrefix() +"§7Dein Inventar wurde erfolgreich geupdatet.");
                getBuildFFA().getOnlinePlayers().get(player).setItemStacks(getBuildFFA().getStatsSQL().getInventoryItems(player.getUniqueId(),
                        getBuildFFA().getOnlinePlayers().get(player).getSelectedKit()));
            }
            });
        }
    }

}

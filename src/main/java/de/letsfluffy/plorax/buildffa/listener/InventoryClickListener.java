package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.maps.MapImportData;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 13:45
 */
public class InventoryClickListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public InventoryClickListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack currentItem = event.getCurrentItem();
        if(event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null) {
            if(event.getClickedInventory().getTitle().equalsIgnoreCase("§8» §aForcemap")) {
                event.setCancelled(true);
                if(currentItem != null) {
                    int slot = event.getSlot();
                    if(getBuildFFA().getMapImporter().getForceMaps().containsKey(slot)) {
                        MapImportData mapImportData = getBuildFFA().getMapImporter().getForceMaps().get(slot);
                        if(getBuildFFA().getMapImporter().getMap() != mapImportData) {
                            getBuildFFA().getGameManager().startMapSwitch(11, mapImportData);
                            player.closeInventory();
                        } else {
                            player.sendMessage(getBuildFFA().getPrefix() + "§cDiese Map wird bereits genutzt!");
                        }
                    }
                }
            }
        }
    }
}

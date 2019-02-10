package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlocks;
import de.letsfluffy.plorax.buildffa.kits.Kit;
import de.letsfluffy.plorax.buildffa.maps.MapImportData;
import de.letsfluffy.plorax.buildffa.utils.GamePlayer;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        if(player.getLocation().getY() >= getBuildFFA().getMapImporter().getMap().getSpawnHigh()) {
            event.setCancelled(true);
            if (event.getClickedInventory() != null && event.getClickedInventory().getTitle() != null) {
                if (event.getClickedInventory().getTitle().equalsIgnoreCase("§8» §aForcemap")) {
                    event.setCancelled(true);
                    if (currentItem != null) {
                        int slot = event.getSlot();
                        if (getBuildFFA().getMapImporter().getForceMaps().containsKey(slot)) {
                            MapImportData mapImportData = getBuildFFA().getMapImporter().getForceMaps().get(slot);
                            if (getBuildFFA().getMapImporter().getMap() != mapImportData) {
                                getBuildFFA().getGameManager().startMapSwitch(11, mapImportData);
                                player.closeInventory();
                            } else {
                                player.sendMessage(getBuildFFA().getPrefix() + "§cDiese Map wird bereits genutzt!");
                            }
                        }
                    }
                } else if (event.getClickedInventory().getTitle().equalsIgnoreCase("§8» §aWähle eine Kategorie")) {
                    event.setCancelled(true);

                    GamePlayer gamePlayer = getBuildFFA().getOnlinePlayers().get(player);
                    if (event.getCurrentItem() != null) {
                        if (event.getSlot() == 11) {
                            Inventory inventory = getBuildFFA().getGameManager().createKitInventory();
                            for (int i = 0; i < getBuildFFA().getKitRegistry().size(); i++) {
                                ItemStack itemStack = inventory.getItem(i);
                                Kit kit = getBuildFFA().getKitRegistry().get(i);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                if (gamePlayer.ownKit(kit)) {
                                    itemMeta.setDisplayName(itemMeta.getDisplayName() + " §7(§eGekauft§7)");
                                    if (gamePlayer.getSelectedKit().equals(kit)) {
                                        itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                                        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                    }
                                } else {
                                    itemMeta.setDisplayName(itemMeta.getDisplayName() + " §7(§e" + kit.getPrice() + " Coins§7)");
                                }
                                itemStack.setItemMeta(itemMeta);
                                inventory.setItem(i, itemStack);
                            }
                            player.openInventory(inventory);
                        } else if (event.getSlot() == 15) {
                            Inventory inventory = getBuildFFA().getGameManager().createBuildBlockInventory();
                            for (int i = 0; i < inventory.getSize(); i++) {
                                ItemStack itemStack = inventory.getItem(i);
                                itemStack.setAmount(1);
                                BuildBlocks buildBlocks = getBuildFFA().getBuildBlockRegistry().get(i);
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                if (gamePlayer.getBuildBlocks().equals(buildBlocks)) {
                                    itemMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                                    itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                }
                                itemStack.setItemMeta(itemMeta);
                                inventory.setItem(i, itemStack);
                            }
                            player.openInventory(inventory);
                        }
                    }

                } else if (event.getClickedInventory().getTitle().equalsIgnoreCase("§8» §aWähle ein Kit aus")) {
                    event.setCancelled(true);
                    if (event.getCurrentItem() != null) {
                        getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
                            Kit kit = getBuildFFA().getKitRegistry().get(event.getSlot());
                            GamePlayer gamePlayer = getBuildFFA().getOnlinePlayers().get(player);
                            if (gamePlayer.ownKit(kit)) {
                                getBuildFFA().getOnlinePlayers().get(player).selectKit(kit);
                                player.closeInventory();
                            } else {
                                if (PloraxAPI.getCoinAPI().getCoins(player.getUniqueId()) >= kit.getPrice()) {
                                    PloraxAPI.getCoinAPI().subtractCoins(player.getUniqueId(), kit.getPrice());
                                    getBuildFFA().getOnlinePlayers().get(player).buyKit(kit);
                                    getBuildFFA().getOnlinePlayers().get(player).selectKit(kit);
                                    player.closeInventory();
                                } else {
                                    player.sendMessage(getBuildFFA().getPrefix() + "§cDu kannst dir dieses Kit nicht leisten.");
                                }
                            }
                        });
                    }
                } else if (event.getClickedInventory().getTitle().equalsIgnoreCase("§8» §aWähle eine Blockart aus")) {
                    event.setCancelled(true);
                    if (event.getCurrentItem() != null) {
                        getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {

                            BuildBlocks buildBlocks = getBuildFFA().getBuildBlockRegistry().get(event.getSlot());
                            System.out.println(buildBlocks.getId());
                            getBuildFFA().getOnlinePlayers().get(player).selectBlocks(buildBlocks);

                            player.closeInventory();
                        });
                    }
                } else if(event.getClickedInventory().getTitle().equalsIgnoreCase("§8» §aInventar sortieren")) {
                    event.setCancelled(false);
                }
            }
        }
    }
}

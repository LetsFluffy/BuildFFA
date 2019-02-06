package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import net.plorax.api.util.PloraxPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 15:03
 */
public class PlayerInteractListener implements Listener {

    //    x
    //  x x x
    //x x x x x
    //  - x -
    //    x

    @Getter
    private final BuildFFA buildFFA;

    public PlayerInteractListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(event.getItem() != null) {
            if(event.getItem().getType().equals(Material.CHEST)) {
                Inventory inventory = Bukkit.createInventory(null, 9, "§8» §aInventar sortieren");
                for(int i = 0; i < getBuildFFA().getInventorys().get(player).length; i++) {
                    ItemStack[] itemStacks = getBuildFFA().getInventorys().get(player);
                    inventory.setItem(i, itemStacks[i]);
                }
                player.openInventory(inventory);
            } else if(event.getItem().getType().equals(Material.SLIME_BALL)) {
                new PloraxPlayer(player.getUniqueId()).fallback();
            } else if(event.getItem().getType().equals(Material.BLAZE_ROD)) {
                Location location = player.getLocation();
                location.setY(location.getY() - 5);

                Location blockLocation = location.getBlock().getLocation();
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.add(1, 0, 0);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.add(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = location.getBlock().getLocation();

                blockLocation = blockLocation.add(1, 0, 0).subtract(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.add(1, 0, 0);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = location.getBlock().getLocation();

                blockLocation = blockLocation.subtract(1, 0, 0);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.add(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = location.getBlock().getLocation();

                blockLocation = blockLocation.add(1, 0, 0).subtract(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.subtract(1, 0, 0);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = location.getBlock().getLocation();

                blockLocation = blockLocation.add(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.add(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = location.getBlock().getLocation();

                blockLocation = blockLocation.subtract(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

                blockLocation = blockLocation.subtract(0, 0, 1);
                if(blockLocation.getBlock().getType().equals(Material.AIR)) {
                    blockLocation.getBlock().setType(Material.STAINED_GLASS);
                    blockLocation.getBlock().setData((byte) 14);
                    getBuildFFA().getPlacedBlocks().put(blockLocation.getBlock(), 8);
                }

            }
        } else if(event.getClickedBlock() != null) {
            if(event.getClickedBlock().getType().equals(Material.BED_BLOCK)) {
                event.setCancelled(true);
            }
        }
    }
}

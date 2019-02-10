package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlock;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlocks;
import de.letsfluffy.plorax.buildffa.utils.GamePlayer;
import lombok.Getter;
import net.plorax.api.util.PloraxPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
                for(int i = 0; i < getBuildFFA().getOnlinePlayers().get(player).getItemStacks().length; i++) {
                    ItemStack[] itemStacks = getBuildFFA().getOnlinePlayers().get(player).getItemStacks();
                    if(getBuildFFA().getIdsOfBlocks().contains(itemStacks[i].getTypeId())) {
                        GamePlayer gamePlayer = getBuildFFA().getOnlinePlayers().get(player);
                        inventory.setItem(i, gamePlayer.getBuildBlocks().getDefaultState());
                    } else {
                        inventory.setItem(i, itemStacks[i]);
                    }
                }
                player.openInventory(inventory);
            } else if(event.getItem().getType().equals(Material.SLIME_BALL)) {
                new PloraxPlayer(player.getUniqueId()).fallback();
            } else if(event.getItem().getType().equals(Material.REDSTONE_TORCH_ON)) {
                final Inventory inventory = Bukkit.createInventory(null, 27, "§8» §aWähle eine Kategorie");
                for(int i = 0; i < 27; i++) {
                    ItemStack itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
                    itemStack.getItemMeta().setDisplayName(" ");
                    inventory.setItem(i, itemStack);
                }
                ItemStack kits = new ItemStack(Material.STICK);
                ItemMeta kitsMeta = kits.getItemMeta();
                kitsMeta.setDisplayName("§8» §aWähle ein Kit");
                kits.setItemMeta(kitsMeta);
                ItemStack blocks = new ItemStack(Material.SANDSTONE);
                ItemMeta blocksMeta = blocks.getItemMeta();
                blocksMeta.setDisplayName("§8» §aWähle eine Blocksorte");
                blocks.setItemMeta(blocksMeta);
                inventory.setItem(11, kits);
                inventory.setItem(15, blocks);
                player.openInventory(inventory);
            } else if(event.getItem().getType().equals(Material.BLAZE_ROD)&& event.getClickedBlock() == null) {
                event.setCancelled(true);
                ItemStack itemStack = player.getInventory().getItemInHand();
                itemStack.setAmount(itemStack.getAmount()-1);
                if(itemStack.getAmount() == 0) {
                    player.getInventory().remove(player.getInventory().getItemInHand());
                } else {
                    player.getInventory().setItemInHand(itemStack);
                }
                Location location = player.getLocation();
                location.setY(location.getY() - 5);

                buildRescuePlatform(location);

            }
        } else if(event.getClickedBlock() != null) {
            if(event.getClickedBlock().getType().equals(Material.BED_BLOCK)) {
                event.setCancelled(true);
            }
        }
    }

    /*
        x
      x x x
    x x x x x
      x x x
        x

     */


    private void buildRescuePlatform(final Location location) {
        final Block block = location.subtract(0, 4, 0).getBlock();
        final Block block1 = block.getLocation().add(1, 0, 0).getBlock();
        final Block block2 = block.getLocation().subtract(1, 0, 0).getBlock();
        final Block block3 = block.getLocation().add(0, 0, 1).getBlock();
        final Block block4 = block.getLocation().subtract(0, 0, 1).getBlock();
        final Block block5 = block.getLocation().subtract(0, 0, 2).getBlock();
        final Block block6 = block.getLocation().add(0, 0, 2).getBlock();
        final Block block7 = block.getLocation().subtract(2, 0, 0).getBlock();
        final Block block8 = block.getLocation().add(2, 0, 0).getBlock();
        final Block block9 = block.getLocation().add(1, 0, 1).getBlock();
        final Block block10 = block.getLocation().subtract(1, 0, 1).getBlock();
        final Block block11 = block.getLocation().add(1, 0, -1).getBlock();
        final Block block12 = block.getLocation().add(-1, 0, 1).getBlock();

        if(block.getType().equals(Material.AIR))
            block.setType(Material.HAY_BLOCK);
        if(block1.getType().equals(Material.AIR))
            block1.setType(Material.HAY_BLOCK);
        if(block2.getType().equals(Material.AIR))
            block2.setType(Material.HAY_BLOCK);
        if(block3.getType().equals(Material.AIR))
            block3.setType(Material.HAY_BLOCK);
        if(block4.getType().equals(Material.AIR))
            block4.setType(Material.HAY_BLOCK);
        if(block5.getType().equals(Material.AIR))
            block5.setType(Material.HAY_BLOCK);
        if(block6.getType().equals(Material.AIR))
            block6.setType(Material.HAY_BLOCK);
        if(block7.getType().equals(Material.AIR))
            block7.setType(Material.HAY_BLOCK);
        if(block8.getType().equals(Material.AIR))
            block8.setType(Material.HAY_BLOCK);
        if(block9.getType().equals(Material.AIR))
            block9.setType(Material.HAY_BLOCK);
        if(block10.getType().equals(Material.AIR))
            block10.setType(Material.HAY_BLOCK);
        if(block11.getType().equals(Material.AIR))
            block11.setType(Material.HAY_BLOCK);
        if(block12.getType().equals(Material.AIR))
            block12.setType(Material.HAY_BLOCK);

        BuildBlock buildBlock = new BuildBlock(block, null, false);
        BuildBlock buildBlock1 = new BuildBlock(block1, null, false);
        BuildBlock buildBlock2 = new BuildBlock(block2, null, false);
        BuildBlock buildBlock3 = new BuildBlock(block3, null, false);
        BuildBlock buildBlock4 = new BuildBlock(block4, null, false);
        BuildBlock buildBlock5 = new BuildBlock(block5, null, false);
        BuildBlock buildBlock6 = new BuildBlock(block6, null, false);
        BuildBlock buildBlock7 = new BuildBlock(block7, null, false);
        BuildBlock buildBlock8 = new BuildBlock(block8, null, false);
        BuildBlock buildBlock9 = new BuildBlock(block9, null, false);
        BuildBlock buildBlock10 = new BuildBlock(block10, null, false);
        BuildBlock buildBlock11 = new BuildBlock(block11, null, false);
        BuildBlock buildBlock12 = new BuildBlock(block12, null, false);

        getBuildFFA().getPlacedBlocks().put(block.getLocation(), buildBlock);
        getBuildFFA().getPlacedBlocks().put(block1.getLocation(), buildBlock1);
        getBuildFFA().getPlacedBlocks().put(block2.getLocation(), buildBlock2);
        getBuildFFA().getPlacedBlocks().put(block3.getLocation(), buildBlock3);
        getBuildFFA().getPlacedBlocks().put(block4.getLocation(), buildBlock4);
        getBuildFFA().getPlacedBlocks().put(block5.getLocation(), buildBlock5);
        getBuildFFA().getPlacedBlocks().put(block6.getLocation(), buildBlock6);
        getBuildFFA().getPlacedBlocks().put(block7.getLocation(), buildBlock7);
        getBuildFFA().getPlacedBlocks().put(block8.getLocation(), buildBlock8);
        getBuildFFA().getPlacedBlocks().put(block9.getLocation(), buildBlock9);
        getBuildFFA().getPlacedBlocks().put(block10.getLocation(), buildBlock10);
        getBuildFFA().getPlacedBlocks().put(block11.getLocation(), buildBlock11);
        getBuildFFA().getPlacedBlocks().put(block12.getLocation(), buildBlock12);

    }
}

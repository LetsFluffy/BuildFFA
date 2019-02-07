package de.letsfluffy.plorax.buildffa.buildblocks;

import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.buildblocks
 * Class created: 2019-02-07, 14:16
 */
public class SandstoneBlocks implements BuildBlocks {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public ItemStack getDefaultState() {
        return ItemStackBuilder.getBuildBlock(Material.SANDSTONE, 64, 2, "Sandstein");
    }

    @Override
    public ItemStack getFirstState() {
        return ItemStackBuilder.getBuildBlock(Material.SANDSTONE, 1, 1, "Sandstein");
    }

    @Override
    public ItemStack getSecondState() {
        return ItemStackBuilder.getBuildBlock(Material.SANDSTONE, 1, 0, "Sandstein");
    }

    @Override
    public ItemStack getThirdState() {
        return ItemStackBuilder.getBuildBlock(Material.RED_SANDSTONE, 1, 0, "Sandstein");
    }
}

package de.letsfluffy.plorax.buildffa.buildblocks;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.buildblocks
 * Class created: 2019-02-07, 14:27
 */
public class DirtBlocks implements BuildBlocks {

    @Override
    public int getId() {
        return 3;
    }

    @Override
    public ItemStack getDefaultState() {
        return ItemStackBuilder.getBuildBlock(Material.DIRT, 64, 0, "Erde");
    }

    @Override
    public ItemStack getFirstState() {
        return ItemStackBuilder.getBuildBlock(Material.WOOD, 1, 1, "Erde");
    }

    @Override
    public ItemStack getSecondState() {
        return ItemStackBuilder.getBuildBlock(Material.WOOD, 1, 5, "Erde");
    }

    @Override
    public ItemStack getThirdState() {
        return ItemStackBuilder.getBuildBlock(Material.STAINED_CLAY, 1, 15, "Erde");
    }
}

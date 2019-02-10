package de.letsfluffy.plorax.buildffa.buildblocks;

import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.buildblocks
 * Class created: 2019-02-07, 14:33
 */
public class QuarzBlocks implements BuildBlocks {
    @Override
    public int getId() {
        return 5;
    }

    @Override
    public ItemStack getDefaultState() {
        return ItemStackBuilder.getBuildBlock(Material.QUARTZ_BLOCK, 64, 2, "Quarz");
    }

    @Override
    public ItemStack getFirstState() {
        return ItemStackBuilder.getBuildBlock(Material.WOOL, 1, 0, "Quarz");
    }

    @Override
    public ItemStack getSecondState() {
        return ItemStackBuilder.getBuildBlock(Material.IRON_BLOCK, 1, 0, "Quarz");
    }

    @Override
    public ItemStack getThirdState() {
        return ItemStackBuilder.getBuildBlock(Material.STONE, 1 , 3, "Quarz");
    }
}

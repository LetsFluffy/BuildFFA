package de.letsfluffy.plorax.buildffa.buildblocks;

import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.buildblocks
 * Class created: 2019-02-07, 14:25
 */
public class StoneBlocks implements BuildBlocks {
    @Override
    public int getId() {
        return 2;
    }

    @Override
    public ItemStack getDefaultState() {
        return ItemStackBuilder.getBuildBlock(Material.STONE, 64, 6, "Stein");
    }

    @Override
    public ItemStack getFirstState() {
        return ItemStackBuilder.getBuildBlock(Material.STONE, 1, 5, "Stein");
    }

    @Override
    public ItemStack getSecondState() {
        return ItemStackBuilder.getBuildBlock(Material.STONE, 1, 0, "Stein");
    }

    @Override
    public ItemStack getThirdState() {
        return ItemStackBuilder.getBuildBlock(Material.COBBLESTONE, 1, 0, "Stein");
    }
}

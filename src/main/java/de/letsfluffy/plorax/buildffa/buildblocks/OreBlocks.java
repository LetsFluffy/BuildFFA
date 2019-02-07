package de.letsfluffy.plorax.buildffa.buildblocks;

import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.buildblocks
 * Class created: 2019-02-07, 14:38
 */
public class OreBlocks implements BuildBlocks {
    @Override
    public int getId() {
        return 7;
    }

    @Override
    public ItemStack getDefaultState() {
        return ItemStackBuilder.getBuildBlock(Material.EMERALD_ORE, 64, 0, "Emeralderz");
    }

    @Override
    public ItemStack getFirstState() {
        return ItemStackBuilder.getBuildBlock(Material.DIAMOND_ORE, 1, 0, "Emeralderz");
    }

    @Override
    public ItemStack getSecondState() {
        return ItemStackBuilder.getBuildBlock(Material.LAPIS_ORE, 1, 0, "Emeralderz");
    }

    @Override
    public ItemStack getThirdState() {
        return ItemStackBuilder.getBuildBlock(Material.REDSTONE_ORE, 1, 0, "Emeralderz");
    }
}

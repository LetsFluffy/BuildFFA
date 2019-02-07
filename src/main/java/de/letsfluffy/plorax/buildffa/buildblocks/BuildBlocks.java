package de.letsfluffy.plorax.buildffa.buildblocks;

import org.bukkit.inventory.ItemStack;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa
 * Class created: 2019-02-07, 14:13
 */
public interface BuildBlocks {

    int getId();

    ItemStack getDefaultState();

    ItemStack getFirstState();

    ItemStack getSecondState();

    ItemStack getThirdState();

}

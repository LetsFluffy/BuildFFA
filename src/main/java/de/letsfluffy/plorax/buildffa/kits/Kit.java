package de.letsfluffy.plorax.buildffa.kits;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.kits
 * Class created: 2019-02-06, 20:15
 */
public interface Kit {

    int getId();

    int getPrice();

    ItemStack getIcon();

    ItemStack[] getDefaultItemsSorted();

    ItemStack[] buildItems(ItemStack[] itemStacks);

    ItemStack[] getArmorContents();

}

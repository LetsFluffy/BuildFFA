package de.letsfluffy.plorax.buildffa.kits;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.kits
 * Class created: 2019-02-11, 14:11
 */
public class BowKit extends Kit {


    public BowKit() {
        super(2500, "Bogen Kit", Material.BOW);
    }

    @Override
    public ItemStack[] getDefaultItemsSorted() {
        ItemStack[] itemStacks = new ItemStack[9];
        ItemMeta[] itemMetas = new ItemMeta[9];

        itemStacks[0] = ItemStackBuilder.buildIronSword(2);

        itemStacks[1] = new ItemStack(Material.BOW);
        itemMetas[1] = itemStacks[1].getItemMeta();
        itemMetas[1].setDisplayName("§aBogen");
        itemMetas[1].addEnchant(Enchantment.ARROW_DAMAGE, 1, true);
        itemMetas[1].spigot().setUnbreakable(true);
        itemMetas[1].addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStacks[1].setItemMeta(itemMetas[1]);

        itemStacks[2] = new ItemStack(Material.ARROW, 16);
        itemMetas[2] = itemStacks[2].getItemMeta();
        itemMetas[2].setDisplayName("§aPfeil");
        itemStacks[2].setItemMeta(itemMetas[2]);

        itemStacks[3] = ItemStackBuilder.buildCobweb(24);

        itemStacks[4] = ItemStackBuilder.buildRescuePlatform(4);

        itemStacks[5] = new ItemStack(Material.SANDSTONE, 64);
        itemMetas[5] = itemStacks[5].getItemMeta();
        itemMetas[5].setDisplayName("§aSandstein");
        itemStacks[5].setItemMeta(itemMetas[5]);

        itemStacks[6] = new ItemStack(Material.SANDSTONE, 64);
        itemMetas[6] = itemStacks[6].getItemMeta();
        itemMetas[6].setDisplayName("§aSandstein");
        itemStacks[6].setItemMeta(itemMetas[6]);

        itemStacks[7] = new ItemStack(Material.SANDSTONE, 64);
        itemMetas[7] = itemStacks[7].getItemMeta();
        itemMetas[7].setDisplayName("§aSandstein");
        itemStacks[7].setItemMeta(itemMetas[7]);

        itemStacks[8] = new ItemStack(Material.SANDSTONE, 64);
        itemMetas[8] = itemStacks[8].getItemMeta();
        itemMetas[8].setDisplayName("§aSandstein");
        itemStacks[8].setItemMeta(itemMetas[8]);

        return itemStacks;
    }

}

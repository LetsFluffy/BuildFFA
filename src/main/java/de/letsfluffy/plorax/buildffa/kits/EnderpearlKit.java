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
 * Class created: 2019-02-11, 14:16
 */
public class EnderpearlKit extends Kit {

    public EnderpearlKit() {
        super(2500, "Enderperlen Kit", Material.ENDER_PEARL);
    }


    @Override
    public ItemStack[] getDefaultItemsSorted() {
        ItemStack[] itemStacks = new ItemStack[9];
        ItemMeta[] itemMetas = new ItemMeta[9];

        itemStacks[0] = ItemStackBuilder.buildIronSword(2);

        itemStacks[1] = new ItemStack(Material.ENDER_PEARL, 3);
        itemMetas[1] = itemStacks[1].getItemMeta();
        itemMetas[1].setDisplayName("§aEnderperle");
        itemStacks[1].setItemMeta(itemMetas[1]);

        itemStacks[2] = new ItemStack(Material.AIR);

        itemStacks[3] = ItemStackBuilder.buildCobweb(16);

        itemStacks[4] = ItemStackBuilder.buildRescuePlatform(2);

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

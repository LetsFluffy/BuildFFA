package de.letsfluffy.plorax.buildffa.kits;

import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.kits
 * Class created: 2019-02-06, 20:19
 */
public class KnockKit implements Kit {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        itemStack.getItemMeta().setDisplayName("§8» §aKnockKit");
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < getDefaultItemsSorted().length; i++) {
            ItemStack itemStack1 = getDefaultItemsSorted()[i];
            lore.add("§8» §a" + itemStack1.getAmount() + "§7x §a" + itemStack1.getItemMeta().getDisplayName());
        }
        itemStack.getItemMeta().setLore(lore);
        return itemStack;
    }

    @Override
    public ItemStack[] getDefaultItemsSorted() {
        ItemStack[] itemStacks = new ItemStack[8];

        return itemStacks;
    }

    @Override
    public ItemStack[] buildItems(ItemStack[] itemStacks) {
        for(int i = 0; i < itemStacks.length; i++) {
            ItemStack itemStack = itemStacks[i];
            if(itemStack.getType().equals(Material.STICK)) {
                Enchantment[] enchantments = {Enchantment.KNOCKBACK};
                int[] level = {2};
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, "§aSchlagstock", enchantments, level);
            }
        }
        return itemStacks;
    }

    @Override
    public ItemStack[] getArmorContents() {
        return new ItemStack[0];
    }
}

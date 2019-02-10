package de.letsfluffy.plorax.buildffa.kits;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
        return 1;
    }

    @Override
    public int getPrice() {
        return 2500;
    }

    @Override
    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§8» §aKnock Kit");
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < getDefaultItemsSorted().length; i++) {
            ItemStack itemStack1 = getDefaultItemsSorted()[i];
            ItemMeta itemMeta1 = itemStack1.getItemMeta();
            if(BuildFFA.getBuildFFA().getIdsOfBlocks().contains(itemStack1.getTypeId())) {
                lore.add("§8» §a" + itemStack1.getAmount() + "§7x §aBlöcke");
            } else {
                lore.add("§8» §a" + itemStack1.getAmount() + "§7x §a" + itemMeta1.getDisplayName());
            }
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public ItemStack[] getDefaultItemsSorted() {
        ItemStack[] itemStacks = new ItemStack[9];
        ItemMeta[] itemMetas = new ItemMeta[9];

        itemStacks[0] = new ItemStack(Material.STICK);
        itemMetas[0] = itemStacks[0].getItemMeta();
        itemMetas[0].setDisplayName("§aSchlagstock");
        itemMetas[0].addEnchant(Enchantment.KNOCKBACK, 4, true);
        itemStacks[0].setItemMeta(itemMetas[0]);

        itemStacks[1] = new ItemStack(Material.ENDER_PEARL, 3);
        itemMetas[1] = itemStacks[1].getItemMeta();
        itemMetas[1].setDisplayName("§aEnderperle");
        itemStacks[1].setItemMeta(itemMetas[1]);

        itemStacks[2] = new ItemStack(Material.FISHING_ROD);
        itemMetas[2] = itemStacks[2].getItemMeta();
        itemMetas[2].setDisplayName("§aAngel");
        itemStacks[2].setItemMeta(itemMetas[2]);

        itemStacks[3] = new ItemStack(Material.BLAZE_ROD);
        itemMetas[3] = itemStacks[3].getItemMeta();
        itemMetas[3].setDisplayName("§aRettungsplattform");
        itemStacks[3].setItemMeta(itemMetas[3]);

        itemStacks[4] = new ItemStack(Material.WEB, 16);
        itemMetas[4] = itemStacks[4].getItemMeta();
        itemMetas[4].setDisplayName("§aSpinnenweben");
        itemStacks[4].setItemMeta(itemMetas[4]);

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

    @Override
    public ItemStack[] buildItems(ItemStack[] itemStacks) {
        for(int i = 0; i < itemStacks.length; i++) {
            ItemStack itemStack = itemStacks[i];
            if(itemStack.getType().equals(Material.STICK)) {
                Enchantment[] enchantments = {Enchantment.KNOCKBACK};
                int[] level = {4};
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, "§aSchlagstock", enchantments, level);
            } else if(itemStack.getType().equals(Material.ENDER_PEARL)) {
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack,3,  "§aEnderperle");
            } else if(itemStack.getType().equals(Material.FISHING_ROD)) {
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, "§aAngel");
            } else if(BuildFFA.getBuildFFA().getIdsOfBlocks().contains(itemStack.getTypeId())) {
                itemStacks[i] = ItemStackBuilder.getBuildBlock(Material.SANDSTONE, 64, 0, "§aSandstein");
            } else if(itemStack.getType().equals(Material.BLAZE_ROD)) {
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, "§aRettungsplattform");
            } else if(itemStack.getType().equals(Material.WEB)){
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, 16, "§aSpinnenweben");
            }
        }
        return itemStacks;
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armorContents = new ItemStack[4];
        armorContents[3] = ItemStackBuilder.getHelmet(Material.CHAINMAIL_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[2] = ItemStackBuilder.getChestplate(Material.CHAINMAIL_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[1] = ItemStackBuilder.getLeggings(Material.CHAINMAIL_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[0] = ItemStackBuilder.getBoots(Material.CHAINMAIL_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        return armorContents;
    }
}

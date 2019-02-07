package de.letsfluffy.plorax.buildffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa
 * Class created: 2019-02-03, 14:15
 */
public class ItemStackBuilder {

    public static ItemStack modifyItemStack(ItemStack itemStack, String displayName, Enchantment[] enchantments, int[] level) {
        itemStack.getItemMeta().setDisplayName(displayName);
        itemStack.getItemMeta().spigot().setUnbreakable(true);
        for(int i = 0; i < enchantments.length; i++) {
            itemStack.getItemMeta().addEnchant(enchantments[i], level[i], false);
        }
        return itemStack;
    }

    public static ItemStack getBuildBlock(Material material, int amount, int subid, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount, (short) subid);
        itemStack.getItemMeta().setDisplayName("§a" + displayName);
        return itemStack;
    }

    public static ItemStack getItem(Material material, String displayName, Enchantment[] enchantments, int[] level) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.getItemMeta().setDisplayName(displayName);
        itemStack.getItemMeta().spigot().setUnbreakable(true);
        for(int i = 0; i < enchantments.length; i++) {
            itemStack.getItemMeta().addEnchant(enchantments[i], level[i], false);
        }
        return itemStack;
    }

    public static ItemStack getHelmet(Material material) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.getItemMeta().setDisplayName("§aHelm");
        return itemStack;
    }

    public static ItemStack getChestplate(Material material) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.getItemMeta().setDisplayName("§aBrustplatte");
        return itemStack;
    }

    public static ItemStack getLeggings(Material material) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.getItemMeta().setDisplayName("§aHose");
        return itemStack;
    }

    public static ItemStack getBoots(Material material) {
        ItemStack itemStack = new ItemStack(material);
        itemStack.getItemMeta().setDisplayName("§aSchuhe");
        return itemStack;
    }

    public static ItemStack getHelmet(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getHelmet(material);
        itemStack.getItemMeta().addEnchant(enchantment, level, false);
        return itemStack;
    }

    public static ItemStack getChestplate(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getChestplate(material);
        itemStack.getItemMeta().addEnchant(enchantment, level, false);
        return itemStack;
    }

    public static ItemStack getLeggings(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getLeggings(material);
        itemStack.getItemMeta().addEnchant(enchantment, level, false);
        return itemStack;
    }

    public static ItemStack getBoots(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getBoots(material);
        itemStack.getItemMeta().addEnchant(enchantment, level, false);
        return itemStack;
    }

    public static ItemStack[] getSpawnItems() {
        ItemStack[] itemStacks = new ItemStack[3];
        itemStacks[0] = new ItemStack(Material.REDSTONE_TORCH_ON);
        itemStacks[0].getItemMeta().setDisplayName("§aKits und Blöcke");
        itemStacks[1] = new ItemStack(Material.CHEST);
        itemStacks[1].getItemMeta().setDisplayName("§aInventar sortieren");
        itemStacks[2] = new ItemStack(Material.SLIME_BALL);
        itemStacks[2].getItemMeta().setDisplayName("§aZurück zur Lobby");
        return itemStacks;
    }




}

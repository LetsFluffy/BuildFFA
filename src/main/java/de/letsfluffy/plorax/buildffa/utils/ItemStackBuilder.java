package de.letsfluffy.plorax.buildffa.utils;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
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
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.spigot().setUnbreakable(true);
        for(int i = 0; i < enchantments.length; i++) {
            itemMeta.addEnchant(enchantments[i], level[i], true);
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack modifyItemStack(ItemStack itemStack, String displayName) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.setDisplayName(displayName);
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack modifyItemStack(ItemStack itemStack, int amount, String displayName) {
        itemStack.setAmount(amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.spigot().setUnbreakable(true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getBuildBlock(Material material, int amount, int subid, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount, (short) subid);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§a" + displayName);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getItem(Material material, String displayName, Enchantment[] enchantments, int[] level) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.spigot().setUnbreakable(true);
        for(int i = 0; i < enchantments.length; i++) {
            itemMeta.addEnchant(enchantments[i], level[i], true);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getHelmet(Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.setDisplayName("§aHelm");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getChestplate(Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.setDisplayName("§aBrustplatte");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getLeggings(Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.setDisplayName("§aHose");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getBoots(Material material) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.spigot().setUnbreakable(true);
        itemMeta.setDisplayName("§aSchuhe");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getHelmet(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getHelmet(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getChestplate(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getChestplate(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getLeggings(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getLeggings(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack getBoots(Material material, Enchantment enchantment, int level) {
        ItemStack itemStack = getBoots(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, true);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack[] getSpawnItems() {
        ItemStack[] itemStacks = new ItemStack[3];
        ItemMeta[] itemMetas = new ItemMeta[3];

        itemStacks[0] = new ItemStack(Material.REDSTONE_TORCH_ON);
        itemMetas[0] = itemStacks[0].getItemMeta();
        itemMetas[0].setDisplayName("§aKits und Blöcke");
        itemStacks[0].setItemMeta(itemMetas[0]);

        itemStacks[1] = new ItemStack(Material.CHEST);
        itemMetas[1] = itemStacks[1].getItemMeta();
        itemMetas[1].setDisplayName("§aInventar sortieren");
        itemStacks[1].setItemMeta(itemMetas[1]);

        itemStacks[2] = new ItemStack(Material.SLIME_BALL);
        itemMetas[2] = itemStacks[2].getItemMeta();
        itemMetas[2].setDisplayName("§aZurück zur Lobby");
        itemStacks[2].setItemMeta(itemMetas[2]);

        return itemStacks;
    }




}

package de.letsfluffy.plorax.buildffa;

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

    public static ItemStack getSword() {
        ItemStack itemStack = new ItemStack(Material.GOLD_SWORD);
        itemStack.getItemMeta().addEnchant(Enchantment.DAMAGE_ALL, 2, false);
        itemStack.getItemMeta().spigot().setUnbreakable(true);
        itemStack.getItemMeta().setDisplayName("§aSchwert");
        return itemStack;
    }

    public static ItemStack getStick() {
        ItemStack itemStack = new ItemStack(Material.STICK);
        itemStack.getItemMeta().addEnchant(Enchantment.KNOCKBACK, 1, false);
        itemStack.getItemMeta().spigot().setUnbreakable(true);
        itemStack.getItemMeta().setDisplayName("§aStock");
        return itemStack;
    }

    public static ItemStack getRod() {
        ItemStack itemStack =  new ItemStack(Material.FISHING_ROD);
        itemStack.getItemMeta().setDisplayName("§aAngel");
        return itemStack;
    }

    public static ItemStack getCobweb() {
        ItemStack itemStack = new ItemStack(Material.WEB, 6);
        itemStack.getItemMeta().setDisplayName("§aSpinnenweben");
        return itemStack;
    }

    public static ItemStack getRescuePlatform() {
        ItemStack itemStack = new ItemStack(Material.BLAZE_ROD);
        itemStack.getItemMeta().setDisplayName("§aRettungsplattform");
        return itemStack;
    }

    public static ItemStack getSandstoneBlocks() {
        ItemStack itemStack = new ItemStack(Material.SANDSTONE, 64);
        itemStack.getItemMeta().setDisplayName("§aSandstein");
        return itemStack;
    }

    public static ItemStack getEnderpearl() {
        ItemStack itemStack = new ItemStack(Material.ENDER_PEARL);
        itemStack.getItemMeta().setDisplayName("§aEnderperle");
        return itemStack;
    }

    public static ItemStack[] getSpawnItems() {
        ItemStack[] itemStacks = new ItemStack[2];
        itemStacks[0] = new ItemStack(Material.CHEST);
        itemStacks[0].getItemMeta().setDisplayName("§aInventar sortieren");
        itemStacks[1] = new ItemStack(Material.SLIME_BALL);
        itemStacks[1].getItemMeta().setDisplayName("§cZurück zur Lobby");
        return itemStacks;
    }

    public static ItemStack getHelmet() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HELMET);
        itemStack.getItemMeta().setDisplayName("§aHelm");
        itemStack.getItemMeta().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, false);
        return itemStack;
    }

    public static ItemStack getChestplate() {
        ItemStack itemStack = new ItemStack(Material.CHAINMAIL_CHESTPLATE);
        itemStack.getItemMeta().setDisplayName("§aBrustplatte");
        itemStack.getItemMeta().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, false);
        return itemStack;
    }

    public static ItemStack getLeggings() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_LEGGINGS);
        itemStack.getItemMeta().setDisplayName("§aHose");
        itemStack.getItemMeta().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, false);
        return itemStack;
    }

    public static ItemStack getBoots() {
        ItemStack itemStack = new ItemStack(Material.LEATHER_BOOTS);
        itemStack.getItemMeta().setDisplayName("§aSchuhe");
        itemStack.getItemMeta().addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 10, false);
        return itemStack;
    }



}

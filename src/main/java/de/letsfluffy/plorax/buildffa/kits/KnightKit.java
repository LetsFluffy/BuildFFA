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
 * Class created: 2019-02-10, 12:06
 */
public class KnightKit implements Kit {
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
        ItemStack itemStack = new ItemStack(Material.STONE_SWORD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§8» §aRitter Kit");
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < getDefaultItemsSorted().length; i++) {
            ItemStack itemStack1 = getDefaultItemsSorted()[i];
            ItemMeta itemMeta1 = itemStack1.getItemMeta();
            lore.add("§8» §a" + itemStack1.getAmount() + "§7x §a" + itemMeta1.getDisplayName());
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    //Eisenschwert Schärfe 2
    //1x Enderperle
    //Angel
    //2x Rettungsplattform
    //8x Spinnenweben
    //4 Stacks Blöcke

    @Override
    public ItemStack[] getDefaultItemsSorted() {
        ItemStack[] itemStacks = new ItemStack[9];
        ItemMeta[] itemMetas = new ItemMeta[9];

        itemStacks[0] = new ItemStack(Material.IRON_SWORD);
        itemMetas[0] = itemStacks[0].getItemMeta();
        itemMetas[0].setDisplayName("§aSchwert");
        itemMetas[0].addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        itemStacks[0].setItemMeta(itemMetas[0]);

        itemStacks[1] = new ItemStack(Material.ENDER_PEARL, 1);
        itemMetas[1] = itemStacks[1].getItemMeta();
        itemMetas[1].setDisplayName("§aEine Perle der Natur");
        itemStacks[1].setItemMeta(itemMetas[1]);

        itemStacks[2] = new ItemStack(Material.FISHING_ROD);
        itemMetas[2] = itemStacks[2].getItemMeta();
        itemMetas[2].setDisplayName("§aAngel");
        itemStacks[2].setItemMeta(itemMetas[2]);

        itemStacks[3] = new ItemStack(Material.BLAZE_ROD, 2);
        itemMetas[3] = itemStacks[3].getItemMeta();
        itemMetas[3].setDisplayName("§aRettungsplattform");
        itemStacks[3].setItemMeta(itemMetas[3]);

        itemStacks[4] = new ItemStack(Material.WEB, 8);
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
            if(itemStack.getType().equals(Material.IRON_SWORD)) {
                Enchantment[] enchantments = {Enchantment.DAMAGE_ALL};
                int[] level = {2};
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, "§aSchwert", enchantments, level);
            } else if(itemStack.getType().equals(Material.ENDER_PEARL)) {
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack,  "§aEine Perle der Natur");
            } else if(itemStack.getType().equals(Material.FISHING_ROD)) {
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, "§aAngel");
            } else if(BuildFFA.getBuildFFA().getIdsOfBlocks().contains(itemStack.getTypeId())) {
                itemStacks[i] = ItemStackBuilder.getBuildBlock(Material.SANDSTONE, 64, 0, "§aSandstein");
            } else if(itemStack.getType().equals(Material.BLAZE_ROD)) {
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, 2,"§aRettungsplattform");
            } else if(itemStack.getType().equals(Material.WEB)){
                itemStacks[i] = ItemStackBuilder.modifyItemStack(itemStack, 8, "§aSpinnenweben");
            }
        }
        return itemStacks;
    }

    @Override
    public ItemStack[] getArmorContents() {
        ItemStack[] armorContents = new ItemStack[4];
        armorContents[3] = ItemStackBuilder.getHelmet(Material.LEATHER_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[2] = ItemStackBuilder.getChestplate(Material.CHAINMAIL_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[1] = ItemStackBuilder.getLeggings(Material.LEATHER_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[0] = ItemStackBuilder.getBoots(Material.LEATHER_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        return armorContents;
    }
}
package de.letsfluffy.plorax.buildffa.kits;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import lombok.Getter;
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
 * Class created: 2019-02-06, 20:15
 */
public abstract class Kit {

    @Getter
    private final int id, price;
    @Getter
    private final String iconName;
    @Getter
    private final Material iconMaterial;

    public Kit(int price, String iconName, Material iconMaterial) {
        this.id = BuildFFA.getBuildFFA().getKitRegistry().size();
        this.price = price;
        this.iconName = iconName;
        this.iconMaterial = iconMaterial;
        BuildFFA.getBuildFFA().getKitRegistry().put(getId(), this);
    }

    public void update() {
        BuildFFA.getBuildFFA().getStatsSQL().addKit(this);
        BuildFFA.getBuildFFA().getStatsSQL().updateKit(this);
    }

    public ItemStack getIcon() {
        ItemStack itemStack = new ItemStack(getIconMaterial());
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("§8» §a" + getIconName());
        List<String> lore = new ArrayList<>();
        for(int i = 0; i < getDefaultItemsSorted().length; i++) {
            ItemStack itemStack1 = getDefaultItemsSorted()[i];
            if(!itemStack1.getType().equals(Material.AIR)) {
                ItemMeta itemMeta1 = itemStack1.getItemMeta();
                if (BuildFFA.getBuildFFA().getIdsOfBlocks().contains(itemStack1.getTypeId())) {
                    lore.add("§8» §a" + itemStack1.getAmount() + "§7x §aBlöcke");
                } else {
                    lore.add("§8» §a" + itemStack1.getAmount() + "§7x §a" + itemMeta1.getDisplayName());
                }
            }
        }
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public abstract ItemStack[] getDefaultItemsSorted();

    public ItemStack[] buildItems(ItemStack[] itemStacks) {
        for(int i = 0; i < itemStacks.length; i++) {
            for(int j = 0; j < getDefaultItemsSorted().length; j++) {
                if(itemStacks[i].getType().equals(getDefaultItemsSorted()[j].getType())) {
                    itemStacks[i] = getDefaultItemsSorted()[j];
                }
            }
        }
        return itemStacks;
    }

    public ItemStack[] getArmorContents() {
        ItemStack[] armorContents = new ItemStack[4];
        armorContents[3] = ItemStackBuilder.getHelmet(Material.LEATHER_HELMET, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[2] = ItemStackBuilder.getChestplate(Material.CHAINMAIL_CHESTPLATE, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[1] = ItemStackBuilder.getLeggings(Material.LEATHER_LEGGINGS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        armorContents[0] = ItemStackBuilder.getBoots(Material.LEATHER_BOOTS, Enchantment.PROTECTION_ENVIRONMENTAL, 1);

        return armorContents;
    }

}

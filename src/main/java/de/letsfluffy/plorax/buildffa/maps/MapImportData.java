package de.letsfluffy.plorax.buildffa.maps;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.maps
 * Class created: 03.02.19, 10:46
 */
@Getter
public class MapImportData {

    private final String name, displayName, creator;
    private String itemID;
    private final int spawnHigh, dieHigh;
    private final Location spawn;

    private ItemStack itemStack;

    public MapImportData(String name, String displayName, String creator, String itemID, int spawnHigh, int dieHigh, Location spawn) {
        this.name = name;
        this.displayName = displayName;
        this.creator = creator;
        this.itemID = itemID;
        this.spawnHigh = spawnHigh;
        this.dieHigh = dieHigh;
        this.spawn = spawn;
        if(itemID == null) {
            this.itemID = "1;0";
        }
        String[] itemStackArray = getItemID().split(";");
        int id = Integer.valueOf(itemStackArray[0]);
        int itemData = Integer.valueOf(itemStackArray[1]);
        ItemStack forceMapItem = null;
        Material type = Material.getMaterial(id);
        if(itemData != 0) {
            forceMapItem = new ItemStack(Material.valueOf(type.name()), 1, (short) itemData);
        } else {
            forceMapItem = new ItemStack(Material.valueOf(type.name()), 1);
        }

        ItemMeta forceMapItemMeta = forceMapItem.getItemMeta();
        forceMapItemMeta.setDisplayName("§8» §a§l" + getDisplayName());
        List<String> list = new ArrayList<>();
        list.add("§8» §7by §a" + getCreator());
        forceMapItemMeta.setLore(list);
        forceMapItem.setItemMeta(forceMapItemMeta);
        itemStack = forceMapItem;

    }
}

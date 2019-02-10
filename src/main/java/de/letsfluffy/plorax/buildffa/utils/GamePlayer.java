package de.letsfluffy.plorax.buildffa.utils;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlocks;
import de.letsfluffy.plorax.buildffa.kits.Kit;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.utils
 * Class created: 2019-02-06, 20:32
 */
@Getter
public class GamePlayer {

    private final Player player;
    private final List<Kit> ownedKits = new ArrayList<>();

    private Kit selectedKit;

    private BuildBlocks buildBlocks;

    private ItemStack[] itemStacks;

    @Setter
    private boolean inSpawnArea = true;

    public GamePlayer(Player player) {
        this.player = player;
        this.selectedKit = BuildFFA.getBuildFFA().getKitRegistry().get(BuildFFA.getBuildFFA().getStatsSQL().getLastKit(player.getUniqueId()));
        this.buildBlocks = BuildFFA.getBuildFFA().getBuildBlockRegistry().get(BuildFFA.getBuildFFA().getStatsSQL().getLastBuildBlock(player.getUniqueId()));
        itemStacks = BuildFFA.getBuildFFA().getStatsSQL().getInventoryItems(getPlayer().getUniqueId(), getSelectedKit());
        itemStacks = getSelectedKit().buildItems(itemStacks);
        for(Integer id : BuildFFA.getBuildFFA().getKitRegistry().keySet()) {
            Kit kit = BuildFFA.getBuildFFA().getKitRegistry().get(id);
            if(ownKit(kit)) {
                getOwnedKits().add(kit);
            }
        }
    }

    public void setItemStacks(ItemStack[] itemStacks) {
        this.itemStacks = getSelectedKit().buildItems(itemStacks);
    }

    public void selectKit(Kit kit) {
        if(getOwnedKits().contains(kit)) {
            selectedKit = kit;
            itemStacks = BuildFFA.getBuildFFA().getStatsSQL().getInventoryItems(player.getUniqueId(), kit);
            BuildFFA.getBuildFFA().getStatsSQL().updateLastKit(player.getUniqueId(), kit);
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Das Kit wurde erfolgreich ausgewählt!");
        } else {
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Du hast das Kit bereits ausgewählt!");
        }
    }

    public void getKit() {
        Kit kit = getSelectedKit();
        if(getOwnedKits().contains(kit)) {
            selectedKit = kit;
            getPlayer().getInventory().clear();
            getPlayer().getInventory().setArmorContents(kit.getArmorContents());
            itemStacks = kit.buildItems(itemStacks);
            for(int i = 0; i < itemStacks.length; i++) {
                if(!BuildFFA.getBuildFFA().getIdsOfBlocks().contains(itemStacks[i].getTypeId())) {
                    player.getInventory().setItem(i, itemStacks[i]);
                } else {
                    player.getInventory().setItem(i, getBuildBlocks().getDefaultState());
                }
            }
        }
    }

    public void selectBlocks(BuildBlocks buildBlocks) {
        if(buildBlocks != this.buildBlocks) {
            BuildFFA.getBuildFFA().getStatsSQL().updateLastBuildBlock(player.getUniqueId(), buildBlocks);
            this.buildBlocks = buildBlocks;
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Du hast neue Blöcke ausgewählt!");
        } else {
            player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Du hast diese Blöcke bereits ausgewählt!");
        }
    }


    public boolean ownKit(Kit kit) {
        return BuildFFA.getBuildFFA().getStatsSQL().hasKitBought(player.getUniqueId(), kit);
    }

    public void buyKit(Kit kit) {
        BuildFFA.getBuildFFA().getStatsSQL().buyKit(player.getUniqueId(), kit);
        getOwnedKits().add(kit);
        player.sendMessage(BuildFFA.getBuildFFA().getPrefix() + "§7Du hast das Kit "
                + kit.getIcon().getItemMeta().getDisplayName().substring(4) + " §7erfolgreich gekauft!");
    }

}

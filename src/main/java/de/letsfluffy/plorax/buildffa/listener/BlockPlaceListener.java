package de.letsfluffy.plorax.buildffa.listener;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlock;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.listener
 * Class created: 2019-02-03, 12:53
 */
public class BlockPlaceListener implements Listener {

    @Getter
    private final BuildFFA buildFFA;

    public BlockPlaceListener(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(PloraxAPI.getSpecPlayers().containsKey(event.getPlayer())) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(getBuildFFA().getPrefix() + "§cIm SpecModus kannst du keine Blöcke platzieren.");
        } else if(getBuildFFA().getMapImporter().getMap().getSpawnHigh() <= event.getBlock().getLocation().getY()) {
            event.setCancelled(true);
        } else {
            if(getBuildFFA().getPlacedBlocks().containsKey(event.getBlock().getLocation())) {
                getBuildFFA().getPlacedBlocks().remove(event.getBlock().getLocation());
            }
            event.setCancelled(false);
            if(getBuildFFA().getIdsOfBlocks().contains(event.getBlock().getTypeId())) {
                getBuildFFA().getPlacedBlocks().put(event.getBlock().getLocation(), new BuildBlock(event.getBlock(),
                        getBuildFFA().getOnlinePlayers().get(event.getPlayer()).getBuildBlocks(), (event.getBlock().getType().equals(Material.WEB) ? false : true)));
            }
        }
    }
}

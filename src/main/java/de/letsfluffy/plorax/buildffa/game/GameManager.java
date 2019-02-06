package de.letsfluffy.plorax.buildffa.game;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.events.DoubleCoinsEvent;
import de.letsfluffy.plorax.buildffa.maps.MapImportData;
import de.letsfluffy.plorax.buildffa.utils.ActionbarAPI;
import de.letsfluffy.plorax.buildffa.utils.TitleAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.game
 * Class created: 2019-02-03, 12:40
 */
public class GameManager {

    @Getter
    private final BuildFFA buildFFA;

    public GameManager(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    private int mapSwitchCounter = 0;
    private MapImportData mapImportData = null;

    public static void setup() {

        BuildFFA.getBuildFFA().getStatsSQL().prepareStatements();

        BuildFFA.getBuildFFA().getMapImporter().importMaps();
        BuildFFA.getBuildFFA().getMapImporter().selectRandomMap();

        BuildFFA.getBuildFFA().getEventRegistry().put("DoubleCoins", new DoubleCoinsEvent());

        BuildFFA.getBuildFFA().registerEvents();
        BuildFFA.getBuildFFA().registerCommands();

        BuildFFA.getBuildFFA().getGameManager().runBlockRemover();

    }

    public static void endGame() {
        BuildFFA.getBuildFFA().getStatsSQL().closeStatements();
        BuildFFA.getBuildFFA().getStatsSQL().getExecutorService().shutdown();
    }

    public void runBlockRemover() {
        Bukkit.getScheduler().cancelAllTasks();
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getBuildFFA(), new Runnable() {
            @Override
            public void run() {
                for(Block block : getBuildFFA().getPlacedBlocks().keySet()) {
                    int i = getBuildFFA().getPlacedBlocks().get(block);
                    getBuildFFA().getPlacedBlocks().remove(block);
                    i--;
                    //10-8: normal, 8-6: grün, 6-4: gelb, 4-2: rot;
                    if(i == 0) {
                        block.setType(Material.AIR);
                        continue;
                    } else if (i == 6) {
                        block.setType(Material.STAINED_CLAY);
                        block.setData((byte) 5);
                    } else if(i == 4) {
                        block.setData((byte) 4);
                    } else if(i == 2) {
                        block.setData((byte) 2);
                    }
                    getBuildFFA().getPlacedBlocks().put(block, i);
                }

                int mapSwitchMinutes = mapSwitchCounter / 60;
                int mapSwitchSeconds = mapSwitchCounter % 60;
                String message = "§7Neue §aMap §r§8» §a§l" + mapImportData.getDisplayName() + " §r §8| §a" + mapSwitchMinutes + "§7:§a" + mapSwitchSeconds;

                for(Player player : Bukkit.getOnlinePlayers()) {
                    ActionbarAPI.sendActionbar(player, message);
                }
                if(mapSwitchCounter == 0) {
                    getBuildFFA().getMapImporter().setMap(mapImportData);
                    for(Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage(" ");
                        player.sendMessage(getBuildFFA().getPrefix() + "§7Die §a§lMap§r §7wird nun §agewechselt§7!");
                        player.sendMessage(getBuildFFA().getPrefix() + "§7Neue Map §8» §a§l" + mapImportData.getDisplayName());
                        player.sendMessage(" ");
                        player.teleport(mapImportData.getSpawn());
                        player.getInventory().clear();
                    }
                    startMapSwitch(60*10, getBuildFFA().getMapImporter().selectNextRandomMap());
                } else {
                    mapSwitchCounter--;
                    if(mapSwitchCounter == 10 || mapSwitchCounter == 5 || mapSwitchCounter == 3 || mapSwitchCounter == 2 || mapSwitchCounter == 1) {
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(getBuildFFA().getPrefix() + "§7Die Map wird in §a§l"
                                    + mapSwitchCounter + " §r§7" + (mapSwitchCounter == 1 ? "Sekunde" : "Sekunden") + " gewechselt.");
                        }
                    }
                    if(mapSwitchCounter == 10 || mapSwitchCounter == 3) {
                        for(Player player : Bukkit.getOnlinePlayers()) {
                            TitleAPI.sendTitle(player, 10, 30, 10, mapImportData.getDisplayName(), mapImportData.getCreator());
                        }
                    }
                }
            }
        }, 20, 20);
    }

    public void startMapSwitch(int count, MapImportData mapImportData) {
          mapSwitchCounter = count;
          this.mapImportData = mapImportData;
    }



}

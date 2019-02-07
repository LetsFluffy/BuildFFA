package de.letsfluffy.plorax.buildffa.game;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.*;
import de.letsfluffy.plorax.buildffa.events.DoubleCoinsEvent;
import de.letsfluffy.plorax.buildffa.events.PowerEvent;
import de.letsfluffy.plorax.buildffa.kits.Kit;
import de.letsfluffy.plorax.buildffa.kits.KnockKit;
import de.letsfluffy.plorax.buildffa.maps.MapImportData;
import de.letsfluffy.plorax.buildffa.utils.ActionbarAPI;
import de.letsfluffy.plorax.buildffa.utils.TitleAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

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
    @Getter
    private Inventory buildBlockInventory = null, kitInventory = null;



    public static void setup() {

        BuildFFA.getBuildFFA().getStatsSQL().prepareStatements();

        BuildFFA.getBuildFFA().getMapImporter().importMaps();
        BuildFFA.getBuildFFA().getMapImporter().selectRandomMap();

        BuildFFA.getBuildFFA().getGameManager().registerGameEvents();

        BuildFFA.getBuildFFA().getGameManager().registerKits();
        BuildFFA.getBuildFFA().getGameManager().createKitInventory();

        BuildFFA.getBuildFFA().getGameManager().registerBuildBlocks();
        BuildFFA.getBuildFFA().getGameManager().createBuildBlockInventory();

        BuildFFA.getBuildFFA().registerEvents();
        BuildFFA.getBuildFFA().registerCommands();

        BuildFFA.getBuildFFA().getGameManager().runBlockRemover();

    }

    public void registerGameEvents() {
        BuildFFA.getBuildFFA().getEventRegistry().put("DoubleCoins", new DoubleCoinsEvent());
        BuildFFA.getBuildFFA().getEventRegistry().put("Power", new PowerEvent());
    }

    public void registerKits() {
        KnockKit knockKit = new KnockKit();

        BuildFFA.getBuildFFA().getKitRegistry().put(knockKit.getId(), knockKit);
    }

    public void createKitInventory() {
        int size = getBuildFFA().getKitRegistry().size();
        while ((size % 9) != 0) {
            size++;
        }
        kitInventory = Bukkit.createInventory(null, size, "§8» §aWähle ein Kit aus");
        for(int i : getBuildFFA().getKitRegistry().keySet()) {
            Kit kit = getBuildFFA().getKitRegistry().get(i);
            getKitInventory().setItem(i, kit.getIcon());
        }
    }

    public void registerBuildBlocks() {
        SandstoneBlocks sandstoneBlocks = new SandstoneBlocks();
        GlasBlocks glasBlocks = new GlasBlocks();
        StoneBlocks stoneBlocks = new StoneBlocks();
        DirtBlocks dirtBlocks = new DirtBlocks();
        PrismarineBlocks prismarineBlocks = new PrismarineBlocks();
        QuarzBlocks quarzBlocks = new QuarzBlocks();
        ResourceBlocks resourceBlocks = new ResourceBlocks();
        OreBlocks oreBlocks = new OreBlocks();
        NetherBlocks netherBlocks = new NetherBlocks();

        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(sandstoneBlocks.getId(), sandstoneBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(glasBlocks.getId(), glasBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(stoneBlocks.getId(), stoneBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(dirtBlocks.getId(), dirtBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(prismarineBlocks.getId(), prismarineBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(quarzBlocks.getId(), quarzBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(resourceBlocks.getId(), resourceBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(oreBlocks.getId(), oreBlocks);
        BuildFFA.getBuildFFA().getBuildBlockRegistry().put(netherBlocks.getId(), netherBlocks);
    }

    public void createBuildBlockInventory() {
        int size = getBuildFFA().getBuildBlockRegistry().size();
        while ((size % 9) != 0) {
            size++;
        }
        buildBlockInventory = Bukkit.createInventory(null, size,"§8» §aWähle eine Blockart aus");
        for(int i : getBuildFFA().getBuildBlockRegistry().keySet()) {
            BuildBlocks buildBlocks = getBuildFFA().getBuildBlockRegistry().get(i);
            getBuildBlockInventory().setItem(i, buildBlocks.getDefaultState());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getDefaultState().getTypeId());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getFirstState().getTypeId());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getSecondState().getTypeId());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getThirdState().getTypeId());
        }
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
                    BuildBlock buildBlock = getBuildFFA().getPlacedBlocks().get(block);
                    int i = buildBlock.getTime();
                    getBuildFFA().getPlacedBlocks().remove(block);
                    buildBlock.subtractTime();
                    //10-8: normal, 8-6: grün, 6-4: gelb, 4-2: rot;
                    if(i == 0) {
                        block.setType(Material.AIR);
                        continue;
                    } else if (i == 6) {
                        block.setType(buildBlock.getBuildBlocks().getFirstState().getType());
                        block.setData(buildBlock.getBuildBlocks().getFirstState().getData().getData());
                    } else if(i == 4) {
                        block.setType(buildBlock.getBuildBlocks().getSecondState().getType());
                        block.setData(buildBlock.getBuildBlocks().getSecondState().getData().getData());
                    } else if(i == 2) {
                        block.setType(buildBlock.getBuildBlocks().getThirdState().getType());
                        block.setData(buildBlock.getBuildBlocks().getThirdState().getData().getData());
                    }
                    getBuildFFA().getPlacedBlocks().put(block, buildBlock);
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

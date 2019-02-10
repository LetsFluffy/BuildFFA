package de.letsfluffy.plorax.buildffa.game;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.*;
import de.letsfluffy.plorax.buildffa.events.DoubleCoinsEvent;
import de.letsfluffy.plorax.buildffa.events.PowerEvent;
import de.letsfluffy.plorax.buildffa.kits.Kit;
import de.letsfluffy.plorax.buildffa.kits.KnightKit;
import de.letsfluffy.plorax.buildffa.kits.KnockKit;
import de.letsfluffy.plorax.buildffa.maps.MapImportData;
import de.letsfluffy.plorax.buildffa.utils.ActionbarAPI;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import de.letsfluffy.plorax.buildffa.utils.TitleAPI;
import lombok.Getter;
import lombok.Setter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Iterator;

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
    @Setter
    private boolean joinable = false;


    public static void setup() {

        BuildFFA.getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {

            while (PloraxAPI.getConnection() == null) ;


            BuildFFA.getBuildFFA().getStatsSQL().prepareStatements();
            BuildFFA.getBuildFFA().getStatsSQL().createTable();

            BuildFFA.getBuildFFA().getGameManager().registerKits();
        });

        BuildFFA.getBuildFFA().getMapImporter().importMaps();
        BuildFFA.getBuildFFA().getMapImporter().selectRandomMap();

        BuildFFA.getBuildFFA().getGameManager().registerGameEvents();

        BuildFFA.getBuildFFA().getGameManager().registerBuildBlocks();

        BuildFFA.getBuildFFA().registerEvents();
        BuildFFA.getBuildFFA().registerCommands();

        BuildFFA.getBuildFFA().getGameManager().setJoinable(true);

        BuildFFA.getBuildFFA().getGameManager().sendMapInformation();
        BuildFFA.getBuildFFA().getGameManager().runBlockRemover();

    }

    public void registerGameEvents() {
        BuildFFA.getBuildFFA().getEventRegistry().put("DoubleCoins", new DoubleCoinsEvent());
        BuildFFA.getBuildFFA().getEventRegistry().put("Power", new PowerEvent());
    }

    public void registerKits() {
        KnockKit knockKit = new KnockKit();
        BuildFFA.getBuildFFA().getKitRegistry().put(knockKit.getId(), knockKit);

        KnightKit knightKit = new KnightKit();
        BuildFFA.getBuildFFA().getKitRegistry().put(knightKit.getId(), knightKit);
        getBuildFFA().getStatsSQL().addKit(knightKit);
    }

    public Inventory createKitInventory() {
        int size = getBuildFFA().getKitRegistry().size();
        while ((size % 9) != 0) {
            size++;
        }
        Inventory kitInventory = Bukkit.createInventory(null, size, "§8» §aWähle ein Kit aus");
        for (int i : getBuildFFA().getKitRegistry().keySet()) {
            Kit kit = getBuildFFA().getKitRegistry().get(i);
            kitInventory.setItem(i, kit.getIcon());
        }
        return kitInventory;
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

        for (int i : getBuildFFA().getBuildBlockRegistry().keySet()) {
            BuildBlocks buildBlocks = getBuildFFA().getBuildBlockRegistry().get(i);
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getDefaultState().getTypeId());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getFirstState().getTypeId());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getSecondState().getTypeId());
            BuildFFA.getBuildFFA().getIdsOfBlocks().add(buildBlocks.getThirdState().getTypeId());
        }
    }

    public Inventory createBuildBlockInventory() {
        int size = getBuildFFA().getBuildBlockRegistry().size();
        while ((size % 9) != 0) {
            size++;
        }
        Inventory buildBlockInventory = Bukkit.createInventory(null, size, "§8» §aWähle eine Blockart aus");
        for (int i : getBuildFFA().getBuildBlockRegistry().keySet()) {
            BuildBlocks buildBlocks = getBuildFFA().getBuildBlockRegistry().get(i);
            buildBlockInventory.setItem(i, buildBlocks.getDefaultState());
        }
        return buildBlockInventory;
    }

    public static void endGame() {
        BuildFFA.getBuildFFA().getStatsSQL().closeStatements();
        BuildFFA.getBuildFFA().getStatsSQL().getExecutorService().shutdown();
    }

    public void sendMapInformation() {
        startMapSwitch(60 * 10, getBuildFFA().getMapImporter().selectNextRandomMap());
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getBuildFFA(), new Runnable() {
            @Override
            public void run() {

                int mapSwitchMinutes = mapSwitchCounter / 60;
                int mapSwitchSeconds = mapSwitchCounter % 60;
                String message = "§cKeine Daten gefunden";
                if(mapImportData != null && getBuildFFA().getMapImporter().getMaps().size() > 1) {
                    message = "§7Nächste §aMap §r§8» §a§l" + mapImportData.getDisplayName() + " §r §8| §a"
                            + mapSwitchMinutes + "§7:§a" + (mapSwitchSeconds < 10 ? "0" + mapSwitchMinutes : mapSwitchSeconds);


                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ActionbarAPI.sendActionbar(player, message);
                    }
                    if (mapSwitchCounter == 0) {
                        getBuildFFA().getMapImporter().setMap(mapImportData);
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            player.sendMessage(" ");
                            player.sendMessage(getBuildFFA().getPrefix() + "§7Die §a§lMap§r §7wird nun §agewechselt§7!");
                            player.sendMessage(getBuildFFA().getPrefix() + "§7Neue Map §8» §a§l" + mapImportData.getDisplayName());
                            player.sendMessage(" ");
                            player.teleport(mapImportData.getSpawn());
                            player.getInventory().clear();
                            player.getInventory().setArmorContents(null);
                            player.getInventory().setItem(0, ItemStackBuilder.getSpawnItems()[0]);
                            player.getInventory().setItem(4, ItemStackBuilder.getSpawnItems()[1]);
                            player.getInventory().setItem(8, ItemStackBuilder.getSpawnItems()[2]);
                        }
                        startMapSwitch(60 * 10, getBuildFFA().getMapImporter().selectNextRandomMap());
                    } else {
                        mapSwitchCounter--;
                        if (mapSwitchCounter == 10 || mapSwitchCounter == 5 || mapSwitchCounter == 3 || mapSwitchCounter == 2 || mapSwitchCounter == 1) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                player.sendMessage(getBuildFFA().getPrefix() + "§7Die Map wird in §a§l"
                                        + mapSwitchCounter + " §r§7" + (mapSwitchCounter == 1 ? "Sekunde" : "Sekunden") + " gewechselt.");
                            }
                        }
                        if (mapSwitchCounter == 10 || mapSwitchCounter == 3) {
                            for (Player player : Bukkit.getOnlinePlayers()) {
                                TitleAPI.sendTitle(player, 10, 30, 10, mapImportData.getDisplayName(), mapImportData.getCreator());
                            }
                        }
                    }
                } else {
                    message = "§7Aktuelle §aMap §r§8» §a§l" + getBuildFFA().getMapImporter().getMap().getDisplayName();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ActionbarAPI.sendActionbar(player, message);
                    }
                }
            }
        }, 20, 20);
    }

    public void runBlockRemover() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getBuildFFA(), new Runnable() {
            @Override
            public void run() {
                HashMap<Location, BuildBlock> tempMap = new HashMap<>();
                for (Location location : getBuildFFA().getPlacedBlocks().keySet()) {
                    BuildBlock buildBlock = getBuildFFA().getPlacedBlocks().get(location);
                    buildBlock.subtractTime();
                    int i = buildBlock.getTime();
                    Block block = location.getBlock();
                    if (i == 0) {
                        block.setType(Material.AIR);
                        continue;
                    } else if (i == 6*20 && buildBlock.isChangeBlock()) {
                        block.setType(buildBlock.getBuildBlocks().getFirstState().getType());
                        block.setData(buildBlock.getBuildBlocks().getFirstState().getData().getData());
                    } else if (i == 4*20 && buildBlock.isChangeBlock()) {
                        block.setType(buildBlock.getBuildBlocks().getSecondState().getType());
                        block.setData(buildBlock.getBuildBlocks().getSecondState().getData().getData());
                    } else if (i == 2*20 && buildBlock.isChangeBlock()) {
                        block.setType(buildBlock.getBuildBlocks().getThirdState().getType());
                        block.setData(buildBlock.getBuildBlocks().getThirdState().getData().getData());
                    }
                    tempMap.put(location, buildBlock);

                }

                getBuildFFA().getPlacedBlocks().clear();
                getBuildFFA().getPlacedBlocks().putAll(tempMap);

            }
        }, 1, 1);
    }

    public void startMapSwitch(int count, MapImportData mapImportData) {
        mapSwitchCounter = count;
        this.mapImportData = mapImportData;
    }


}

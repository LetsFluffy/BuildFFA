package de.letsfluffy.plorax.buildffa.game;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.*;
import de.letsfluffy.plorax.buildffa.events.DoubleCoinsEvent;
import de.letsfluffy.plorax.buildffa.events.PowerEvent;
import de.letsfluffy.plorax.buildffa.kits.*;
import de.letsfluffy.plorax.buildffa.maps.MapImportData;
import de.letsfluffy.plorax.buildffa.utils.ActionbarAPI;
import de.letsfluffy.plorax.buildffa.utils.ItemStackBuilder;
import de.letsfluffy.plorax.buildffa.utils.PacketScoreboard;
import de.letsfluffy.plorax.buildffa.utils.TitleAPI;
import lombok.Getter;
import lombok.Setter;
import net.plorax.api.PloraxAPI;
import net.plorax.api.StatsAPI;
import net.plorax.api.util.PloraxPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.util.Vector;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

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
        BuildFFA.getBuildFFA().getGameManager().runRescuePlatformHelper();
        BuildFFA.getBuildFFA().getGameManager().runBlockRemover();
        BuildFFA.getBuildFFA().getGameManager().runItemGiver();

    }

    public void registerGameEvents() {
        BuildFFA.getBuildFFA().getEventRegistry().put("DoubleCoins", new DoubleCoinsEvent());
        BuildFFA.getBuildFFA().getEventRegistry().put("Power", new PowerEvent());
    }

    public void registerKits() {
        new KnightKit();
        new KnockKit();
        new GrapplingHookKit();
        new BowKit();
        new EnderpearlKit();
        //new GranateKit();

        for (int i = 0; i < getBuildFFA().getKitRegistry().size(); i++) {
            getBuildFFA().getKitRegistry().get(i).update();
        }
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
                String message = "§cKeine Daten gefunden";
                if (mapImportData != null && getBuildFFA().getMapImporter().getMaps().size() > 1) {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                    String date = simpleDateFormat.format(mapSwitchCounter * 1000);
                    message = "§7Nächste Map §r§8» §a§l" + mapImportData.getDisplayName() + "§r §8| §a" +
                            date.split(":")[0] + "§7:§a" + date.split(":")[1];


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
                            PacketScoreboard.updateScoreboard(player);
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
                                TitleAPI.sendTitle(player, 10, 30, 10, "§8» §a§l" + mapImportData.getDisplayName(),
                                        "§7by §8» §a" + mapImportData.getCreator());
                            }
                        }
                    }
                } else {
                    message = "§7Aktuelle Map §r§8» §a§l" + getBuildFFA().getMapImporter().getMap().getDisplayName();
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        ActionbarAPI.sendActionbar(player, message);
                    }
                }
            }
        }, 20, 20);
    }

    public void runRescuePlatformHelper() {
        Bukkit.getScheduler().scheduleAsyncRepeatingTask(getBuildFFA(), new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getLocation().subtract(0, 1, 0).getBlock().getType().equals(Material.HAY_BLOCK)) {
                        final Vector vector = new Vector();

                        final double aDouble = -0.08D;
                        final double distance = player.getLocation().distance(player.getLocation().add(0, 7, 0));

                        final double vecY = (1D + 0.03D * distance) * (player.getLocation().add(0, 7, 0).getY()
                                - player.getLocation().getY()) / distance - 0.5D * aDouble * distance;

                        vector.setY(vecY);

                        player.setVelocity(vector);
                    }
                }
            }
        }, 1, 1);
    }

    public void runItemGiver() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getBuildFFA(), new Runnable() {
            @Override
            public void run() {
                int spawnHigh = getBuildFFA().getMapImporter().getMap().getSpawnHigh();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    double y = player.getLocation().getY();
                    if (y <= getBuildFFA().getMapImporter().getMap().getDieHigh()) {
                        if (getBuildFFA().getProjectiles().containsKey(player)) {
                            getBuildFFA().getProjectiles().get(player).remove();
                            getBuildFFA().getProjectiles().remove(player);
                        }
                        player.teleport(getBuildFFA().getMapImporter().getMap().getSpawn());
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(null);
                        player.getInventory().setItem(0, ItemStackBuilder.getSpawnItems()[0]);
                        player.getInventory().setItem(4, ItemStackBuilder.getSpawnItems()[1]);
                        player.getInventory().setItem(8, ItemStackBuilder.getSpawnItems()[2]);

                        getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
                            long[] l = new long[2];
                            l[0] = 0;
                            l[1] = 1;
                            PloraxAPI.getStatsAPI().addStats(player.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA, l);
                            PloraxAPI.getCoinAPI().subtractCoins(player.getUniqueId(), 1);
                        });

                        if (getBuildFFA().getCombatLog().containsKey(player)) {
                            Player killer = getBuildFFA().getCombatLog().get(player);
                            getBuildFFA().getStatsSQL().getExecutorService().execute(() -> {
                                PloraxAPI.getCoinAPI().addCoins(killer.getUniqueId(), getBuildFFA().getKillCoins());
                                long[] l = new long[2];
                                l[0] = 1;
                                l[1] = 0;
                                PloraxAPI.getStatsAPI().addStats(killer.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA, l);
                            });
                            getBuildFFA().getCombatLog().remove(player);
                            getBuildFFA().getKillstreak().remove(player);
                            getBuildFFA().getKillstreak().put(player, 0);
                            int kills = getBuildFFA().getKillstreak().get(killer);
                            kills++;
                            if ((kills == 3) || ((kills % 5) == 0)) {
                                for (Player player1 : Bukkit.getOnlinePlayers()) {
                                    player1.sendMessage(getBuildFFA().getPrefix() + new PloraxPlayer(killer.getUniqueId()).getPrefixName()
                                            + " §7hat eine §a§l" + kills + "er§r §7Killstreak!");
                                }
                            }
                            getBuildFFA().getKillstreak().remove(killer);
                            getBuildFFA().getKillstreak().put(killer, kills);
                            killer.sendMessage(getBuildFFA().getPrefix() + "§7Du hast " + new PloraxPlayer(player.getUniqueId()).getPrefixName() + " §7getötet.");
                            killer.setHealth(20);
                            player.sendMessage(getBuildFFA().getPrefix() + "§7Du wurdest von " + new PloraxPlayer(killer.getUniqueId()).getPrefixName() + " §7getötet.");
                            PacketScoreboard.updateScoreboard(killer);
                        } else {
                            player.sendMessage(getBuildFFA().getPrefix() + "§7Du bist gestorben.");
                        }
                        player.setHealth(20);
                        PacketScoreboard.updateScoreboard(player);
                        player.setVelocity(new Vector());
                    } else if (y<= spawnHigh && player.getInventory().getItem(0).getType().equals(Material.REDSTONE_TORCH_ON)) {
                        player.closeInventory();
                        player.getInventory().clear();
                        player.getInventory().setArmorContents(getBuildFFA().getOnlinePlayers().get(player).getSelectedKit().getArmorContents());

                        getBuildFFA().getOnlinePlayers().get(player).getKit();
                    }
                }
            }
        }, 1, 1);
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
                    } else if (i == 6 * 20 && buildBlock.isChangeBlock()) {
                        block.setType(buildBlock.getBuildBlocks().getFirstState().getType());
                        block.setData(buildBlock.getBuildBlocks().getFirstState().getData().getData());
                    } else if (i == 4 * 20 && buildBlock.isChangeBlock()) {
                        block.setType(buildBlock.getBuildBlocks().getSecondState().getType());
                        block.setData(buildBlock.getBuildBlocks().getSecondState().getData().getData());
                    } else if (i == 2 * 20 && buildBlock.isChangeBlock()) {
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

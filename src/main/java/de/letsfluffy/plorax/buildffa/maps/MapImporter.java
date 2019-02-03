package de.letsfluffy.plorax.buildffa.maps;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.File;
import java.util.Random;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.maps
 * Class created: 03.02.19, 10:52
 */
@Getter
public class MapImporter {

    private final BuildFFA buildFFA;
    private final Inventory forceMapInventory = Bukkit.createInventory(null, 27, "§8» §aForcemap");
    private final List<MapImportData> maps = new ArrayList<>();
    @Setter
    private MapImportData map;
    private final HashMap<Integer, MapImportData> forceMaps = new HashMap<>();


    public MapImporter(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    public void importMaps() {
        File mapsFile = new File(getBuildFFA().getPrefix(), "maps.yml");
        FileConfiguration mapsConfiguration = YamlConfiguration.loadConfiguration(mapsFile);
        List<String> mapList = mapsConfiguration.getStringList("maps");
        for(String maps : mapList) {
            Bukkit.createWorld(new WorldCreator(maps));
        }
        for(World world : Bukkit.getWorlds()) {
            world.setTime(0);
            world.setStorm(false);
            world.setThundering(false);
            world.setGameRuleValue("doDaylightCycle", "false");
        }
        for(String maps : mapList) {
            File mapFile = new File(getBuildFFA().getDataFolder() + "/maps", maps +".yml");
            if(!mapFile.exists()) {
                System.out.println("Map File " + maps + " does NOT exists!");
                continue;
            }
            FileConfiguration mapConfiguration = YamlConfiguration.loadConfiguration(mapFile);

            World world = Bukkit.getWorld(mapConfiguration.getString("Spawn.WORLD"));
            double x = mapConfiguration.getDouble("Spawn.X");
            double y = mapConfiguration.getDouble("Spawn.Y");
            double z = mapConfiguration.getDouble("Spawn.Z");
            double yaw = mapConfiguration.getDouble("Spawn.YAW");
            double pitch = mapConfiguration.getDouble("Spawn.PITCH");
            Location spawn = new Location(world, x, y, z);
            spawn.setYaw((float) yaw);
            spawn.setPitch((float) pitch);

            int spawnHigh = mapConfiguration.getInt("spawnHigh");
            String name = mapConfiguration.getString("name");
            String displayName = mapConfiguration.getString("displayName");
            String creator = mapConfiguration.getString("creator");
            String itemID = mapConfiguration.getString("item");

            MapImportData mapImportData = new MapImportData(name, displayName, creator, itemID, spawnHigh, spawn);
            getMaps().add(mapImportData);

        }
        int current = 0;
        for(MapImportData mapImportData : getMaps()) {
            getForceMaps().put(current, mapImportData);
            getForceMapInventory().setItem(current, mapImportData.getItemStack());
        }
    }

    public void selectRandomMap() {
        int random = new Random().nextInt(getMaps().size());
        setMap(getMaps().get(random));
    }

    public MapImportData selectNextRandomMap() {
        int random = new Random().nextInt(getMaps().size());
        return getMaps().get(random);
    }
}

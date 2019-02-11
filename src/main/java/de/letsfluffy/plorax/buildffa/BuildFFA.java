package de.letsfluffy.plorax.buildffa;

import com.google.common.reflect.ClassPath;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlock;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlocks;
import de.letsfluffy.plorax.buildffa.commands.EventCommand;
import de.letsfluffy.plorax.buildffa.commands.ForcemapCommand;
import de.letsfluffy.plorax.buildffa.events.Event;
import de.letsfluffy.plorax.buildffa.game.GameManager;
import de.letsfluffy.plorax.buildffa.kits.Kit;
import de.letsfluffy.plorax.buildffa.maps.MapImporter;
import de.letsfluffy.plorax.buildffa.mysql.StatsSQL;
import de.letsfluffy.plorax.buildffa.utils.GamePlayer;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa
 * Class created: 03.02.19, 10:43
 */
public class BuildFFA extends JavaPlugin {

    @Getter
    private static BuildFFA buildFFA;
    @Getter
    private String prefix = "§2§lBuild§a§lFFA §8| ";
    @Getter
    private MapImporter mapImporter;
    @Getter
    private StatsSQL statsSQL;
    @Getter
    private GameManager gameManager;

    @Getter
    private final HashMap<Location, BuildBlock> placedBlocks = new HashMap<>();

    @Getter
    private final HashMap<Player, Player> combatLog = new HashMap<>();
    @Getter
    private final HashMap<Player, Integer> killstreak = new HashMap<>();

    @Getter
    @Setter
    private int killCoins = 5;

    @Getter
    @Setter
    private Event currentEvent = null;

    @Getter
    private final HashMap<String, Event> eventRegistry = new HashMap<>();
    @Getter
    private final HashMap<Integer, Kit> kitRegistry = new HashMap<>();
    @Getter
    private final HashMap<Integer, BuildBlocks> buildBlockRegistry = new HashMap<>();

    @Getter
    private final HashMap<Player, GamePlayer> onlinePlayers = new HashMap<>();

    @Getter
    private final List<Integer> idsOfBlocks = new ArrayList<>();

    @Getter
    private final HashMap<Player, Projectile> projectiles = new HashMap<>();



    @Override
    public void onEnable() {
        buildFFA = this;
        mapImporter = new MapImporter(this);
        statsSQL = new StatsSQL(this);
        gameManager = new GameManager(this);

        GameManager.setup();
    }

    @Override
    public void onDisable() {
        GameManager.endGame();
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        try {
            for (ClassPath.ClassInfo ci : ClassPath.from(getClassLoader())
                    .getTopLevelClasses("de.letsfluffy.plorax.buildffa.listener")) {
                @SuppressWarnings("rawtypes")
                Class clazz = Class.forName(ci.getName());

                if (Listener.class.isAssignableFrom(clazz)) {
                    pm.registerEvents((Listener) clazz.getConstructor(BuildFFA.class).newInstance(this), this);
                }
            }
        } catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    public void registerCommands() {
        //new ForcemapCommand(this);
        new EventCommand(this);
    }


}

package de.letsfluffy.plorax.buildffa.mysql;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.ItemStackBuilder;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.mysql
 * Class created: 2019-02-03, 11:51
 */
public class StatsSQL {

    @Getter
    private final BuildFFA buildFFA;

    @Getter
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private PreparedStatement createTable, insertUser, updateInventory, selectData;

    public StatsSQL(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    public void prepareStatements() {
        try {
            createTable = PloraxAPI.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS InventoryBuildFFA(UUID VARCHAR, INVENTORY VARCHAR(30))");
            insertUser = PloraxAPI.getConnection().prepareStatement("INSERT INTO InventoryBuildFFA(UUID, INVENTORY)VALUES(?,?)");
            updateInventory = PloraxAPI.getConnection().prepareStatement("UPDATE InventoryBuildFFA SET INVENTORY = ? WHERE UUID = ?");
            selectData = PloraxAPI.getConnection().prepareStatement("SELECT * FROM InventoryBuildFFA WHERE UUID = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeStatements() {
        try {
            createTable.close();
            insertUser.close();
            updateInventory.close();
            selectData.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(UUID uuid) {
        try {
            insertUser.setString(1, uuid.toString());
            //1: Schwert, 2: Stick, 3: Angel, 4: Cobweb, 5: Rettungsplattform, 6: Sandstone, 7: Enderperle
            insertUser.setString(2, "1;2;3;4;5;6;6;6;7");
            insertUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInventory(UUID uuid, Inventory inventory) {

        try {
            updateInventory.setString(1, uuid.toString());
            String data = "";
            for(int i = 0; i < 8; i++) {
                ItemStack itemStack = inventory.getItem(i);
                String s = "";
                if(itemStack.getType().equals(Material.GOLD_SWORD)) {
                    s = "1";
                } else if(itemStack.getType().equals(Material.STICK)) {
                    s = "2";
                } else if(itemStack.getType().equals(Material.FISHING_ROD)) {
                    s = "3";
                } else if(itemStack.getType().equals(Material.WEB)) {
                    s = "4";
                } else if(itemStack.getType().equals(Material.BLAZE_ROD)) {
                    s = "5";
                } else if(itemStack.getType().equals(Material.SANDSTONE)) {
                    s = "6";
                } else if(itemStack.getType().equals(Material.ENDER_PEARL)) {
                    s = "7";
                }
                data = data + s + ";";
            }
            data = data.substring(0, (data.length()-1));
            updateInventory.setString(2, data);
            updateInventory.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ItemStack[] getInventoryItems(UUID uuid) {
        ItemStack[] itemStack = new ItemStack[8];
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            if(resultSet.next()) {
                String s = resultSet.getString("INVENTORY");
                String[] array = s.split(";");
                for(int i = 0; i < array.length; i++) {
                    String s1 = array[i];
                    if(s1.equalsIgnoreCase("1")) {
                        itemStack[i] = ItemStackBuilder.getSword();
                    } else if(s1.equalsIgnoreCase("2")) {
                        itemStack[i] = ItemStackBuilder.getStick();
                    } else if(s1.equalsIgnoreCase("3")) {
                        itemStack[i] = ItemStackBuilder.getRod();
                    } else if(s1.equalsIgnoreCase("4")) {
                        itemStack[i] = ItemStackBuilder.getCobweb();
                    } else if(s1.equalsIgnoreCase("5")) {
                        itemStack[i] = ItemStackBuilder.getRescuePlatform();
                    } else if(s1.equalsIgnoreCase("6")) {
                        itemStack[i] = ItemStackBuilder.getSandstoneBlocks();
                    } else if(s1.equalsIgnoreCase("7")) {
                        itemStack[i] = ItemStackBuilder.getEnderpearl();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return itemStack;
    }

    public boolean isExisting(UUID uuid) {
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

}

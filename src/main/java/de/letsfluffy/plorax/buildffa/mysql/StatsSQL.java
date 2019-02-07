package de.letsfluffy.plorax.buildffa.mysql;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import de.letsfluffy.plorax.buildffa.buildblocks.BuildBlocks;
import de.letsfluffy.plorax.buildffa.kits.Kit;
import lombok.Getter;
import net.plorax.api.PloraxAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    private PreparedStatement createTable, insertUser, updateKits, updateLastKit, updateLastBlock, selectData;

    public StatsSQL(BuildFFA buildFFA) {
        this.buildFFA = buildFFA;
    }

    public void prepareStatements() {
        try {
            createTable = PloraxAPI.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS InventoryBuildFFA(UUID VARCHAR(64), KITS MESSAGE_TEXT, LASTKIT INTEGER, LASTBLOCK INTEGER)");
            insertUser = PloraxAPI.getConnection().prepareStatement("INSERT INTO InventoryBuildFFA(UUID, KITS, LASTKIT, LASTBLOCK)VALUES(?,?,?,?)");
            updateKits = PloraxAPI.getConnection().prepareStatement("UPDATE InventoryBuildFFA SET KITS = ? WHERE UUID = ?");
            updateLastKit = PloraxAPI.getConnection().prepareStatement("UPDATE InventoryBuildFFA SET LASTKIT WHERE UUID = ?");
            updateLastBlock = PloraxAPI.getConnection().prepareStatement("UPDATE InventoryBuildFFA SET LASTBLOCK WHERE UUID = ?");
            selectData = PloraxAPI.getConnection().prepareStatement("SELECT * FROM InventoryBuildFFA WHERE UUID = ?");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeStatements() {
        try {
            createTable.close();
            insertUser.close();
            updateKits.close();
            updateLastKit.close();
            updateLastBlock.close();
            selectData.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //-#-#-#-
    public void insertUser(UUID uuid) {
        try {
            String defaultString = "";
            for(int i = 0; i < getBuildFFA().getKitRegistry().size()-1; i++) {
                String data = i + "-false-";
                for(int j = 0; j < getBuildFFA().getKitRegistry().get(i).getDefaultItemsSorted().length; j++) {
                    ItemStack itemStack = getBuildFFA().getKitRegistry().get(i).getDefaultItemsSorted()[j];
                    String s1 = itemStack.getTypeId() + ":" + itemStack.getData().getData();
                    data = data + s1 + ";";
                }
                data = data.substring(0, data.length()-1);
                defaultString = defaultString + data + "#";

            }
            defaultString = defaultString.substring(0, defaultString.length()-1);
            defaultString = defaultString.replaceFirst("false", "true");
            insertUser.setString(1, uuid.toString());
            insertUser.setString(2, defaultString);
            insertUser.setInt(3, 0);
            insertUser.setInt(4, 0);
            insertUser.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateInventory(UUID uuid, Kit kit, Inventory inventory) {

        try {
            String rawKitString = getRawKitsString(uuid);
            updateKits.setString(1, uuid.toString());
            String data = "";
            for(int i = 0; i < 8; i++) {
                ItemStack itemStack = inventory.getItem(i);
                String s = itemStack.getTypeId() + ":" + itemStack.getData().getData();
                data = data + s + ";";
            }
            data = data.substring(0, (data.length()-1));
            data = kit.getId() + "-true-" + data;
            String s = "";

            String[] rawKitStringArray = rawKitString.split("#");
            for(int i = 0; i < rawKitStringArray.length; i++) {
                if(Integer.valueOf(rawKitStringArray[i].split("-")[0]) == kit.getId()) {
                    s = s + data + "#";
                } else {
                    s = s + rawKitStringArray[i] + "#";
                }
            }

            s = s.substring(0, (s.length()-1));

            updateKits.setString(2, s);
            updateKits.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String getRawKitsString(UUID uuid) {
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            if(resultSet.next()) {
                return resultSet.getString("KITS");
            }
        } catch (SQLException e) {

        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public ItemStack[] getInventoryItems(UUID uuid, Kit kit) {
        ItemStack[] itemStacks = new ItemStack[8];
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            if(resultSet.next()) {
                String s = resultSet.getString("KITS");
                String[] kits = s.split("#");
                String[] kitsNameArray = kits[kit.getId()].split("-");
                String[] itemArray = kitsNameArray[2].split(";");
                for(int i = 0; i < itemArray.length; i++) {
                    String item = itemArray[i];
                    int id = Integer.valueOf(item.split(":")[0]);
                    int itemData = Integer.valueOf(item.split(":")[1]);
                    ItemStack itemStack = null;
                    Material type = Material.getMaterial(id);
                    if(itemData != 0) {
                        itemStack = new ItemStack(Material.valueOf(type.name()), 1, (short) itemData);
                    } else {
                        itemStack = new ItemStack(Material.valueOf(type.name()), 1);
                    }
                    itemStacks[i] = itemStack;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return itemStacks;
    }

    public boolean hasKitBought(UUID uuid, Kit kit) {
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            if(resultSet.next()) {
                String s = resultSet.getString("KITS");
                String[] kitsArray = s.split("#");
                for(int i = 0; i < kitsArray.length; i++) {
                    if(Integer.valueOf(kitsArray[i].split("-")[0]) == kit.getId()) {
                        boolean b = Boolean.valueOf(kitsArray[i].split("-")[1]);
                        return b;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void updateLastKit(UUID uuid, Kit kit) {
        try {
            updateLastKit.setString(1, uuid.toString());
            updateLastKit.setInt(2, kit.getId());
            updateLastKit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastKit(UUID uuid) {
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("LASTKIT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void updateLastBuildBlock(UUID uuid, BuildBlocks buildBlocks) {
        try {
            updateLastKit.setString(1, uuid.toString());
            updateLastKit.setInt(2, buildBlocks.getId());
            updateLastKit.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getLastBuildBlock(UUID uuid) {
        ResultSet resultSet = null;
        try {
            selectData.setString(1, uuid.toString());
            resultSet = selectData.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("LASTBLOCK");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public void buyKit(UUID uuid, Kit kit) {
        try {
            String rawString = getRawKitsString(uuid);
            String s = "";
            String data = kit.getId() + "-true-";
            for(int i = 0; i < kit.getDefaultItemsSorted().length; i++) {
                ItemStack itemStack = kit.getDefaultItemsSorted()[i];
                String s1 = itemStack.getTypeId() + ":" + itemStack.getData().getData();
                data = data + s1 + ";";
            }
            data = data.substring(0, (data.length()-1));
            String[] rawKitStringArray = rawString.split("#");
            for(int i = 0; i < rawKitStringArray.length; i++) {
                if(Integer.valueOf(rawKitStringArray[i].split("-")[0]) == kit.getId()) {
                    s = s + data + "#";
                } else {
                    s = s + rawKitStringArray[i] + "#";
                }
            }

            s = s.substring(0, (s.length()-1));

            updateKits.setString(1, uuid.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

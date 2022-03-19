package me.gerald.economy.util;

import me.gerald.economy.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigUtil {
    //thanks kody simpson like always :)
    private static File balanceFile;
    private static FileConfiguration balanceFileConfig;

    private static File shopFile;
    private static FileConfiguration shopFileConfig;

    //Finds or generates the custom config file
    public static void setup(){
        //balance file
        balanceFile = new File(Main.INSTANCE.getDataFolder(), "balance.yml");
        if (!balanceFile.exists()){
            try{
                balanceFile.createNewFile();
            }catch (IOException ignored){ }
        }
        balanceFileConfig = YamlConfiguration.loadConfiguration(balanceFile);

        shopFile = new File(Main.INSTANCE.getDataFolder(), "shop.yml");
        if (!shopFile.exists()){
            try{
                shopFile.createNewFile();
            }catch (IOException ignored){ }
        }
        shopFileConfig = YamlConfiguration.loadConfiguration(shopFile);
    }

    public static void save(){
        try{
            balanceFileConfig.save(balanceFile);
            shopFileConfig.save(shopFile);
        }catch (IOException e){
            System.out.println("Couldn't save file.");
        }
    }

    public static void reload(){
        balanceFileConfig = YamlConfiguration.loadConfiguration(balanceFile);
        shopFileConfig = YamlConfiguration.loadConfiguration(shopFile);
    }

    public static FileConfiguration getBalance(){
        return balanceFileConfig;
    }

    public static FileConfiguration getShop() {
        return shopFileConfig;
    }
}

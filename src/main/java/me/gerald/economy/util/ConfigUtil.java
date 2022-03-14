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
    }

    public static void save(){
        try{
            balanceFileConfig.save(balanceFile);
        }catch (IOException e){
            System.out.println("Couldn't save file.");
        }
    }

    public static void reload(){
        balanceFileConfig = YamlConfiguration.loadConfiguration(balanceFile);
    }

    public static FileConfiguration getBalance(){
        return balanceFileConfig;
    }
}

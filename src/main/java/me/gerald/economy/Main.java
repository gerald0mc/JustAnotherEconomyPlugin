package me.gerald.economy;

import me.gerald.economy.command.CommandManager;
import me.gerald.economy.event.EntityDeathListener;
import me.gerald.economy.event.JoinListener;
import me.gerald.economy.util.ConfigUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main INSTANCE;

    @Override
    public void onEnable() {
        //set plugin instance
        INSTANCE = this;
        //setup custom config
        ConfigUtil.setup();
        //setup normal config
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        //register command
        getCommand("economy").setExecutor(new CommandManager());
        //register events
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
    }
}

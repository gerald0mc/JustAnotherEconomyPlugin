package me.gerald.thing;

import me.gerald.thing.command.CommandManager;
import me.gerald.thing.event.EntityDeathListener;
import me.gerald.thing.event.JoinListener;
import me.gerald.thing.util.ConfigUtil;
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
        getCommand("thing").setExecutor(new CommandManager());
        //register events
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(), this);
    }
}

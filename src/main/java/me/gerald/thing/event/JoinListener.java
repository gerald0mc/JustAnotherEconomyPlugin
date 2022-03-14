package me.gerald.thing.event;

import me.gerald.thing.Main;
import me.gerald.thing.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(!ConfigUtil.getBalance().contains(player.getDisplayName() + " Balance")) {
            ConfigUtil.getBalance().addDefault(player.getDisplayName() + " Balance", 0);
            ConfigUtil.getBalance().set(player.getDisplayName() + " Balance", 0);
            ConfigUtil.getBalance().options().copyDefaults(true);
            ConfigUtil.save();
            ConfigUtil.reload();
            Main.INSTANCE.getLogger().info("Added " + player.getDisplayName() + " to the balance list with a value of [0].");
        }
    }
}

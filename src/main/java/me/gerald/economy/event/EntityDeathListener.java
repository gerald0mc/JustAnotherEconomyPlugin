package me.gerald.economy.event;

import me.gerald.economy.Main;
import me.gerald.economy.util.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }else {
            ConfigUtil.getBalance().addDefault(killer.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(killer.getDisplayName() + " Balance") + (getEntityName(event.getEntity()).equalsIgnoreCase("Unknown") ? 5 : Main.INSTANCE.getConfig().getInt(getEntityName(event.getEntity()) + "Value")));
            ConfigUtil.getBalance().set(killer.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(killer.getDisplayName() + " Balance") + (getEntityName(event.getEntity()).equalsIgnoreCase("Unknown") ? 5 : Main.INSTANCE.getConfig().getInt(getEntityName(event.getEntity()) + "Value")));
            ConfigUtil.save();
            ConfigUtil.reload();
            killer.sendMessage(ChatColor.GREEN + "Killed a entity named " + ChatColor.YELLOW + getEntityName(event.getEntity()) + ChatColor.GREEN + " and received " + ChatColor.AQUA + "$" + (getEntityName(event.getEntity()).equalsIgnoreCase("Unknown") ? 5 : Main.INSTANCE.getConfig().getInt(getEntityName(event.getEntity()) + "Value")));
        }
    }

    public String getEntityName(Entity entity) {
        if(entity instanceof Player) {
            return "Player";
        }else if(entity instanceof Zombie) {
            return "Zombie";
        }else if(entity instanceof Skeleton) {
            return "Skeleton";
        }else if(entity instanceof Spider) {
            return "Spider";
        }else if(entity instanceof Creeper) {
            return "Creeper";
        }else if(entity instanceof Blaze) {
            return "Blaze";
        }else if(entity instanceof Witch) {
            return "Witch";
        }else if(entity instanceof Enderman) {
            return "Enderman";
        }else if(entity instanceof Cow) {
            return "Cow";
        }else if(entity instanceof Sheep) {
            return "Sheep";
        }else if(entity instanceof Pig) {
            return "Pig";
        }else if(entity instanceof Chicken) {
            return "Chicken";
        }else {
            return "Unknown";
        }
    }
}

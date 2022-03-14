package me.gerald.economy.event;

import me.gerald.economy.Main;
import me.gerald.economy.util.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    public int entityValue;
    public String entityName;

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        getEntityValue(event.getEntity());
        Player killer = event.getEntity().getKiller();
        if(killer == null) {
            return;
        }else {
            ConfigUtil.getBalance().addDefault(killer.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(killer.getDisplayName() + " Balance") + entityValue);
            ConfigUtil.getBalance().set(killer.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(killer.getDisplayName() + " Balance") + entityValue);
            ConfigUtil.save();
            killer.sendMessage(ChatColor.GREEN + "Killed a entity named " + ChatColor.YELLOW + entityName + ChatColor.GREEN + " and received " + ChatColor.AQUA + "$" + entityValue);
        }
    }

    public void getEntityValue(Entity entity) {
        if(entity instanceof Player) {
            entityValue = Main.INSTANCE.getConfig().getInt("PlayerValue");
            entityName = ((Player) entity).getDisplayName();
        }else if(entity instanceof Zombie) {
            entityValue = Main.INSTANCE.getConfig().getInt("ZombieValue");
            entityName = "Zombie";
        }else if(entity instanceof Skeleton) {
            entityValue = Main.INSTANCE.getConfig().getInt("SkeletonValue");
            entityName = "Skeleton";
        }else if(entity instanceof Spider) {
            entityValue = Main.INSTANCE.getConfig().getInt("SpiderValue");
            entityName = "Spider";
        }else if(entity instanceof Creeper) {
            entityValue = Main.INSTANCE.getConfig().getInt("CreeperValue");
            entityName = "Creeper";
        }else if(entity instanceof Blaze) {
            entityValue = Main.INSTANCE.getConfig().getInt("BlazeValue");
            entityName = "Blaze";
        }else if(entity instanceof Witch) {
            entityValue = Main.INSTANCE.getConfig().getInt("WitchValue");
            entityName = "Witch";
        }else if(entity instanceof Enderman) {
            entityValue = Main.INSTANCE.getConfig().getInt("EndermanValue");
            entityName = "Enderman";
        }else if(entity instanceof Cow) {
            entityValue = Main.INSTANCE.getConfig().getInt("CowValue");
            entityName = "Cow";
        }else if(entity instanceof Sheep) {
            entityValue = Main.INSTANCE.getConfig().getInt("SheepValue");
            entityName = "Sheep";
        }else if(entity instanceof Pig) {
            entityValue = Main.INSTANCE.getConfig().getInt("PigValue");
            entityName = "Pig";
        }else if(entity instanceof Chicken) {
            entityValue = Main.INSTANCE.getConfig().getInt("ChickenValue");
            entityName = "Chicken";
        }else {
            entityValue = 5;
            entityName = "Unknown Entity";
        }
    }
}

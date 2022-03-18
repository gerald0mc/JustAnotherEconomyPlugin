package me.gerald.economy.event;

import me.gerald.economy.Main;
import me.gerald.economy.command.CommandManager;
import me.gerald.economy.util.ConfigUtil;
import me.gerald.economy.util.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.util.ArrayList;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        //gui
        Inventory settingGUI = Bukkit.createInventory(event.getWhoClicked(), 36, ChatColor.AQUA + "Economy Settings");
        //glass
        CustomItem glass = new CustomItem(Material.THIN_GLASS, ChatColor.AQUA + "Free spot", new String[] {ChatColor.WHITE + "Cuz I'm freee..."});
        //player
        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        ItemMeta playerMeta = playerHead.getItemMeta();
        playerMeta.setDisplayName(ChatColor.GOLD + "Player Value");
        ArrayList<String> playerLore = new ArrayList<>();
        playerLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("PlayerValue"));
        playerMeta.setLore(playerLore);
        playerHead.setItemMeta(playerMeta);
        //zombie
        ItemStack zombie = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.ZOMBIE.ordinal());
        ItemMeta zombieMeta = zombie.getItemMeta();
        zombieMeta.setDisplayName(ChatColor.GREEN + "Zombie Value");
        ArrayList<String> zombieLore = new ArrayList<>();
        zombieLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("ZombieValue"));
        zombieMeta.setLore(zombieLore);
        zombie.setItemMeta(zombieMeta);
        //skeleton
        ItemStack skeleton = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.SKELETON.ordinal());
        ItemMeta skeletonMeta = skeleton.getItemMeta();
        skeletonMeta.setDisplayName(ChatColor.GRAY + "Skeleton Value");
        ArrayList<String> skeletonLore = new ArrayList<>();
        skeletonLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("SkeletonValue"));
        skeletonMeta.setLore(skeletonLore);
        skeleton.setItemMeta(skeletonMeta);
        //spider
        CustomItem spider = new CustomItem(Material.SPIDER_EYE, ChatColor.DARK_GRAY + "Spider Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("SpiderValue")});
        //creeper
        ItemStack creeper = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.CREEPER.ordinal());
        ItemMeta creeperMeta = creeper.getItemMeta();
        creeperMeta.setDisplayName(ChatColor.DARK_GREEN + "Creeper Value");
        ArrayList<String> creeperLore = new ArrayList<>();
        creeperLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("CreeperValue"));
        creeperMeta.setLore(creeperLore);
        creeper.setItemMeta(creeperMeta);
        //blaze
        CustomItem blaze = new CustomItem(Material.BLAZE_ROD, ChatColor.GOLD + "Blaze Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("BlazeValue")});
        //witch
        CustomItem witch = new CustomItem(Material.POTION, ChatColor.LIGHT_PURPLE + "Witch Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("WitchValue")});
        //enderman
        CustomItem enderman = new CustomItem(Material.ENDER_PEARL, ChatColor.DARK_PURPLE + "Enderman Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("EndermanValue")});
        //cow
        CustomItem cow = new CustomItem(Material.COOKED_BEEF, ChatColor.GOLD + "Cow Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("CowValue")});
        //sheep
        CustomItem sheep = new CustomItem(Material.WOOL, ChatColor.WHITE + "Wool Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("SheepValue")});
        //pig
        CustomItem pig = new CustomItem(Material.GRILLED_PORK, ChatColor.RED + "Pig Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("PigValue")});
        //chicken
        CustomItem chicken = new CustomItem(Material.COOKED_CHICKEN, ChatColor.WHITE + "Chicken Value", new String[] {ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("ChickenValue")});
        //emerald
        CustomItem emeraldBlock = new CustomItem(Material.EMERALD_BLOCK, ChatColor.GREEN + "Mob Death Drops " + ChatColor.RED + "(You can click me)", new String[] {ChatColor.AQUA + "Toggles mob death drops."});

        ItemStack[] guiItems = {
                glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(),
                playerHead, zombie, skeleton, spider.getItem(), creeper, blaze.getItem(), witch.getItem(), glass.getItem(), glass.getItem(), enderman.getItem(),
                cow.getItem(), sheep.getItem(), pig.getItem(), chicken.getItem(), glass.getItem(), emeraldBlock.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(),
                glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem(), glass.getItem()};
        settingGUI.setContents(guiItems);

        if(event.getInventory().getName().equals(ChatColor.AQUA + "Economy GUI")
                || event.getInventory().getName().equals(ChatColor.AQUA + "Economy GUI " + ChatColor.RED + "OP Mode")) {
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case LAVA_BUCKET:
                    ConfigUtil.reload();
                    player.sendMessage(ChatColor.GREEN + "Reloaded plugin config...");
                    break;
                case EMPTY_MAP:
                    player.closeInventory();
                    player.openInventory(settingGUI);
                    break;
            }
        }else if(event.getInventory().getName().equals(ChatColor.AQUA + "Economy Settings")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.EMERALD_BLOCK) {
                Main.INSTANCE.getConfig().addDefault("MobMoneyDrops", !Main.INSTANCE.getConfig().getBoolean("MobMoneyDrops"));
                Main.INSTANCE.getConfig().set("MobMoneyDrops", !Main.INSTANCE.getConfig().getBoolean("MobMoneyDrops"));
                Main.INSTANCE.saveConfig();
                Main.INSTANCE.reloadConfig();
                player.sendMessage(ChatColor.GREEN + "Set " + ChatColor.YELLOW + "MobMoneyDrops " + ChatColor.GREEN + "to " + ChatColor.AQUA + Main.INSTANCE.getConfig().getBoolean("MobMoneyDrops"));
                player.closeInventory();
                player.openInventory(settingGUI);
            }
        }
    }
}

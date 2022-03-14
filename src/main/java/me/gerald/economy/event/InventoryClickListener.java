package me.gerald.economy.event;

import me.gerald.economy.Main;
import me.gerald.economy.command.CommandManager;
import me.gerald.economy.util.ConfigUtil;
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
        if(event.getInventory().getName().equals(ChatColor.AQUA + "Economy GUI")
                || event.getInventory().getName().equals(ChatColor.AQUA + "Economy GUI " + ChatColor.RED + "OP Mode")
                || event.getInventory().getName().equals(ChatColor.AQUA + "Economy Settings")) {
            event.setCancelled(true);
            switch (event.getCurrentItem().getType()) {
                case LAVA_BUCKET:
                    ConfigUtil.reload();
                    player.sendMessage(ChatColor.GREEN + "Reloaded plugin config...");
                    break;
                case EMPTY_MAP:
                    player.closeInventory();
                    Inventory settingGUI = Bukkit.createInventory(event.getWhoClicked(), 36, ChatColor.AQUA + "Economy Settings");
                    //settings
                    ItemStack glass = new ItemStack(Material.THIN_GLASS, 1);
                    ItemMeta glassMeta = glass.getItemMeta();
                    glassMeta.setDisplayName(ChatColor.AQUA + "Free spot");
                    ArrayList<String> glassLore = new ArrayList<>();
                    glassLore.add(ChatColor.WHITE + "Cuz I'm freeeee...");
                    glassMeta.setLore(glassLore);
                    glass.setItemMeta(glassMeta);
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
                    ItemStack spider = new ItemStack(Material.STRING, 1);
                    ItemMeta spiderMeta = spider.getItemMeta();
                    spiderMeta.setDisplayName(ChatColor.DARK_GRAY + "Spider Value");
                    ArrayList<String> spiderLore = new ArrayList<>();
                    spiderLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("SpiderValue"));
                    spiderMeta.setLore(spiderLore);
                    spider.setItemMeta(spiderMeta);
                    //creeper
                    ItemStack creeper = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.CREEPER.ordinal());
                    ItemMeta creeperMeta = creeper.getItemMeta();
                    creeperMeta.setDisplayName(ChatColor.DARK_GREEN + "Creeper Value");
                    ArrayList<String> creeperLore = new ArrayList<>();
                    creeperLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("CreeperValue"));
                    creeperMeta.setLore(creeperLore);
                    creeper.setItemMeta(creeperMeta);
                    //blaze
                    ItemStack blaze = new ItemStack(Material.BLAZE_ROD, 1);
                    ItemMeta blazeMeta = blaze.getItemMeta();
                    blazeMeta.setDisplayName(ChatColor.GOLD + "Blaze Value");
                    ArrayList<String> blazeLore = new ArrayList<>();
                    blazeLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("BlazeValue"));
                    blazeMeta.setLore(blazeLore);
                    blaze.setItemMeta(blazeMeta);
                    //witch
                    ItemStack witch = new ItemStack(Material.POTION, 1);
                    ItemMeta witchMeta = witch.getItemMeta();
                    witchMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Witch Value");
                    ArrayList<String> witchLore = new ArrayList<>();
                    witchLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("WitchValue"));
                    witchMeta.setLore(witchLore);
                    witch.setItemMeta(witchMeta);
                    //enderman
                    ItemStack enderman = new ItemStack(Material.ENDER_PEARL, 1);
                    ItemMeta endermanMeta = enderman.getItemMeta();
                    endermanMeta.setDisplayName(ChatColor.DARK_PURPLE + "Enderman Value");
                    ArrayList<String> endermanLore = new ArrayList<>();
                    endermanLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("EndermanValue"));
                    endermanMeta.setLore(endermanLore);
                    enderman.setItemMeta(endermanMeta);
                    //cow
                    ItemStack cow = new ItemStack(Material.COOKED_BEEF, 1);
                    ItemMeta cowMeta = cow.getItemMeta();
                    cowMeta.setDisplayName(ChatColor.GOLD + "Cow Value");
                    ArrayList<String> cowLore = new ArrayList<>();
                    cowLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("CowValue"));
                    cowMeta.setLore(cowLore);
                    cow.setItemMeta(cowMeta);
                    //sheep
                    ItemStack sheep = new ItemStack(Material.WOOL, 1);
                    ItemMeta sheepMeta = sheep.getItemMeta();
                    sheepMeta.setDisplayName(ChatColor.WHITE + "Sheep Value");
                    ArrayList<String> sheepLore = new ArrayList<>();
                    sheepLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("SheepValue"));
                    sheepMeta.setLore(sheepLore);
                    sheep.setItemMeta(sheepMeta);
                    //pig
                    ItemStack pig = new ItemStack(Material.GRILLED_PORK, 1);
                    ItemMeta pigMeta = pig.getItemMeta();
                    pigMeta.setDisplayName(ChatColor.RED + "Pig Value");
                    ArrayList<String> pigLore = new ArrayList<>();
                    pigLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("PigValue"));
                    pigMeta.setLore(pigLore);
                    pig.setItemMeta(pigMeta);
                    //chicken
                    ItemStack chicken = new ItemStack(Material.COOKED_CHICKEN, 1);
                    ItemMeta chickenMeta = chicken.getItemMeta();
                    chickenMeta.setDisplayName(ChatColor.WHITE + "Chicken Value");
                    ArrayList<String> chickenLore = new ArrayList<>();
                    chickenLore.add(ChatColor.AQUA + "$" + Main.INSTANCE.getConfig().getInt("ChickenValue"));
                    chickenMeta.setLore(chickenLore);
                    chicken.setItemMeta(chickenMeta);

                    ItemStack[] guiItems = {glass, glass, glass, glass, glass, glass, glass, glass, glass,
                            glass, playerHead, zombie, skeleton, spider, creeper, blaze, witch, glass,
                            glass, enderman, cow, sheep, pig, chicken, glass, glass,
                            glass, glass, glass, glass, glass, glass, glass, glass, glass, glass};
                    settingGUI.setContents(guiItems);
                    player.openInventory(settingGUI);
                    break;
            }
        }
    }
}

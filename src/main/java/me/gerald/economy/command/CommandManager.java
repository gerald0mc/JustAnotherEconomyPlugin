package me.gerald.economy.command;

import me.gerald.economy.Main;
import me.gerald.economy.util.ConfigUtil;
import me.gerald.economy.util.CustomItem;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Map;


public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        switch (args[0]) {
            //a help menu    
            case "help":
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Economy " + ChatColor.GREEN + "Help Menu");
                sender.sendMessage(ChatColor.AQUA + "[help]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Sends the help menu in chat.");
                sender.sendMessage(ChatColor.AQUA + "[balance]" + (sender.hasPermission("economy.op") ? " <player>" : "") + ChatColor.GRAY + ": " + ChatColor.GREEN + "Shows the balance of the player.");
                sender.sendMessage(ChatColor.AQUA + "[send] <target> <amount>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Sends a player an amount of money.");
                sender.sendMessage(ChatColor.AQUA + "[shop] [add <price>, list, buy <playerName>, remove]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Shop command as well as all the commands linked to it.");
                sender.sendMessage(ChatColor.AQUA + "[gui]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Shows the plugin GUI.");
                if(sender.hasPermission("economy.op")) {
                    sender.sendMessage(ChatColor.GREEN + "====" + ChatColor.RED + "Secret OP Powers" + ChatColor.GREEN + "====");
                    sender.sendMessage(ChatColor.AQUA + "[set] <entity> <value>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Set the value of a entity setting.");
                    sender.sendMessage(ChatColor.AQUA + "[list]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Lists all settings in the plugin.");
                    sender.sendMessage(ChatColor.AQUA + "[remove] <player>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Removes a players shop listing from the shop.");
                    sender.sendMessage(ChatColor.AQUA + "[setbal] <player> <value>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Set the players balance to value.");
                    sender.sendMessage(ChatColor.AQUA + "[reload]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Reloads the config of the plugin.");
                }
                return true;
            //shows the gui for the plugin    
            case "gui":
                Inventory gui = Bukkit.createInventory(player, 9, ChatColor.AQUA + "Economy GUI " + (sender.hasPermission("economy.op") ? ChatColor.RED + "OP Mode" : ""));
                //glass
                CustomItem glass = new CustomItem(Material.THIN_GLASS, ChatColor.AQUA + "Free spot", new String[] {ChatColor.WHITE + "Cuz I'm freee..."});
                //player info
                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                ItemMeta skullMeta = skull.getItemMeta();
                skullMeta.setDisplayName(ChatColor.YELLOW + player.getDisplayName() + ChatColor.WHITE + " Info");
                ArrayList<String> skullLore = new ArrayList<>();
                skullLore.add(ChatColor.WHITE + "Coords" + ChatColor.GRAY + ": " + ChatColor.AQUA + "X:" + player.getLocation().getBlock().getX() + " Y:" + player.getLocation().getBlock().getY() + " Z:" + player.getLocation().getBlock().getZ());;
                skullLore.add(ChatColor.WHITE + "Is opp?" + ChatColor.GRAY + ": " + ChatColor.AQUA + (sender.hasPermission("economy.op") ? "True" : "False"));
                skullMeta.setLore(skullLore);
                skull.setItemMeta(skullMeta);
                //balance
                CustomItem emerald = new CustomItem(Material.EMERALD, ChatColor.GREEN + "Balance", new String[] {ChatColor.AQUA + "$" + ConfigUtil.getBalance().getInt(player.getDisplayName() + " Balance")});
                //reload
                CustomItem lava = new CustomItem(Material.LAVA_BUCKET, ChatColor.RED + "Reload", new String[] {ChatColor.WHITE + "Reloads the plugin config."});
                //list settings
                CustomItem map = new CustomItem(Material.EMPTY_MAP, ChatColor.AQUA + "List Settings", new String[] {ChatColor.WHITE + "Shows all settings for the plugin and their values."});

                ItemStack[] menuItems = {glass.getItem(),
                        glass.getItem(),
                        skull,
                        emerald.getItem(),
                        glass.getItem(),
                        (sender.hasPermission("economy.op") ? lava.getItem() : glass.getItem()),
                        (sender.hasPermission("economy.op") ? map.getItem() : glass.getItem()),
                        glass.getItem(),
                        glass.getItem()};
                gui.setContents(menuItems);
                player.openInventory(gui);
                return true;
            //gets the player/players balance    
            case "balance":
                int balance = ConfigUtil.getBalance().getInt(player.getDisplayName() + " Balance");
                if(sender.hasPermission("economy.op")) {
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.GREEN + "Balance" + ChatColor.GRAY + ": " + ChatColor.AQUA + "$" + balance);
                    }else {
                        String targetString = args[1];
                        Player target = Bukkit.getPlayer(targetString);
                        if(target == null) {
                            sender.sendMessage(ChatColor.RED + "Please make sure the player is online.");
                            return true;
                        }
                        int targetBalance = ConfigUtil.getBalance().getInt(target.getDisplayName() + " Balance");
                        sender.sendMessage(ChatColor.GREEN + "Balance of " + ChatColor.YELLOW + target.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.AQUA + "$" + targetBalance);
                    }
                }else {
                    sender.sendMessage(ChatColor.GREEN + "Balance" + ChatColor.GRAY + ": " + ChatColor.AQUA + "$" + balance);
                }
                return true;
            //send another player money    
            case "send":
                if(args.length == 1) {
                    sender.sendMessage(ChatColor.RED + "Please enter what person you wish to send money to.");
                    return true;
                }
                String targetString = args[1];
                Player target = Bukkit.getPlayer(targetString);
                if(target == null) {
                    sender.sendMessage(ChatColor.RED + "Please make sure the player is online please.");
                    return true;
                }
                if(args.length == 2) {
                    sender.sendMessage(ChatColor.RED + "Please specify how much you would like to send the other player.");
                    return true;
                }
                int amountBeingSent = Integer.parseInt(args[2]);
                ConfigUtil.getBalance().addDefault(player.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(player.getDisplayName() + " Balance") - amountBeingSent);
                ConfigUtil.getBalance().set(player.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(player.getDisplayName() + "Balance") - amountBeingSent);
                sender.sendMessage(ChatColor.GREEN + "Sent " + ChatColor.YELLOW + target.getDisplayName() + " " + ChatColor.AQUA + "$" + amountBeingSent);
                ConfigUtil.getBalance().addDefault(target.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(target.getDisplayName() + " Balance") + amountBeingSent);
                ConfigUtil.getBalance().set(target.getDisplayName() + " Balance", ConfigUtil.getBalance().getInt(target.getDisplayName() + "Balance") + amountBeingSent);
                target.sendMessage(ChatColor.GREEN + "Sent " + ChatColor.AQUA + "$" + amountBeingSent + ChatColor.GREEN + " by " + ChatColor.YELLOW + player.getDisplayName());
                return true;
            //shop header    
            case "shop":
                switch (args[1]) {
                    //add a item to the shop    
                    case "add":
                        if(player.getItemInHand().getType() == Material.AIR) {
                            sender.sendMessage(ChatColor.RED + "Please be holding the item you wish to put up for sale.");
                            return true;
                        }
                        if(args.length == 2) {
                            sender.sendMessage(ChatColor.RED + "Please specify how much you would like to post your item for.");
                            return true;
                        }
                        int price1 = Integer.parseInt(args[2]);
                        if(ConfigUtil.getShop().isSet(player.getDisplayName() + ".item")) {
                            sender.sendMessage(ChatColor.RED + "You already have a active listing.");
                            return true;
                        }
                        ConfigUtil.getShop().addDefault(player.getDisplayName() + ".item", player.getItemInHand().getType().name());
                        ConfigUtil.getShop().set(player.getDisplayName() + ".item", player.getItemInHand().getType().name());
                        ConfigUtil.getShop().addDefault(player.getDisplayName() + ".value", price1);
                        ConfigUtil.getShop().set(player.getDisplayName() + ".value", price1);
                        ConfigUtil.getShop().addDefault(player.getDisplayName() + ".quantity", player.getItemInHand().getAmount());
                        ConfigUtil.getShop().set(player.getDisplayName() + ".quantity", player.getItemInHand().getAmount());
                        ConfigUtil.getShop().addDefault(player.getDisplayName() + ".durability", player.getItemInHand().getDurability());
                        ConfigUtil.getShop().set(player.getDisplayName() + ".durability", player.getItemInHand().getDurability());
                        ConfigUtil.save();
                        ConfigUtil.reload();
                        sender.sendMessage(ChatColor.GREEN + "Posted item to shop.");
                        player.setItemInHand(new ItemStack(Material.AIR));
                        return true;
                    //lists all things    
                    case "list":
                        sender.sendMessage(ChatColor.LIGHT_PURPLE + "Economy" + ChatColor.GREEN + " Shop" + ChatColor.GRAY + ":");
                        for(Map.Entry<String, Object> entry : ConfigUtil.getShop().getValues(true).entrySet()) {
                            Material item = Material.AIR;
                            int value = 0;
                            int quantity = 0;
                            short durability = 0;
                            if(entry.getKey().contains("item") || entry.getKey().contains("value") || entry.getKey().contains("quantity") || entry.getKey().contains("durability")) {
                                continue;
                            }
                            for(Map.Entry<String, Object> entry1 : ConfigUtil.getShop().getConfigurationSection(entry.getKey()).getValues(true).entrySet()) {
                                switch (entry1.getKey()) {
                                    case "item":
                                        item = Material.getMaterial((String) entry1.getValue());
                                        break;
                                    case "value":
                                        value = (int) entry1.getValue();
                                        break;
                                    case "quantity":
                                        quantity = (int) entry1.getValue();
                                        break;
                                    case "durability":
                                        int value1 = (int) entry1.getValue();
                                        durability = (short) value1;
                                        break;
                                }
                            }
                            sender.sendMessage(ChatColor.GREEN + "Item" + ChatColor.GRAY + ": [" + ChatColor.AQUA + item.name() + ChatColor.GRAY + "]" + ChatColor.GREEN + " Durability" + ChatColor.GRAY + ": [" + ChatColor.AQUA + durability + ChatColor.GRAY + "]" + ChatColor.GREEN + " Quantity" + ChatColor.GRAY + ": [" + ChatColor.AQUA + quantity + ChatColor.GRAY + "]" + ChatColor.GREEN + " Seller" + ChatColor.GRAY + ": [" + ChatColor.AQUA + entry.getKey() + ChatColor.GRAY + "]" + ChatColor.GREEN + " Value" + ChatColor.GRAY + ": [" + ChatColor.AQUA + value + ChatColor.GRAY + "]");
                        }
                        return true;
                    //buys a item    
                    case "buy":
                        if(args.length == 2) {
                            sender.sendMessage(ChatColor.RED + "Please specify which player's item you are trying to buy.");
                            return true;
                        }
                        String owner = args[2];
                        if(player.getDisplayName().equalsIgnoreCase(owner)) {
                            sender.sendMessage(ChatColor.RED + "You can't buy your own item off the market.");
                            return true;
                        }
                        if(ConfigUtil.getShop().isSet(owner)) {
                            Material item = Material.getMaterial(ConfigUtil.getShop().getString(owner + ".item"));
                            int price = ConfigUtil.getShop().getInt(owner + ".value");
                            int quantity = ConfigUtil.getShop().getInt(owner + ".quantity");
                            int dura = ConfigUtil.getShop().getInt(owner + ".durability");
                            short durability = (short) dura;
                            ItemStack newItem = new ItemStack(item, quantity);
                            newItem.setDurability(durability);
                            if(ConfigUtil.getBalance().getInt(player.getDisplayName() + " Balance") < price) {
                                sender.sendMessage(ChatColor.RED + "You need " + ChatColor.AQUA + (price - ConfigUtil.getBalance().getInt(player.getDisplayName() + " Balance")) + ChatColor.RED + " more $ to buy this.");
                                return true;
                            }
                            ConfigUtil.getBalance().set(player.getDisplayName() + " Balance", (ConfigUtil.getBalance().getInt(player.getDisplayName() + " Balance") - price));
                            player.setItemInHand(newItem);
                            sender.sendMessage(ChatColor.GREEN + "Successfully bought the item.");
                            ConfigUtil.getShop().set(owner + ".item", null);
                            ConfigUtil.getShop().set(owner + ".value", null);
                            ConfigUtil.getShop().set(owner + ".quantity", null);
                            ConfigUtil.getShop().set(owner + ".durability", null);
                            ConfigUtil.getShop().set(owner, null);
                            ConfigUtil.save();
                            ConfigUtil.reload();
                        }else {
                            sender.sendMessage(ChatColor.RED + "There is no active listing from that player.");
                        }
                        return true;
                    //removes yours or a players listing from the shop    
                    case "remove":
                        switch (args.length) {
                            case 2:
                                if(ConfigUtil.getShop().isSet(player.getDisplayName() + ".item")) {
                                    Material item = Material.getMaterial(ConfigUtil.getShop().getString(player.getDisplayName() + ".item"));
                                    int quantity = ConfigUtil.getShop().getInt(player.getDisplayName() + ".quantity");
                                    int dura = ConfigUtil.getShop().getInt(player.getDisplayName() + ".durability");
                                    short durability = (short) dura;
                                    ItemStack newItem = new ItemStack(item, quantity);
                                    newItem.setDurability(durability);
                                    if(player.getItemInHand().getType() != Material.AIR) {
                                        sender.sendMessage(ChatColor.RED + "Please make sure you have a empty hand.");
                                        return true;
                                    }
                                    ConfigUtil.getShop().set(player.getDisplayName() + ".item", null);
                                    ConfigUtil.getShop().set(player.getDisplayName() + ".value", null);
                                    ConfigUtil.getShop().set(player.getDisplayName() + ".quantity", null);
                                    ConfigUtil.getShop().set(player.getDisplayName() + ".durability", null);
                                    ConfigUtil.getShop().set(player.getDisplayName(), null);
                                    ConfigUtil.save();
                                    ConfigUtil.reload();
                                    player.setItemInHand(newItem);
                                    sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.AQUA + player.getDisplayName() + ChatColor.GREEN + " from the shop.");
                                }else {
                                    sender.sendMessage(ChatColor.RED + "You don't have a active listing to remove.");
                                }
                                return true;
                            case 3:
                                if(sender.hasPermission("economy.op")) {
                                    String targetPlayer = args[2];
                                    if(ConfigUtil.getShop().isSet(targetPlayer + ".item")) {
                                        ConfigUtil.getShop().set(targetPlayer + ".item", null);
                                        ConfigUtil.getShop().set(targetPlayer + ".value", null);
                                        ConfigUtil.getShop().set(targetPlayer + ".quantity", null);
                                        if(ConfigUtil.getShop().isSet(targetPlayer + ".durability")) {
                                            ConfigUtil.getShop().set(targetPlayer + ".durability", null);
                                        }
                                        ConfigUtil.getShop().set(targetPlayer, null);
                                        ConfigUtil.save();
                                        ConfigUtil.reload();
                                        sender.sendMessage(ChatColor.GREEN + "Removed " + ChatColor.AQUA + targetPlayer + ChatColor.GREEN + " from the shop.");
                                    }else {
                                        sender.sendMessage(ChatColor.RED + "Shop doesn't seem to contain this person please make sure you are typing it in with everything correct (Caps included)");
                                    }
                                }else {
                                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                                }
                                return true;
                        }
                }
                return true;
            //op commands
            //set a settings value in game    
            case "set":
                if(sender.hasPermission("economy.op")) {
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.RED + "Please specify what entities value you would to set.");
                        return true;
                    }
                    String entity = args[1];
                    String entityUppercase = entity.substring(0, 1).toUpperCase() + entity.substring(1);
                    String setting = entityUppercase + "Value";
                    if(args.length == 2) {
                        sender.sendMessage(ChatColor.RED + "Please specify what value you would like to set " + entity + " to.");
                        return true;
                    }
                    String value = args[2];
                    if(Main.INSTANCE.getConfig().contains(setting)) {
                        Main.INSTANCE.getConfig().addDefault(setting, Integer.valueOf(value));
                        Main.INSTANCE.getConfig().set(setting, Integer.valueOf(value));
                        Main.INSTANCE.saveConfig();
                        Main.INSTANCE.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Set " + ChatColor.YELLOW + setting + ChatColor.GREEN + " to " + ChatColor.AQUA + "$" + value);
                    }else {
                        sender.sendMessage(ChatColor.RED + "That setting doesn't exist.");
                    }
                }else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
                return true;
            //list all settings and their corresponding values    
            case "list":
                if(sender.hasPermission("economy.op")) {
                    sender.sendMessage(ChatColor.LIGHT_PURPLE + "Thing " + ChatColor.GREEN + "Settings");
                    for(Map.Entry<String, Object> entry : Main.INSTANCE.getConfig().getValues(true).entrySet()) {
                        sender.sendMessage(ChatColor.GREEN + entry.getKey() + ChatColor.GRAY + ": " + ChatColor.AQUA + entry.getValue());
                    }
                }else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
                return true;
            //set a players balance    
            case "setbal":
                if(sender.hasPermission("economy.op")) {
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.RED + "Please input the player whose balance you wish to set.");
                        return true;
                    }
                    String target1 = args[1];
                    Player player1 = Bukkit.getPlayer(target1);
                    if(player1 == null) {
                        sender.sendMessage(ChatColor.RED + "Please make sure the player is online if not change in config.");
                        return true;
                    }
                    if(args.length == 2) {
                        sender.sendMessage(ChatColor.RED + "Please input how much you wish to set " + ChatColor.AQUA + player1.getDisplayName() + "'s " + ChatColor.RED + "balance to.");
                        return true;
                    }
                    int newBalance = Integer.parseInt(args[2]);
                    ConfigUtil.getBalance().addDefault(player1.getDisplayName() + " Balance", newBalance);
                    ConfigUtil.getBalance().set(player1.getDisplayName() + " Balance", newBalance);
                    ConfigUtil.save();
                    sender.sendMessage(ChatColor.GREEN + "Set " + ChatColor.YELLOW + player1.getDisplayName() + "'s " + ChatColor.GREEN + " balance to " + ChatColor.AQUA + "$" + ConfigUtil.getBalance().getInt(player1.getDisplayName() + " Balance"));
                }else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
                return true;
            //reload the config    
            case "reload":
                if(sender.hasPermission("economy.op")) {
                    ConfigUtil.reload();
                    sender.sendMessage(ChatColor.GREEN + "Reloaded plugin config...");
                }else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                }
                return true;
        }
        return false;
    }
}

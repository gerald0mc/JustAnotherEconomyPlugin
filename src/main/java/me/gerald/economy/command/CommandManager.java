package me.gerald.economy.command;

import me.gerald.economy.Main;
import me.gerald.economy.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;

import java.util.Map;


public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        switch (args[0]) {
            case "help":
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Economy " + ChatColor.GREEN + "Help Menu");
                sender.sendMessage(ChatColor.AQUA + "[help]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Sends the help menu in chat.");
                sender.sendMessage(ChatColor.AQUA + "[balance]" + (sender.hasPermission("economy.op") ? " <player>" : "") + ChatColor.GRAY + ": " + ChatColor.GREEN + "Shows the balance of the player.");
                sender.sendMessage(ChatColor.AQUA + "[send] <target> <amount>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Sends a player an amount of money.");
                if(sender.hasPermission("economy.op")) {
                    sender.sendMessage(ChatColor.GREEN + "====" + ChatColor.RED + "Secret OP Powers" + ChatColor.GREEN + "====");
                    sender.sendMessage(ChatColor.AQUA + "[set] <entity> <value>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Set the value of a entity setting.");
                    sender.sendMessage(ChatColor.AQUA + "[list]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Lists all settings in the plugin.");
                    sender.sendMessage(ChatColor.AQUA + "[setbal] <player> <value>" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Set the players balance to value.");
                    sender.sendMessage(ChatColor.AQUA + "[reload]" + ChatColor.GRAY + ": " + ChatColor.GREEN + "Reloads the config of the plugin.");
                }
                return true;
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
            //op commands
            case "set":
                if(sender.hasPermission("economy.op")) {
                    if(args.length == 1) {
                        sender.sendMessage(ChatColor.RED + "Please specify what entities value you would to set.");
                        return true;
                    }
                    String entity = args[1];
                    String setting = getSetting(entity);
                    if(args.length == 2) {
                        sender.sendMessage(ChatColor.RED + "Please specify what value you would like to set " + entity + " to.");
                        return true;
                    }
                    String value = args[2];
                    if(!setting.equalsIgnoreCase("null")) {
                        Main.INSTANCE.getConfig().addDefault(setting, Integer.valueOf(value));
                        Main.INSTANCE.getConfig().set(setting, Integer.valueOf(value));
                        Main.INSTANCE.saveConfig();
                        Main.INSTANCE.reloadConfig();
                        sender.sendMessage(ChatColor.GREEN + "Set " + ChatColor.YELLOW + setting + ChatColor.GREEN + " to " + ChatColor.AQUA + "$" + value);
                    }else {
                        sender.sendMessage(ChatColor.RED + "That setting doesn't exist.");
                    }
                    return true;
                }else {
                    sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
                    return true;
                }
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

    public String getSetting(String setting) {
        if(setting.equalsIgnoreCase("player")) {
            return "PlayerValue";
        }else if(setting.equalsIgnoreCase("zombie")) {
            return "ZombieValue";
        }else if(setting.equalsIgnoreCase("skeleton")) {
            return "SkeletonValue";
        }else if(setting.equalsIgnoreCase("spider")) {
            return "SpiderValue";
        }else if(setting.equalsIgnoreCase("creeper")) {
            return "CreeperValue";
        }else if(setting.equalsIgnoreCase("blaze")) {
            return "BlazeValue";
        }else if(setting.equalsIgnoreCase("witch")) {
            return "WitchValue";
        }else if(setting.equalsIgnoreCase("enderman")) {
            return "EndermanValue";
        }else if(setting.equalsIgnoreCase("cow")) {
            return "CowValue";
        }else if(setting.equalsIgnoreCase("sheep")) {
            return "SheepValue";
        }else if(setting.equalsIgnoreCase("pig")) {
            return "PigValue";
        }else if(setting.equalsIgnoreCase("chicken")) {
            return "ChickenValue";
        }else {
            return "null";
        }
    }
}

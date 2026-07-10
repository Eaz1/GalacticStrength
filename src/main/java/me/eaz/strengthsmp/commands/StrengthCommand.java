package me.eaz.strengthsmp.commands;

import me.eaz.strengthsmp.StrengthSMP;
import me.eaz.strengthsmp.managers.StrengthManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class StrengthCommand implements CommandExecutor {

    private final StrengthSMP plugin;
    private final StrengthManager manager;

    public StrengthCommand(StrengthSMP plugin) {
        this.plugin = plugin;
        this.manager = plugin.getStrengthManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command.");
                return true;
            }

            Player player = (Player) sender;

            double bonus = manager.getStrength(player) / 2.0;

            player.sendMessage(ChatColor.GREEN + "Your Strength Bonus: "
                    + ChatColor.YELLOW + bonus + "❤");

            return true;
        }


        if (args[0].equalsIgnoreCase("top")) {

            sender.sendMessage(ChatColor.GOLD + "=== Strength Leaderboard ===");

            List<Map.Entry<UUID, Double>> list =
                    new ArrayList<>(manager.getAllStrength().entrySet());

            list.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

            int place = 1;

            for (Map.Entry<UUID, Double> entry : list) {

                if (place > 10) break;

                String name = Bukkit.getOfflinePlayer(entry.getKey()).getName();

                sender.sendMessage(
                        ChatColor.YELLOW + "#" + place +
                        " " + ChatColor.WHITE + name +
                        " - " + ChatColor.GREEN +
                        (entry.getValue() / 2.0) + "❤"
                );

                place++;
            }

            return true;
        }
              if (args[0].equalsIgnoreCase("resetall")) {

            if (!sender.hasPermission("strengthsmp.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }

            manager.resetAll();

            sender.sendMessage(ChatColor.GREEN + "All player strength has been reset.");
            return true;
        }


        if (args[0].equalsIgnoreCase("set")) {

            if (!sender.hasPermission("strengthsmp.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }

            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "/strength set <player> <damage>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            double amount;

            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number.");
                return true;
            }

            manager.setStrength(target, amount);

            sender.sendMessage(ChatColor.GREEN + "Set "
                    + target.getName() + "'s strength to "
                    + amount / 2.0 + "❤");

            return true;
                               }
              if (args[0].equalsIgnoreCase("add")) {

            if (!sender.hasPermission("strengthsmp.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }

            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "/strength add <player> <damage>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            double amount;

            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number.");
                return true;
            }

            manager.setStrength(target, manager.getStrength(target) + amount);

            sender.sendMessage(ChatColor.GREEN + "Added "
                    + amount / 2.0 + "❤ strength to "
                    + target.getName());

            return true;
        }


        if (args[0].equalsIgnoreCase("remove")) {

            if (!sender.hasPermission("strengthsmp.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission.");
                return true;
            }

            if (args.length < 3) {
                sender.sendMessage(ChatColor.RED + "/strength remove <player> <damage>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[1]);

            if (target == null) {
                sender.sendMessage(ChatColor.RED + "Player not found.");
                return true;
            }

            double amount;

            try {
                amount = Double.parseDouble(args[2]);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "Invalid number.");
                return true;
            }

            manager.setStrength(target, manager.getStrength(target) - amount);

            sender.sendMessage(ChatColor.GREEN + "Removed "
                    + amount / 2.0 + "❤ strength from "
                    + target.getName());

            return true;
        }
              sender.sendMessage(ChatColor.RED + "Unknown command.");
        return true;
    }
}

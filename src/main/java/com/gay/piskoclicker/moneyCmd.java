package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class moneyCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (sender.hasPermission("admin")) {
                switch (args.length) {
                    case (0):
                        p.sendMessage(ChatColor.GOLD + "Your amount of dicks: " + ChatColor.LIGHT_PURPLE + (int) Double.parseDouble(Events.getBalance(p)));
                        break;
                    case (1):
                        p.sendMessage(ChatColor.GOLD + args[0] + "'s amount of dicks: " + ChatColor.LIGHT_PURPLE + Events.getBalance(Bukkit.getServer().getPlayer(args[0])));
                        break;
                    case (3):
                        if (checkString(args[2])) {
                            p.sendMessage(ChatColor.RED + "Вводимое значение должно быть числом!");
                            return true;
                        }
                        double val = Double.parseDouble(args[2]);
                        Events.addBalance(Bukkit.getServer().getPlayer(args[1]), val);
                        p.sendMessage(ChatColor.GOLD + args[1] + "'s amount of dicks: " + ChatColor.LIGHT_PURPLE + Events.getBalance(Bukkit.getServer().getPlayer(args[1])));
                        break;
                }
            } else {
                p.sendMessage(ChatColor.GOLD + "Your amount of dicks: " + ChatColor.LIGHT_PURPLE + (int) Double.parseDouble(Events.getBalance(p)));
            }
        }
        return true;
    }

    public boolean checkString(String string) {
        try {
            Double.parseDouble(string);
        } catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
}

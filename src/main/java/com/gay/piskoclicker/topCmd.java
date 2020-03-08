package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.UUID;

public class topCmd implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            HashSet<UUID> uuids = new HashSet<>();
            for (OfflinePlayer player1 : Bukkit.getServer().getOfflinePlayers()) uuids.add(player1.getUniqueId());
            for (Player player2 : Bukkit.getServer().getOnlinePlayers()) uuids.add(player2.getUniqueId());
            UUID[] realUUIDs = new UUID[uuids.size()];
            UUID inUUID;
            for (int i = 0; i < uuids.size(); i++) {
                for (int j = i + 1; j < uuids.size(); j++) {
                    if (Events.getBalanceDoubleUUID(realUUIDs[i]) < Events.getBalanceDoubleUUID(realUUIDs[j])) {
                        inUUID = realUUIDs[i];
                        realUUIDs[i] = realUUIDs[j];
                        realUUIDs[j] = inUUID;
                    }
                }
            }
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                if (i > realUUIDs.length) {
                    player.sendMessage(builder.toString());
                    return true;
                }
                switch (i) {
                    case (0):
                        if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(realUUIDs[i])))
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.YELLOW).append(Bukkit.getServer().getPlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        else
                            System.out.println(realUUIDs[i]);
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.YELLOW).append(Bukkit.getServer().getOfflinePlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        break;
                    case (1):
                        if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(realUUIDs[i])))
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.WHITE).append(Bukkit.getServer().getPlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        else
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.WHITE).append(Bukkit.getServer().getOfflinePlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        break;
                    case (2):
                        if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(realUUIDs[i])))
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.GOLD).append(Bukkit.getServer().getPlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        else
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.GOLD).append(Bukkit.getServer().getOfflinePlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        break;
                    default:
                        if (Bukkit.getServer().getOnlinePlayers().contains(Bukkit.getServer().getPlayer(realUUIDs[i])))
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.GRAY).append(Bukkit.getServer().getPlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        else
                            builder.append(" ").append(ChatColor.RED).append(ChatColor.BOLD).append(i + 1).append(". ").append(ChatColor.GRAY).append(Bukkit.getServer().getOfflinePlayer(realUUIDs[i]).getName()).append(ChatColor.RESET).append(ChatColor.GOLD).append(" [").append(ChatColor.LIGHT_PURPLE).append((int) Events.getBalanceDoubleUUID(realUUIDs[i])).append(ChatColor.GOLD).append("]").append(ChatColor.RESET).append("\n");
                        break;
                }
            }
            player.sendMessage(builder.toString());
        }
        return true;
    }

}

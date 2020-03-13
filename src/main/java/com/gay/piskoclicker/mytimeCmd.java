package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class mytimeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender,@NotNull Command cmd,@NotNull String label,@NotNull String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            long sec, min, hour, day, time;
            time = Events.getPlayedTime(player);
            if (player.hasPermission("admin")) {
                if (args.length == 0) {
                    sec = time % 60;
                    min = ((time - sec)/60) % 60;
                    hour = ((time - min*60 - sec)/3600) % 24;
                    day = (time - hour*3600 - min*60 - sec)/86400;
                    player.sendMessage("Ты отыграл: " +
                            (day > 0 ? day + " days, " : "") +
                            (hour > 0 ? hour + " hours, " : "") +
                            (min > 0 ? min + " minutes, " : "") +
                            (sec > 0 ? sec + " seconds" : ""));
                } else {
                    time = Events.getPlayedTime(Objects.requireNonNull(Bukkit.getServer().getPlayer(args[0])));
                    sec = time % 60;
                    min = ((time - sec)/60) % 60;
                    hour = ((time - min*60 - sec)/3600) % 24;
                    day = (time - hour*3600 - min*60 - sec)/86400;
                    player.sendMessage("Ты отыграл: " +
                            (day > 0 ? day + " days, " : "") +
                            (hour > 0 ? hour + " hours, " : "") +
                            (min > 0 ? min + " minutes, " : "") +
                            (sec > 0 ? sec + " seconds" : ""));
                }
            } else {
                sec = time % 60;
                min = ((time - sec)/60) % 60;
                hour = ((time - min*60 - sec)/3600) % 24;
                day = (time - hour*3600 - min*60 - sec)/86400;
                player.sendMessage("Ты отыграл: " +
                        (day > 0 ? day + " days, " : "") +
                        (hour > 0 ? hour + " hours, " : "") +
                        (min > 0 ? min + " minutes, " : "") +
                        (sec > 0 ? sec + " seconds" : ""));
            }
        }
        return true;
    }
}

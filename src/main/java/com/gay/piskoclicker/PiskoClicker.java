package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class PiskoClicker extends JavaPlugin {

    public static PiskoClicker instance;
    public static PiskoClicker getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        getLogger().info(ChatColor.AQUA + "PiskoClicker successfully enabled! Loading config file...");
        loadConfig();
        getLogger().info(ChatColor.GREEN + "The config was successfully loaded!");
        getCommand("money").setExecutor(new moneyCmd());
        getCommand("top").setExecutor(new topCmd());
        for (String str : getConfig().getStringList("Users.")) {
            getConfig().set("Users." + str + ".helloDelay", true);
        }

    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            getConfig().set("Users." + player.getUniqueId() + ".helloDelay", true);
            PiskoClicker.getInstance().saveConfig();
        }
        for (OfflinePlayer player : Bukkit.getServer().getOfflinePlayers()) {
            getConfig().set("Users." + player.getUniqueId() + ".helloDelay", true);
            PiskoClicker.getInstance().saveConfig();
        }
    }

    public void loadConfig () {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}

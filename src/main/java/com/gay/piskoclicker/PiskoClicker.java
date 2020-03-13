package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class PiskoClicker extends JavaPlugin {

    public static PiskoClicker instance;
    public static PiskoClicker getInstance() {
        return instance;
    }


    @Override
    public void onEnable() {
        instance = this;
        this.getServer().getPluginManager().registerEvents(new Events(), this);
        Events events = new Events();
        events.startBoosters();
        getLogger().info(ChatColor.AQUA + "PiskoClicker successfully enabled! Loading config file...");
        loadConfig();
        getLogger().info(ChatColor.GREEN + "The config was successfully loaded!");
        Objects.requireNonNull(getCommand("money")).setExecutor(new moneyCmd());
        Objects.requireNonNull(getCommand("top")).setExecutor(new topCmd());
        Objects.requireNonNull(getCommand("mytime")).setExecutor(new mytimeCmd());
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
    }

    public void loadConfig () {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

}

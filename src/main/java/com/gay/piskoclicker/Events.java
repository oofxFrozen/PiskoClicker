package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Objects;

public class Events implements Listener {

    public void setupBoosters(Player p) {
        if (PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.1.multiplier") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.1.lvl", 0);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.1.cost", 100);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.1.multiplier", 0.1);
        }
        if (PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.2.multiplier") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.2.lvl", 0);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.2.cost", 2000);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.2.multiplier", 1);
        }
    }
    public void setupAfkBoosters(Player p) {
        if (PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".afkBoosters.1.multiplier") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".afkBoosters.1.lvl", 0);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".afkBoosters.1.cost", 10000);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".afkBoosters.1.multiplier", 5);
        }
    }

    public static Long getPlayedTime (Player player) {
        return PiskoClicker.getInstance().getConfig().getLong("Users." + player.getUniqueId() + ".playedTime");
    }
    public void addTime (Player player) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".playedTime", getPlayedTime(player)+1);
    }

    public void buyBooster (Player player, int boosterID) {
        DickInv i = new DickInv();
        if (Double.parseDouble(getBalance(player)) >= getCost(player, boosterID)) {
            addBalance(player, -getCost(player, boosterID));
            setLevel(player, boosterID);
            setCost(player, boosterID);
            setMultiplier(player, getBoosterMultiplier(player, 1, boosterID));
            i.openPiskaInventory(player);
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.DARK_RED + "У Вас не хватает средств на покупку данного бустера!");
        }
    }
    public void buyAfkBooster (Player player, int boosterID) {
        DickInv i = new DickInv();
        if (Double.parseDouble(getBalance(player)) >= getAfkCost(player, boosterID)) {
            addBalance(player, -getCost(player, boosterID));
            setAfkLevel(player, boosterID);
            setAfkCost(player, boosterID);
            setAfkMultiplier(player, getBoosterMultiplier(player, 2, boosterID));
            i.openPiskaInventory(player);
        } else {
            player.closeInventory();
            player.sendMessage(ChatColor.DARK_RED + "У Вас не хватает средств на покупку данного бустера!");
        }
    }

    public double getBoosterMultiplier (Player player, int type, int id) {
        double multiplier;
        if (type == 1) multiplier = PiskoClicker.getInstance().getConfig().getDouble("Users." + player.getUniqueId() + ".boosters." + id + ".multiplier");
         else multiplier = PiskoClicker.getInstance().getConfig().getDouble("Users." + player.getUniqueId() + ".afkBoosters." + id + ".multiplier");
         return multiplier;
    }
    public static double getMultiplier (Player player) {
        return PiskoClicker.getInstance().getConfig().getDouble("Users." + player.getUniqueId() + ".multiplier");
    }
    public static void setMultiplier (Player player, double value) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".multiplier", getMultiplier(player) + value);
    }
    public static void setLevel (Player player, int booster) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".boosters." + booster + ".lvl", PiskoClicker.getInstance().getConfig().getInt("Users." + player.getUniqueId() + ".boosters." + booster + ".lvl") + 1);
    }
    public static void setCost (Player player, int booster) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".boosters." + booster + ".cost", PiskoClicker.getInstance().getConfig().getInt("Users." + player.getUniqueId() + ".boosters." + booster + ".cost")*1.2);
    }
    public static double getCost (Player player, int booster) {
       return PiskoClicker.getInstance().getConfig().getDouble("Users." + player.getUniqueId() + ".boosters." + booster + ".cost");
    }
    public static double getAfkMultiplier (Player player) {
        return PiskoClicker.getInstance().getConfig().getDouble("Users." + player.getUniqueId() + ".afkMultiplier");
    }
    public static void setAfkMultiplier (Player player, double value) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".afkMultiplier", getAfkMultiplier(player) + value);
    }
    public static void setAfkLevel (Player player, int booster) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".afkBoosters." + booster + ".lvl", PiskoClicker.getInstance().getConfig().getInt("Users." + player.getUniqueId() + ".afkBoosters." + booster + ".lvl") + 1);
    }
    public static void setAfkCost (Player player, int booster) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".afkBoosters." + booster + ".cost", PiskoClicker.getInstance().getConfig().getInt("Users." + player.getUniqueId() + ".afkBoosters." + booster + ".cost")*1.2);
    }
    public static double getAfkCost (Player player, int booster) {
        return PiskoClicker.getInstance().getConfig().getDouble("Users." + player.getUniqueId() + ".afkBoosters." + booster + ".cost");
    }
    public static String getBalance (Player player) {
        return PiskoClicker.getInstance().getConfig().getString("Users." + player.getUniqueId() + ".balance");
    }
    public static void addBalance (Player player, double value) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".balance", ((long)(Double.parseDouble(getBalance(player))*10) + value*10)/10);
        PiskoClicker.getInstance().saveConfig();
    }
    public boolean getHelloDelay(Player player) {
        if (PiskoClicker.getInstance().getConfig().get("Users." + player.getUniqueId() + ".helloDelay") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".helloDelay", true);
        }
        PiskoClicker.getInstance().saveConfig();
        return PiskoClicker.getInstance().getConfig().getBoolean("Users." + player.getUniqueId() + ".helloDelay");
    }
    public void setHelloDelay(Player player) {
        if (getHelloDelay(player)) {
            PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".helloDelay", !PiskoClicker.getInstance().getConfig().getBoolean("Users." + player.getUniqueId() + ".helloDelay"));
            PiskoClicker.getInstance().saveConfig();
            Bukkit.getScheduler().runTaskLater(PiskoClicker.getInstance(), () -> {
                PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".helloDelay", !PiskoClicker.getInstance().getConfig().getBoolean("Users." + player.getUniqueId() + ".helloDelay"));
                PiskoClicker.getInstance().saveConfig();
            }, 20*60);
        }
    }
    public void startBoosters () {
        Bukkit.getScheduler().runTaskTimerAsynchronously(PiskoClicker.getInstance(), () -> {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (getAfkMultiplier(player) > 0) {
                    addBalance(player, getAfkMultiplier(player));
                }
                addTime(player);
            }
        }, 0L, 20L);
    }






    @EventHandler
    public void settingBalanceAndHelloMessage (PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) {
            e.setJoinMessage("" + ChatColor.WHITE + ChatColor.MAGIC + "|" + ChatColor.RESET + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Встретим нового игрока " + ChatColor.ITALIC + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + " на нашем сервере!" + " " + ChatColor.WHITE + ChatColor.MAGIC + "|");
        } else e.setJoinMessage("");
        if (getHelloDelay(e.getPlayer())) {
            e.getPlayer().sendMessage(ChatColor.MAGIC + "|" + ChatColor.RESET + ChatColor.GREEN + " Привет, " + ChatColor.GOLD + e.getPlayer().getName() + ChatColor.GREEN + "! Добро пожаловать на сервер, веселись!" + " " + ChatColor.WHITE + ChatColor.MAGIC + "|");
            setHelloDelay(e.getPlayer());
        }
        if (PiskoClicker.getInstance().getConfig().get("Users." + e.getPlayer().getUniqueId() + ".balance") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + e.getPlayer().getUniqueId() + ".balance", 0);
            PiskoClicker.getInstance().saveConfig();
        }
        if (PiskoClicker.getInstance().getConfig().get("Users." + e.getPlayer().getUniqueId() + ".timePlayer") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + e.getPlayer().getUniqueId() + ".timePlayed", 0);
        }
        if (PiskoClicker.getInstance().getConfig().get("Users." + e.getPlayer().getUniqueId() + ".helloDelay") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + e.getPlayer().getUniqueId() + ".helloDelay", true);
            PiskoClicker.getInstance().saveConfig();
        }

    }

    @EventHandler
    public void playerLeave (PlayerQuitEvent e) {
        e.setQuitMessage(ChatColor.YELLOW + "Лох " + ChatColor.LIGHT_PURPLE + e.getPlayer().getName() + ChatColor.YELLOW + " ливнул.");
    }

    @EventHandler
    public void piskaRemover (PlayerDeathEvent e) {
        ItemStack piska = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = piska.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Писька " + e.getEntity().getName());
        meta.setLore(Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."));
        piska.setItemMeta(meta);
        if (e.getDrops().contains(piska))
            e.getDrops().removeIf(item -> item == piska);
    }

    @EventHandler
    public void piskaAdder (PlayerJoinEvent e) {
        ItemStack piska = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = piska.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Писька " + e.getPlayer().getName());
        meta.setLore(Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."));
        piska.setItemMeta(meta);
        if (!e.getPlayer().getInventory().contains(piska)) {
            e.getPlayer().getInventory().addItem(piska);
        }
    }

    @EventHandler
    public void piskaAdderAfterDeath (PlayerRespawnEvent e) {
        ItemStack piska = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = piska.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Писька " + e.getPlayer().getName());
        meta.setLore(Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."));
        piska.setItemMeta(meta);
        if (!e.getPlayer().getInventory().contains(piska)) {
            e.getPlayer().getInventory().addItem(piska);
        }
    }

    @EventHandler
    public void piskaUnDropable (PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().hasItemMeta() && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName() && Objects.equals(e.getItemDrop().getItemStack().getItemMeta().getLore(), Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."))) {
            e.setCancelled(true);
        } else e.setCancelled(false);
    }

    @EventHandler
    public void piskaUnCraft (PrepareItemCraftEvent e) {
        if (e.getInventory().contains(Material.BLAZE_ROD))
            for (ItemStack itemStack : e.getInventory().getMatrix())
                if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasLore())
                    if (itemStack.getItemMeta().hasLore() && Objects.equals(itemStack.getItemMeta().getLore(),
                            Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."))) {
                    itemStack.setType(Material.AIR);
                    return;
                    }
    }

    @EventHandler
    public void chatMessages (AsyncPlayerChatEvent e) {
        e.setMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE
                + (int) Double.parseDouble(getBalance(e.getPlayer())) + ChatColor.GOLD + "] " + ChatColor.WHITE + e.getPlayer().getName() + ": " + e.getMessage());
        e.setFormat(e.getMessage());
    }

    @EventHandler
    public void farmingDicks (InventoryClickEvent e) {
        if (Objects.requireNonNull(Objects.requireNonNull(e.getInventory().getItem(1)).getI18NDisplayName()).equals("" + ChatColor.MAGIC + ChatColor.BOLD + " HELP ME ")) {
            Player player = (Player) e.getWhoClicked();
            if (e.getCurrentItem() != null && !e.isShiftClick() && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() ) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "CLICK ME TO EARN DICKS") || e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "CLICK ME TO EARN DICKS")) {
                    addBalance(player, 1 + getMultiplier(player));
                }
                String itemName = e.getCurrentItem().getItemMeta().getDisplayName();

                //////////////////////// Click Boosters
                if (itemName.equals(ChatColor.WHITE + "Накачай левую руку!")) buyBooster(player, 1);
                if (itemName.equals(ChatColor.WHITE + "Накачай правую руку!")) buyBooster(player, 2);
                if (itemName.equals(ChatColor.WHITE + "Используй пачку от принглс правильно.")) buyBooster(player, 3);
                ////////////////////////

                //////////////////////// Afk Boosters
                if (itemName.equals(ChatColor.WHITE + "Пусть тян делает все за тебя.")) buyAfkBooster(player, 1);
                if (itemName.equals(ChatColor.WHITE + "Нет лучше лупы, чем друга залупы.")) buyAfkBooster(player, 2);
                ////////////////////////
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void openInventory (PlayerInteractEvent e) {
        DickInv i = new DickInv();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand().hasItemMeta() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Писька " + e.getPlayer().getName())) {
                i.openPiskaInventory(e.getPlayer());
                e.setCancelled(true);
            } else e.setCancelled(false);
        }
    }

    @EventHandler
    public void addingBoosters (PlayerJoinEvent e) {
        Player p = e.getPlayer();
        setupBoosters(p);
        setupAfkBoosters(p);
    }
}

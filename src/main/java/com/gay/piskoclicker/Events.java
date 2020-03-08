package com.gay.piskoclicker;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.UUID;

public class Events implements Listener {

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
    public static String getBalance (Player player) {
        return PiskoClicker.getInstance().getConfig().getString("Users." + player.getUniqueId() + ".balance");
    }
    public static double getBalanceDoubleUUID (UUID id) {
        return PiskoClicker.getInstance().getConfig().getDouble("Users." + id + ".balance");
    }
    public static void addBalance (Player player, double value) {
        PiskoClicker.getInstance().getConfig().set("Users." + player.getUniqueId() + ".balance", (Double.parseDouble(getBalance(player)) + value));
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
        if (PiskoClicker.getInstance().getConfig().get("Users." + e.getPlayer().getUniqueId() + ".helloDelay") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + e.getPlayer().getUniqueId() + ".helloDelay", true);
            PiskoClicker.getInstance().saveConfig();
        }

    }

    @EventHandler
    public void playerLeave (PlayerQuitEvent e) {
        e.setQuitMessage("");
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
    public void piskaBedrocker (InventoryClickEvent e) {
        if (e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() && e.getCurrentItem().getItemMeta().hasLore() && e.getCurrentItem().getItemMeta().getLore().equals(Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."))) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void piskaUnDropable (PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().hasItemMeta() && e.getItemDrop().getItemStack().getItemMeta().hasDisplayName() && e.getItemDrop().getItemStack().getItemMeta().getLore().equals(Collections.singletonList(ChatColor.WHITE + "ПКМ, чтобы открыть меню."))) {
            e.setCancelled(true);
        } else e.setCancelled(false);
    }
    @EventHandler
    public void piskaAfterDeath (EntityDeathEvent e) {
        if (e.getEntity() != null && e.getEntity().getKiller() != null && e.getEntity() instanceof Player) {
            e.getDrops().clear();
            ItemStack piska = new ItemStack(Material.BLAZE_ROD);
            ItemMeta meta = piska.getItemMeta();
            meta.setDisplayName(ChatColor.GOLD + "Писька " + e.getEntity().getName());
            meta.setLore(Collections.singletonList(ChatColor.WHITE + "Писька поверженного письконосителя, который был убит игроком " + e.getEntity().getKiller().getName()));
            piska.setItemMeta(meta);
            e.getDrops().add(piska);
        }
    }
    @EventHandler
    public void chatMessages (AsyncPlayerChatEvent e) {
        e.setMessage(ChatColor.GOLD + "[" + ChatColor.LIGHT_PURPLE + (int) Double.parseDouble(getBalance(e.getPlayer())) + ChatColor.GOLD + "] " + ChatColor.WHITE + e.getPlayer().getName() + ": " + e.getMessage());
        e.setFormat(e.getMessage());
    }


    @EventHandler
    public void farmingDicks (InventoryClickEvent e) {
        if (e.getInventory() != null && e.getInventory().getName() != null && e.getInventory().getName().equals(ChatColor.GOLD + "DICK CLICKER by " + ChatColor.BOLD + ChatColor.AQUA + "oofxFrozen")) {
            Player player = (Player) e.getWhoClicked();
            DickInv i = new DickInv();
            if (e.getCurrentItem() != null && !e.isShiftClick() && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().hasDisplayName() ) {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.LIGHT_PURPLE + "CLICK ME TO EARN DICKS") || e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.RED + "CLICK ME TO EARN DICKS")) {
                    addBalance(player, 1 + getMultiplier(player));
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Накачай левую руку!")) {
                    if (Double.parseDouble(getBalance(player)) >= getCost(player, 1)) {
                        addBalance(player, -getCost(player, 1));
                        setLevel(player, 1);
                        setCost(player, 1);
                        setMultiplier(player, 0.1);
                        i.openPiskaInventory(player);
                    } else {
                        player.closeInventory();
                        player.sendMessage(ChatColor.DARK_RED + "У Вас не хватает средств на покупку данного бустера!");
                    }
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.WHITE + "Накачай правую руку!")) {
                    if (Double.parseDouble(getBalance(player)) >= getCost(player, 2)) {
                        addBalance(player, -getCost(player, 2));
                        setLevel(player, 2);
                        setCost(player, 2);
                        setMultiplier(player, 2);
                        i.openPiskaInventory(player);
                    } else {
                        player.closeInventory();
                        player.sendMessage(ChatColor.DARK_RED + "У Вас не хватает средств на покупку данного бустера!");
                    }
                }
            }
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void openInventory (PlayerInteractEvent e) {
        DickInv i = new DickInv();
        if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (e.getPlayer().getInventory().getItemInMainHand() != null && e.getPlayer().getInventory().getItemInMainHand().hasItemMeta() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().hasDisplayName() && e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Писька " + e.getPlayer().getName())) {
                i.openPiskaInventory(e.getPlayer());
                e.setCancelled(true);
            } else e.setCancelled(false);
        }
    }







    @EventHandler
    public void addingBoosters (PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.1.multiplier") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.1.lvl", 0);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.1.cost", 100);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.1.multiplier", 0.1);
        }
        if (PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.2.multiplier") == null) {
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.2.lvl", 0);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.2.cost", 1000);
            PiskoClicker.getInstance().getConfig().set("Users." + p.getUniqueId() + ".boosters.2.multiplier", 1);
        }
    }
}

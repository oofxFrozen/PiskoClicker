package com.gay.piskoclicker;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class DickInv implements Listener {

    public void openPiskaInventory (Player p) {
        ItemStack empty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        ItemMeta meta = empty.getItemMeta();
        meta.setDisplayName("" + ChatColor.MAGIC + ChatColor.BOLD + "  HELP ME  ");
        empty.setItemMeta(meta);

        ItemStack leftHand = new ItemStack(Material.TORCH);
        meta = leftHand.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Накачай левую руку!");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(ChatColor.WHITE + "Level: " + ChatColor.GOLD + PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.1.lvl"));
        arrayList.add(ChatColor.WHITE + "Cost: " + ChatColor.GOLD + PiskoClicker.getInstance().getConfig().getInt("Users." + p.getUniqueId() + ".boosters.1.cost"));
        arrayList.add(ChatColor.WHITE + "Adds: " + ChatColor.GOLD + PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.1.multiplier"));
        meta.setLore(arrayList);
        leftHand.setItemMeta(meta);
        arrayList.clear();

        ItemStack rightHand = new ItemStack(Material.REDSTONE_TORCH_ON);
        meta = rightHand.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + "Накачай правую руку!");
        arrayList.add(ChatColor.WHITE + "Level: " + ChatColor.GOLD + PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.2.lvl"));
        arrayList.add(ChatColor.WHITE + "Cost: " + ChatColor.GOLD + PiskoClicker.getInstance().getConfig().getInt("Users." + p.getUniqueId() + ".boosters.2.cost"));
        arrayList.add(ChatColor.WHITE + "Adds: " + ChatColor.GOLD + PiskoClicker.getInstance().getConfig().get("Users." + p.getUniqueId() + ".boosters.2.multiplier"));
        meta.setLore(arrayList);
        rightHand.setItemMeta(meta);
        arrayList.clear();

        ItemStack pinkDick = new ItemStack(Material.WOOL, 1, (short) 6);
        ItemStack redDick = new ItemStack(Material.WOOL, 1, (short) 14);
        meta = pinkDick.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "CLICK ME TO EARN DICKS");
        pinkDick.setItemMeta(meta);
        meta = redDick.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "CLICK ME TO EARN DICKS");
        redDick.setItemMeta(meta);
        Inventory inventory = PiskoClicker.getInstance().getServer().createInventory(null, 27, ChatColor.GOLD + "DICK CLICKER by " + ChatColor.BOLD + ChatColor.AQUA + "oofxFrozen");
        for (int i = 0; i < 27; i++) {
            inventory.setItem(i, empty);
        }
        inventory.setItem(4, redDick);
        inventory.setItem(13, pinkDick);
        inventory.setItem(22, pinkDick);
        inventory.setItem(21, pinkDick);
        inventory.setItem(23, pinkDick);
        inventory.setItem(18, leftHand);
        inventory.setItem(19, rightHand);

        p.openInventory(inventory);
    }
}

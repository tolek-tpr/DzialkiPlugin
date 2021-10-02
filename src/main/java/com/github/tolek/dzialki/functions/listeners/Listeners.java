package com.github.tolek.dzialki.functions.listeners;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import com.github.tolek.dzialki.plot.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

import java.util.Set;
import java.util.stream.Collectors;

public class Listeners implements Listener {

    private Inventory hInv;
    private Inventory bInv;
    private Inventory eaInv;
    private Inventory sInv;

    private PlotManager plots;

    public Listeners(PlotManager plots) {
        this.plots = plots;
    }

    public void plyaerIntecactEvent(PlayerInteractEvent event) {
        Player player = (Player) event.getPlayer();
        Plot plot = plots.getPlotByLocation(event.getClickedBlock().getX(), event.getClickedBlock().getZ());
        if (plot == null) return;

        // check if owner/added user is on the plot
        Set<Player> staff = Bukkit.getServer().getOnlinePlayers().stream()
                .filter(p -> plot.owner.equals(p.getName()) || plot.allowedUsers.contains(p.getName()))
                .filter(p -> plot.overlaps((int) p.getLocation().getX(), (int) p.getLocation().getZ()))
                .collect(Collectors.toSet());
        if(plot.type == Type.NONE) {
            player.sendMessage("haha");
            event.setCancelled(true);
        }else if(plot.type == Type.HOSPITAL) {

        }
        player.sendMessage(staff.size() == 0 ? "No one here" : "The staff is on duty");

        if (staff.size() == 0) return;
    }

    public void createHInv() {
        hInv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Hospital");
    }

}

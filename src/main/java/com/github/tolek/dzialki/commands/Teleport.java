package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Teleport implements CommandExecutor {

    private PlotManager plots;

    public Teleport(PlotManager plots) {
        this.plots = plots;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player) || args.length < 1) return false;

        Plot plot = plots.getPlotbyName(args[0]);
        if (plot == null) return false;

        Player player = ((Player) sender);
        Location location = new Location(player.getWorld(), plot.x + plot.sizeX / 2, player.getLocation().getY(), plot.z + plot.sizeZ / 2);
        player.teleport(location);

        return true;
    }
}

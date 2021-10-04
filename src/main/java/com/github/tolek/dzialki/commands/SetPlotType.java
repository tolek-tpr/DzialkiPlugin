package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import com.github.tolek.dzialki.plot.Type;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;

public class SetPlotType implements CommandExecutor {

    private final PlotManager plots;

    public SetPlotType(PlotManager plots) {
        this.plots = plots;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        Location l = p.getLocation();
        Plot plot = plots.getPlotByLocation(l.getBlockX(), l.getBlockZ());

        if (args.length == 0) {
            p.sendMessage("Parameter missing, usage: /setplottype [type]");
            return false;
        }

        if (!plot.isOwnedBy(p)) {
            p.sendMessage("You can't do that, only the owner can change the plot type");
            return false;
        }

        if (!plot.overlaps(l.getBlockX(), l.getBlockZ())) {
            p.sendMessage("Please stand on your plot");
            return false;
        }

        if(args[0].equalsIgnoreCase("NONE") || args[0].equalsIgnoreCase("HOSPITAL")
                || args[0].equalsIgnoreCase("STORE") || args[0].equalsIgnoreCase("BLACKSMITH")
                || args[0].equalsIgnoreCase("ESTATE_AGENCY")) {
            plot.setPlotType(args[0].toUpperCase());
            p.sendMessage("Set the plot type to: " + args[0]);
            return false;
        }else {
            p.sendMessage("Available plot types: none, store, hospital, blacksmith, estate_agency");

        }
        return false;
    }

}

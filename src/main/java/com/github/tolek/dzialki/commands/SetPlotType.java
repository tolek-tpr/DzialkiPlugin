package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import com.github.tolek.dzialki.plot.Type;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
        }

        if (!plot.isOwnedBy(p)) {
            p.sendMessage("You can't do that, only the owner can change the plot type");
        }

        if (!plot.overlaps(l.getBlockX(), l.getBlockZ())) {
            p.sendMessage("Please stand on your plot");
        }

        plot.setPlotType(Type.valueOf(args[1]));

        return false;
    }

}

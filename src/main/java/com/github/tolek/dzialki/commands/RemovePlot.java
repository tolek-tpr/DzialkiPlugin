package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePlot implements CommandExecutor {

    private PlotManager plots;

    public RemovePlot(PlotManager plots) {
        this.plots = plots;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Plot plot = plots.getPlotbyName(args[0]);
        if (!plot.isOwnedBy(player) && !player.isOp()) {
            sender.sendMessage("You don't own this plot");
            return false;
        }

        return plots.remove(plot);
    }
}

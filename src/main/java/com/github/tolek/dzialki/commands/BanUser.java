package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanUser implements CommandExecutor {

    private PlotManager plots;

    public BanUser(PlotManager plots) {
        this.plots = plots;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        Plot plot = plots.getPlotbyName(args[1]);
        if (!plot.isOwnedBy(player) && !player.isOp()) {
            sender.sendMessage("You don't own this plot");
            return false;
        }
        if (!plot.isAccessibleBy(args[0])) {
            sender.sendMessage("User not invited to this plot");
            return false;
        }
        plot.removeUser(args[0]);
        player.sendMessage("Player " + args[0] + " banned from the plot " + args[1]);
        return true;
    }
}

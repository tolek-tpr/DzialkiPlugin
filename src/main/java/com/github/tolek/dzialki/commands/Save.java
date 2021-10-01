package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.PlotManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Save implements CommandExecutor {

    private PlotManager plots;

    public Save(PlotManager plots) {
        this.plots = plots;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp())
            return false;

        plots.save();

        if (sender instanceof Player) sender.sendMessage("Plots saved to file");
        else System.out.println("Plots saved to file");

        return true;
    }
}

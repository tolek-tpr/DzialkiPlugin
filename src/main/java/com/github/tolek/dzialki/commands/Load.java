package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.PlotManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Load implements CommandExecutor {

    private PlotManager plots;

    public Load(PlotManager plots) {
        this.plots = plots;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp())
            return false;

        plots.removeAll();
        plots.load();

        if (sender instanceof Player) sender.sendMessage("Plots reloaded from file");
        else System.out.println("Plots reloaded from file");

        return true;
    }
}

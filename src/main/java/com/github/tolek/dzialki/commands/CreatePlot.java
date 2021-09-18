package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CreatePlot extends Command {

    private PlotManager plots;

    public CreatePlot(PlotManager plots) {
        super("dz_create", "Claim a plot", true, false,
                List.of(Pair.of("name", "name of the plot"), Pair.of("sizeX", "size on X axis"), Pair.of("sizeZ", "size on Z axis")), null);
        this.plots = plots;
    }

    @Override
    protected boolean handleCommand(CommandSender sender, org.bukkit.command.Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String playerName = player.getName().toString();
        int x = player.getLocation().getBlockX();
        int z = player.getLocation().getBlockZ();

        // check for max allowed plots count
        if (plots.getUserPlotCount(playerName) >= Plot.MAX_PLOTS)
            return warning("You have no more plots left (max " + Plot.MAX_PLOTS + ")");

        // check if plot name unique
        if (plots.getPlotbyName(args[0]) != null)
            return warning("A plot with that name already exists!");

        // create the plot
        String name = args[0];
        int sizeX = Integer.parseInt(args[1]);
        int sizeZ = Integer.parseInt(args[2]);
        if (sizeX > Plot.MAX_SIZE || sizeZ > Plot.MAX_SIZE)
            return warning("Plot too large, max size in either dimension is " + Plot.MAX_SIZE);

        // add the plot to the list
        Plot plot = new Plot(name, x, z, sizeX, sizeZ, playerName);
        if (plots.add(plot) == false)
            return error("Plot overlaps with another plot");

        info(ChatColor.GOLD + "Plot created! You have " + (Plot.MAX_PLOTS - plots.getUserPlotCount(playerName)) + " more left");
        return true;
    }

}

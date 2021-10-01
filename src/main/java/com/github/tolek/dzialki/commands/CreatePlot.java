package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import com.github.tolek.dzialki.plot.Type;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreatePlot implements CommandExecutor {

	private PlotManager plots;

	public CreatePlot(PlotManager plots) {
		this.plots = plots;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			Bukkit.getServer().getConsoleSender().sendMessage("You can't do that!");
			return false;
		}
		Player player = (Player) sender;
		String playerName = player.getName().toString();
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();

		if (args.length != 3) {
			player.sendMessage("Parameter missing, usage: /dz_create [name] [sizeX] [sizeZ]");
			return false;
		}

		// check for max allowed plots count
		if (plots.getUserPlotCount(playerName) >= Plot.MAX_PLOTS) {
			player.sendMessage(
					ChatColor.RED + "" + ChatColor.BOLD + "You have 0 plots left (max " + Plot.MAX_PLOTS + ")");
			return false;
		}

		// check if plot name unique
		if (plots.getPlotbyName(args[0]) != null) {
			player.sendMessage("A plot with that name already exists!");
			return false;
		}

		// create the plot
		String name = args[0];
		int sizeX = Integer.parseInt(args[1]);
		int sizeZ = Integer.parseInt(args[2]);
		if (sizeX > Plot.MAX_SIZE || sizeZ > Plot.MAX_SIZE) {
			player.sendMessage("Plot too large, max size in either dimension is " + Plot.MAX_SIZE);
			return false;
		}
		Plot plot = new Plot(name, x, z, sizeX, sizeZ, playerName, Type.NONE);

		// add the plot to the list
		if (plots.add(plot) == false) {
			player.sendMessage("Plot overlaps with another plot");
			return false;
		}
		player.sendMessage(ChatColor.GOLD + "Plot created! You have "
				+ (Plot.MAX_PLOTS - plots.getUserPlotCount(playerName)) + " more left");
		return true;
	}

}

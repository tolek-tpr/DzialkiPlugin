package com.github.tolek.dzialki.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;

public class SetMaxPlots implements CommandExecutor {

	private PlotManager plots;

	public SetMaxPlots(PlotManager plots) {
		this.plots = plots;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("dz_max_plots")) {
			Plot.MAX_PLOTS = Integer.parseInt(args[0]);
			player.sendMessage("Changed the max size of plots to: " + Plot.MAX_PLOTS);
		}

		return false;
	}

}

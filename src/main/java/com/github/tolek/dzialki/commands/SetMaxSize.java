package com.github.tolek.dzialki.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;

public class SetMaxSize implements CommandExecutor {

	private PlotManager plots;

	public SetMaxSize(PlotManager plots) {
		this.plots = plots;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		if (label.equalsIgnoreCase("dz_max_size")) {
			Plot.MAX_SIZE = Integer.parseInt(args[0]);
			player.sendMessage("Changed the max size of plots to: " + Plot.MAX_SIZE);
		}

		return false;
	}

}

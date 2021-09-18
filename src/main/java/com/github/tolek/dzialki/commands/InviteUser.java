package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InviteUser implements CommandExecutor {

	private PlotManager plots;

	public InviteUser(PlotManager plots) {
		this.plots = plots;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
			return false;
		Player player = (Player) sender;
		Plot plot = plots.getPlotbyName(args[1]);
		if (!plot.isOwnedBy(player) && !player.isOp()) {
			sender.sendMessage("You don't own this plot");
			return false;
		}
		plot.addUser(args[0]);
		player.sendMessage("Added player " + args[0] + " to the plot: " + args[1]);
		return true;
	}
}

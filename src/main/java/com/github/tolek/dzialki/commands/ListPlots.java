package com.github.tolek.dzialki.commands;

import com.github.tolek.dzialki.plot.PlotManager;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListPlots implements CommandExecutor {

	private PlotManager plots;

	public ListPlots(PlotManager plots) {
		this.plots = plots;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.isOp())
			return false;

		if (sender instanceof Player) {
			sender.sendMessage(plots.toString());
		} else {
			System.out.println(plots.toString());
		}
		return true;
	}
}

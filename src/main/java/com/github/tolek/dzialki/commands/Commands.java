package com.github.tolek.dzialki.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	private PlotManager plots;

	public Commands(PlotManager plots) {
		this.plots = plots;
	}

	/*
	 * /dz create /dz delete /dz add <nick> /dz remove <nick>
	 *
	 * I max ilosc slotow na dzialke 2
	 */

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (!(sender instanceof Player)) {
			Bukkit.getServer().getConsoleSender().sendMessage("You can't do that!");
			return false;
		}

		String playerId = player.getUniqueId().toString();
		int x = player.getLocation().getBlockX();
		int z = player.getLocation().getBlockZ();

		switch (label) {
		case ("dz_create"):
//			if (args[0] == null || !args[0].isEmpty()) {
//				player.sendMessage("Please provide the name for the plot! Usage: /dz create [name]");
//				return false;
//			}

			// check for max allowed plots count
			if (plots.getUserPlotCount(playerId) >= Plot.MAX_PLOTS) {
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
			Plot plot = new Plot(name, x, z,
					sizeX, sizeZ, playerId);

			// add the plot to the list
			if (plots.add(plot) == false) {
				player.sendMessage("Plot overlaps with another plot");
				return false;
			}
			player.sendMessage(ChatColor.GOLD + "Plot created! You have "
					+ (Plot.MAX_PLOTS - plots.getUserPlotCount(playerId)) + " more left");

			break;
		case ("dz_delete"):
			plot = plots.getPlotbyName(args[0]);
			plots.remove(plot);
			break;
		case ("dz_add"):

			plot = plots.getPlotbyName(args[1]);
			if (plot.isOwnedBy(player)) {
				plot.addUser(args[0]);
				player.sendMessage("Added player " + args[0] + " to the plot: " + args[1]);
			}
			break;
		case ("dz_remove"):
			plot = plots.getPlotbyName(args[1]);
		if (plot.isOwnedBy(player)) {
			plot.removeUser(args[0]);
			player.sendMessage("Removed player " + args[0] + " from plot: " + args[1]);
		}

			break;
		}
		if (player.isOp()) {
			if (label.equalsIgnoreCase("dz_max_size")) {
				Plot.MAX_SIZE = Integer.parseInt(args[0]);
				player.sendMessage(ChatColor.GOLD + "Max size changed to " + args[0]);
			}
			if (label.equalsIgnoreCase("dz_max_plots")) {
				Plot.MAX_PLOTS = Integer.parseInt(args[0]);
				player.sendMessage(ChatColor.GOLD + "Max number of plots changed to " + args[0]);
			}
			if (label.equalsIgnoreCase("getlist")) {
				player.sendMessage(plots.toString());
				System.out.println(plots.toString());
			}
		} else {
			player.sendMessage(ChatColor.DARK_RED + "You can't do that!");
		}
		return false;
	}

}

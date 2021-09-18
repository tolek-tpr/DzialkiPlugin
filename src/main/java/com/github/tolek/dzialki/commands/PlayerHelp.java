package com.github.tolek.dzialki.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerHelp implements CommandExecutor {
	
	final String[][] helpPages = {
			{ "&1-------------Plot Help-------------" },
			{ "- &bdz_create <name> <size> <size>: makes a plot. " },
			{ "- &bdz_remove <name>: deletes a plot." },
			{ "- &bdz_invite <player> <plot>: adds a player to the plot." },
			{ "- &bdz_ban <player> <plot>: removes a player from the plot." }
	};
	
	private List<String> format() {
		return Arrays.stream(helpPages).map(
				helpParagraphs -> ChatColor.translateAlternateColorCodes('&', String.join("\n", helpParagraphs)))
				.collect(Collectors.toList());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage(String.join("\n", format()));
		format();
		return false;
	}
	
}

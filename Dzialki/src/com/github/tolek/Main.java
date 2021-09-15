package com.github.tolek;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.tolek.commands.Commands;
import com.github.tolek.listeners.Listeners;
import com.github.tolek.plot.PlotManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	private PlotManager plots = new PlotManager();

	private Commands commands;
	private Listeners listeners;

	
	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Działki plugin v1 enabling");
		PluginManager pm = this.getServer().getPluginManager();
				
		plots.load();
		System.out.println(plots.toString());

		commands = new Commands(plots);
		this.getCommand("dz_create").setExecutor(commands);
		this.getCommand("getlist").setExecutor(commands);
		this.getCommand("dz_max_size").setExecutor(commands);
		this.getCommand("dz_max_plots").setExecutor(commands);
		this.getCommand("dz_delete").setExecutor(commands);
		this.getCommand("dz_add").setExecutor(commands);
		this.getCommand("dz_remove").setExecutor(commands);

		listeners = new Listeners(plots);
		pm.registerEvents(listeners, this);

	}
	
	public void onDisable() {
		plots.save();
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Działki plugin v1 disabling");
	}
	
}

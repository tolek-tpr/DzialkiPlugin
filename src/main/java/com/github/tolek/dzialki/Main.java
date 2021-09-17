package com.github.tolek.dzialki;

import com.github.tolek.dzialki.commands.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.tolek.dzialki.listeners.Listeners;
import com.github.tolek.dzialki.plot.PlotManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	private PlotManager plots;

	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Dzialki plugin v1 enabling");
		PluginManager pm = this.getServer().getPluginManager();

		plots.load();
		System.out.println(plots.toString());

		this.getCommand("dzlist").setExecutor(new ListPlots(plots));
		this.getCommand("dzcreate").setExecutor(new CreatePlot(plots));
		this.getCommand("dzremove").setExecutor(new RemovePlot(plots));
		this.getCommand("dzinvite").setExecutor(new InviteUser(plots));
		this.getCommand("dzban").setExecutor(new BanUser(plots));
//		this.getCommand("dzsetmaxsize").setExecutor(new SetMaxSize(plots));
//		this.getCommand("dzsetmaxplots").setExecutor(new SetMaxPlots(plots));

		Listeners listeners = new Listeners(plots);
		pm.registerEvents(listeners, this);

	}

	public void onDisable() {
		plots.save();
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Dzialki plugin v1 disabling");
	}

}

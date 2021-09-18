package com.github.tolek.dzialki;

import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.tolek.dzialki.commands.BanUser;
import com.github.tolek.dzialki.commands.CreatePlot;
import com.github.tolek.dzialki.commands.GetSize;
import com.github.tolek.dzialki.commands.InviteUser;
import com.github.tolek.dzialki.commands.ListPlots;
import com.github.tolek.dzialki.commands.RemovePlot;
import com.github.tolek.dzialki.commands.SetMaxPlots;
import com.github.tolek.dzialki.commands.SetMaxSize;
import com.github.tolek.dzialki.listeners.Listeners;
import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;
import com.github.tolek.dzialki.settings.SettingManager;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	private PlotManager plots = new PlotManager();
	private SettingManager settings = new SettingManager();

	public void onEnable() {
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Dzialki plugin v1 enabling");
		PluginManager pm = this.getServer().getPluginManager();

		plots.load();
		settings.load();
		System.out.println(plots.toString());
		System.out.println(Plot.MAX_SIZE + " " + Plot.MAX_PLOTS);

		this.getCommand("dz_list").setExecutor(new ListPlots(plots));
		this.getCommand("dz_create").setExecutor(new CreatePlot(plots));
		this.getCommand("dz_remove").setExecutor(new RemovePlot(plots));
		this.getCommand("dz_invite").setExecutor(new InviteUser(plots)); 	 	
		this.getCommand("dz_ban").setExecutor(new BanUser(plots));
		this.getCommand("dz_max_size").setExecutor(new SetMaxSize(plots));
		this.getCommand("dz_max_plots").setExecutor(new SetMaxPlots(plots));
		this.getCommand("getsize").setExecutor(new GetSize());

		Listeners listeners = new Listeners(plots);
		pm.registerEvents(listeners, this);

	}

	public void onDisable() {
		plots.save();
		settings.save();
		System.out.println(Plot.MAX_SIZE + " " + Plot.MAX_PLOTS);
		getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "Dzialki plugin v1 disabling");
	}

}

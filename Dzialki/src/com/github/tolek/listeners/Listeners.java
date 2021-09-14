package com.github.tolek.listeners;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.tolek.plot.Plot;
import com.github.tolek.plot.PlotManager;

public class Listeners implements Listener {
	
	private PlotManager plots;
	
	public Listeners(PlotManager plots) {
		this.plots = plots;
	}
	
	final static String[][] bookPages = { 
			{"All functions: \n /dz_create <name> <size> <size>\n /dz_delete <name>\n /dz_add <player> <plot>\n /dz_remove <player> <plot>\n\n\nPlugin made by: Tolek_pro"}, 
			{"/dz_add <plot> <user>\n /dz_remove <plot> <user>"},
			{"YT channels: @INIVIX\n BY @Tolek Pro"}
	 };
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (player.getInventory().isEmpty()) {
			ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bookMeta = (BookMeta) book.getItemMeta();
			bookMeta.setTitle("What is in the plugin?");
			bookMeta.setAuthor("tolek_pro");
			bookMeta.setPages(Arrays.stream(bookPages).map(
					pageParagraphs -> ChatColor.translateAlternateColorCodes('&', String.join("\n", pageParagraphs)))
					.collect(Collectors.toList()));
			book.setItemMeta(bookMeta);
			player.getInventory().addItem(book);		
			player.sendMessage(ChatColor.GREEN + "Please read the book you have been given");
			player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);

		}
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = (Player) event.getPlayer();
		int x = event.getBlock().getLocation().getBlockX();
		int z = event.getBlock().getLocation().getBlockZ();
		Plot plot = plots.getPlotByLocation(x, z);
		if (plot != null && (!plot.isOwnedBy(p) || !plot.isAccessibleBy(p))) {
			event.setCancelled(true);
		}
	}

}

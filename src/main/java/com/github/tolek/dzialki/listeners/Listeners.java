package com.github.tolek.dzialki.listeners;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;


import com.github.tolek.dzialki.plot.Type;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.github.tolek.dzialki.plot.Plot;
import com.github.tolek.dzialki.plot.PlotManager;

public class Listeners implements Listener {

	private PlotManager plots;
	private Inventory hinv;

	private HashMap<String, Plot> previousPlots = new HashMap<String, Plot>();

	public Listeners(PlotManager plots) {
		this.plots = plots;
	}

	final static String[][] bookPages = { {
			"for a list of all commands do /dz_help" },
			{ "Made by @Tolek Pro on youtube" } };

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
		if (plot != null && !(p.isOp() || plot.isOwnedBy(p) || plot.isAccessibleBy(p))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPalce(BlockPlaceEvent event) {
		Player p = (Player) event.getPlayer();
		int x = event.getBlock().getLocation().getBlockX();
		int z = event.getBlock().getLocation().getBlockZ();
		Plot plot = plots.getPlotByLocation(x, z);
		if (plot != null && !(p.isOp() || plot.isOwnedBy(p) || plot.isAccessibleBy(p))) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		// when entering the plot say "entering + plot name"
		// and when leaving the plot say "leaving + plot name"
		Player p = (Player) event.getPlayer();
		int x = p.getLocation().getBlockX();
		int z = p.getLocation().getBlockZ();
		Plot plot = plots.getPlotByLocation(x, z);
		if (previousPlots.get(p.getName()) == plot) {
			return;
		}
		if (plot != null) {
			p.sendMessage(ChatColor.GREEN + "Entering " + plot.name);
		} else {
			p.sendMessage(ChatColor.RED + "Leaving " + previousPlots.get(p.getName()).name);
		}
		previousPlots.put(p.getName(), plot);
	}
	
	@EventHandler
	public void playerInteractEvent(PlayerInteractEvent event) {
		Player player = (Player) event.getPlayer();
		int x = event.getClickedBlock().getX();
		int z = event.getClickedBlock().getZ();
		Plot plot = plots.getPlotByLocation(x, z);

		if (plot != null && !(player.isOp() || plot.isOwnedBy(player) || plot.isAccessibleBy(player)) && plot.type == Type.NONE) {
			event.setCancelled(true);
		}
		if (plot == null) return;

		// check if owner/added user is on the plot
		Set<Player> staff = Bukkit.getServer().getOnlinePlayers().stream()
				.filter(p -> plot.owner.equals(p.getName()) || plot.allowedUsers.contains(p.getName()))
				.filter(p -> plot.overlaps((int) p.getLocation().getX(), (int) p.getLocation().getZ()))
				.collect(Collectors.toSet());

		if(plot.type == Type.HOSPITAL && event.getClickedBlock().getType().equals(Material.CHEST)) {
			event.setCancelled(true);

			player.openInventory(hinv);
			player.sendMessage("Working");
		}
		if(staff.size() == 0) {
			player.sendMessage("No staff here");
		}


		if (staff.size() == 0) return;
		player.sendMessage("test");
	}

	public void makeNewHospitalInv() {
		int slot = 54;
		hinv = Bukkit.createInventory(null, slot, ChatColor.GOLD + "Hospital");

		ItemStack filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
		//hinv.setItem(0, filler);
		for(int i = 0; i < slot - 1; i++) {
			if(i == 53) {
				ItemStack barrier = new ItemStack(Material.BARRIER);
				hinv.setItem(53, barrier);
				i++;
			}
			hinv.setItem(i, filler);
		}
	}

}

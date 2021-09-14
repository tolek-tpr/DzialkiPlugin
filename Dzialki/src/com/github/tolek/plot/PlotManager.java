package com.github.tolek.plot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class PlotManager {

	final static String FILE_NAME = "dzialki.dzplugin";

	private List<Plot> plots = new ArrayList<Plot>();

	public boolean add(Plot p) {
		return plotExists(p) == null ? plots.add(p) : false;
	}
	
	public boolean remove(Plot p) {
		return plots.remove(p);
	}

	public Plot plotExists(Plot q) {
		for (Plot p : plots) {
			if (q.overlaps(p))
				return p;
		}
		return null;
	}

	public int getUserPlotCount(String user) {
		int count = 0;
		for (Plot p : plots) {
			if (user.equals(p.admin)) {
				count++;
			}
		}
		return count;
	}

	public Plot getPlotbyName(String name) {
		int count = 0;
		for (Plot p : plots) {
			if (name.equals(p.name)) {
				return p;
			}
		}
		return null;
	}
	
	public Plot getPlotByLocation(int x, int z) {
		String name = "";
		for (Plot p : plots) {
			if(p.overlaps(x, z)) {
				name = p.name;
				return p;
			}
		}
		return null;
	}
	
	public void addAllowedUser(String user, String plot) {
		
	}

	public void save() { 
		String data = "";
		for (Plot p : plots) {
			String allowedUsersList = "";
			for (String userId : p.allowedUsers) {
				allowedUsersList += userId + ',';
			}
			data += p.x + "|" + p.z + "|" + p.sizeX + "|" + p.sizeZ + "|" + p.admin + "|" + p.name + "|" + p.admin + "," + allowedUsersList + "\n";
		}

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(FILE_NAME, false);
			outputStream.write(data.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void load() {
		try {
			Scanner myReader = new Scanner(new File(FILE_NAME));
			while (myReader.hasNextLine()) {
				String[] line = myReader.nextLine().split("\\|");
				int x = Integer.parseInt(line[0]);
				int z = Integer.parseInt(line[1]);
				int sizeX = Integer.parseInt(line[2]);
				int sizeZ = Integer.parseInt(line[3]);
				String admin = line[4];
				String name = line[5];
				String[] allowedUsers = line[6].split("\\,");
				// System.out.println("x: " + x + " z: " + z + " sx: " + sizeX + " sz: " + sizeZ
				// + " admin: " + admin);

				Plot plot;
				try {
					plot = new Plot(x, z, sizeX, sizeZ, admin, name, allowedUsers);
					if (add(plot)) {
						System.out.println("Added plot " + plot.toString());
					} else {
						System.out.println("Could not add plot");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found! (Don't worry it will be created as soon as someone makes a plot)");
			e.printStackTrace();
		}
	}

	public String toString() {
		String out = "Plots: " + plots.size() + "\n";
		for (Plot p : plots) {
			out += p.toString() + "\n";
		}
		return out;
	}

}

package com.github.tolek.plot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
		for (Plot p : plots) {
			if (name.equals(p.name))
				return p;
		}
		return null;
	}
	
	public Plot getPlotByLocation(int x, int z) {
		for (Plot p : plots) {
			if (p.overlaps(x, z))
				return p;
		}
		return null;
	}

	public void save() {
		String data = plots.stream().map(Plot::exportToString).collect(Collectors.joining("\n"));
		System.out.println("Saving file\n" + data);
		try {
			FileOutputStream writer = new FileOutputStream(FILE_NAME, false);
			writer.write(data.getBytes());
			writer.close();
		} catch (IOException e) {
			System.err.println(("Could not write file"));
		}
	}

	public void load() {
		try {
			Scanner reader = new Scanner(new File(FILE_NAME));
			while (reader.hasNextLine()) {
				try {
					Plot plot = Plot.importFromStorage(reader.nextLine());
					if (!add(plot))
						System.out.println("Could not add plot");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Plots file not found, not loading");
		}
	}

	public String toString() {
		return "Plots: " + plots.size() + "\n" + plots.stream().map(Plot::toString).collect(Collectors.joining("\n"));
	}

}

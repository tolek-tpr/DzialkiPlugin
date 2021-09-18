package com.github.tolek.dzialki.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import com.github.tolek.dzialki.plot.Plot;

public class SettingManager {
	
	final String FILE_NAME = "settings.dzplugin";

	public void save() {
		String data = Plot.MAX_SIZE + "|" + Plot.MAX_PLOTS;
		System.out.println("Saving file\n" + data);
		try {
			FileWriter writer = new FileWriter(FILE_NAME, false);
			writer.write(Plot.MAX_SIZE + "|" + Plot.MAX_PLOTS);
			writer.close();
		} catch (IOException e) {
			System.err.println(("Could not write file"));
		}
	}
	
	public void load() {
		try {
			Scanner reader = new Scanner(new File(FILE_NAME));
			while (reader.hasNextLine()) {
				String[] data = reader.nextLine().split("\\|");
				Plot.MAX_SIZE = Integer.parseInt(data[0]);
				Plot.MAX_PLOTS = Integer.parseInt(data[1]);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Plots file not found, not loading");
		}
	}
	
}

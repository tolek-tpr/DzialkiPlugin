package com.github.tolek.dzialki.plot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

public class Plot {

	public static int MAX_SIZE = 100;
	public static int MAX_PLOTS = 2;

	public String name;
	public Type type;

	public int x;
	public int z;
	public int sizeX;
	public int sizeZ;

	public String owner;
	public List<String> allowedUsers = new LinkedList<String>();
	public Plot(String name, int x, int z, int sizeX, int sizeZ, String owner, Type type) {
		this.name = name;
		this.x = x;
		this.z = z;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.owner = owner;
		this.type = type;
	}

	public Plot(String name, int x, int z, int sizeX, int sizeZ, String owner, Type type, List<String> allowedUsers) {
		this.name = name;
		this.x = x;
		this.z = z;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.owner = owner;
		this.type = type;
		this.allowedUsers = allowedUsers;
	}

	public boolean overlaps(Plot p) {
		int xs1 = this.x;
		int xe1 = xs1 + this.sizeX;
		int xs2 = p.x;
		int xe2 = xs2 + p.sizeX;

		int zs1 = this.z;
		int ze1 = zs1 + this.sizeZ;
		int zs2 = p.z;
		int ze2 = zs2 + p.sizeZ;

		boolean overlapsX = xs2 >= xs1 && xs2 <= xe1 || xe2 >= xs1 & xe2 <= xe1;
		boolean overlapsZ = zs2 >= zs1 && zs2 <= ze1 || ze2 >= zs1 & ze2 <= ze1;

		return overlapsX || overlapsZ;
	}

	public boolean overlaps(int x, int z) {
		int xs1 = this.x;
		int xe1 = xs1 + this.sizeX;

		int zs1 = this.z;
		int ze1 = zs1 + this.sizeZ;

		boolean overlapsX = x >= xs1 && x <= xe1;
		boolean overlapsZ = z >= zs1 && z <= ze1;

		return overlapsX && overlapsZ;

	}

	public boolean isOwnedBy(Player p) {
		return p != null && p.getName().equals(owner);
	}

	public boolean isOwnedBy(String name) {
		return name != null && name.equals(owner);
	}

	public boolean isAccessibleBy(Player p) {
		return p != null && allowedUsers.contains(p.getName());
	}

	public boolean isAccessibleBy(String name) {
		return name != null && allowedUsers.contains(name);
	}

	public Type getPlotType(Plot p) { return p.type; }

	public void setPlotType(String type) { this.type = Type.valueOf(type);


	}

	public boolean addUser(Player p) {
		return p != null && allowedUsers.add(p.getName());
	}

	public boolean addUser(String name) {
		return !"".equals(name) && !owner.equals(name) && !allowedUsers.contains(name) && allowedUsers.add(name);
	}

	public boolean removeUser(Player p) {
		return p != null && allowedUsers.remove(p.getName());
	}

	public boolean removeUser(String name) {
		return name != null && allowedUsers.remove(name);
	}

	public static Plot importFromStorage(String line) {
		String[] items = line.split("\\|");
		Type type = items.length < 7 ? Type.NONE : Type.valueOf(items[6]);
		List<String> allowedUsers = items.length < 8 ? new LinkedList<String>()
				: Stream.of(items[7].split("\\,")).collect(Collectors.toList());
		return new Plot(items[0], Integer.parseInt(items[1]), Integer.parseInt(items[2]), Integer.parseInt(items[3]),
				Integer.parseInt(items[4]), items[5], type, allowedUsers);
	}

	public String exportToStorage() {
		return String.join("|", name, String.valueOf(x), String.valueOf(z), String.valueOf(sizeX),
				String.valueOf(sizeZ), owner, type == null ? "" : type.toString(), String.join(",", allowedUsers));
	}

	public String toString() {
		return name + " [" + x + ":" + z + "][" + sizeX + ":" + sizeZ + "] by " + owner + " type " + type;
	}

















}

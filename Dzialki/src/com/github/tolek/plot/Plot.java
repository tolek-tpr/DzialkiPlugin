package com.github.tolek.plot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Plot {

	public static int MAX_SIZE = 100;
	public static int MAX_PLOTS = 2;

	public int x;
	public int z;
	public int sizeX;
	public int sizeZ;

	public String admin;
	public String name;
	public List<String> allowedUsers = new ArrayList<String>();

	public Plot(int x, int z, int sizeX, int sizeZ, String admin, String name) throws Exception {
		if (sizeX > MAX_SIZE || sizeZ > MAX_SIZE) {
			throw new Exception("Plot Too Large");
		}
		this.x = x;
		this.z = z;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.admin = admin;
		this.name = name;
	}
	
	public Plot(int x, int z, int sizeX, int sizeZ, String admin, String name, String[] allowedUsers) throws Exception {
		if (sizeX > MAX_SIZE || sizeZ > MAX_SIZE) {
			throw new Exception("Plot Too Large");
		}
		this.x = x;
		this.z = z;
		this.sizeX = sizeX;
		this.sizeZ = sizeZ;
		this.admin = admin;
		this.name = name;
		this.allowedUsers = List.of(allowedUsers);
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

	public String toString() {
		return name + " [" + x + ":" + z + "][" + sizeX + ":" + sizeZ + "] by " + admin;
	}

	public boolean isOwnedBy(Player p) {
		return admin.equals(p.getUniqueId().toString());
	}

	public boolean isOwnedBy(String id) {
		return admin.equals(id);
	}

	public boolean addUser(String id) {
		return "".equals(id) && allowedUsers.add(id);
	}

	public boolean isAccessibleBy(Player p) {
		return p != null && allowedUsers.contains(p.getUniqueId().toString());
	}
	
	public boolean removeUser(String id) {
		return allowedUsers.remove(id);
	}
}

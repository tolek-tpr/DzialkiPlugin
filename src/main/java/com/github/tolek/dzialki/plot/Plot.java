package com.github.tolek.dzialki.plot;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class Plot {

    public static int MAX_SIZE = 100;
    public static int MAX_PLOTS = 2;

    public String name;
    
    public int x;
    public int z;
    public int sizeX;
    public int sizeZ;

    public String owner;
    public List<String> allowedUsers = new ArrayList<String>();

    public Plot(String name, int x, int z, int sizeX, int sizeZ, String admin) throws Exception {
        if (sizeX > MAX_SIZE || sizeZ > MAX_SIZE) {
            throw new Exception("Plot Too Large");
        }
        this.name = name;
        this.x = x;
        this.z = z;
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        this.owner = admin;
    }

    public Plot(String name, int x, int z, int sizeX, int sizeZ, String admin, List<String> allowedUsers) throws Exception {
        if (sizeX > MAX_SIZE || sizeZ > MAX_SIZE) {
            throw new Exception("Plot Too Large");
        }
        this.name = name;
        this.x = x;
        this.z = z;
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
        this.owner = admin;
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
        return p != null && owner.equals(p.getUniqueId().toString());
    }

    public boolean isOwnedBy(String id) {
        return owner.equals(id);
    }

    public boolean isAccessibleBy(Player p) {
        return p != null && allowedUsers.contains(p.getUniqueId().toString());
    }

    public boolean isAccessibleBy(String id) {
        return id != null && allowedUsers.contains(id);
    }

    public boolean addUser(Player p) {
        return p != null && allowedUsers.add(p.getUniqueId().toString());
    }

    public boolean addUser(String id) {
        return !"".equals(id) && allowedUsers.add(id);
    }

    public boolean removeUser(Player p) {
        return allowedUsers.remove(p.getUniqueId().toString());
    }

    public boolean removeUser(String id) {
        return allowedUsers.remove(id);
    }

    public static Plot importFromStorage(String line) throws Exception {
        String[] items = line.split("\\|");
        System.out.println(items);
        return new Plot(items[0],
                Integer.parseInt(items[1]),
                Integer.parseInt(items[2]),
                Integer.parseInt(items[3]),
                Integer.parseInt(items[4]),
                items[5],
                items.length < 7 ? new ArrayList<String>() : List.of(items[6].split("\\,")));
    }

    public String exportToStorage() {
        return String.join("|", name, String.valueOf(x), String.valueOf(z), String.valueOf(sizeX), String.valueOf(sizeZ), owner, String.join(",", allowedUsers));
    }

    public String toString() {
        return name + " [" + x + ":" + z + "][" + sizeX + ":" + sizeZ + "] by " + owner;
    }

}

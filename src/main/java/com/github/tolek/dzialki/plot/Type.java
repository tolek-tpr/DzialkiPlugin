package com.github.tolek.dzialki.plot;

public enum Type {
    NONE("NONE"),
    STORE("STORE"),
    BLACKSMITH("BLACKSMITH"),
    HOSPITAL("HOSPITAL"),
    ESTATE_AGENCY("ESTATE_AGENCY");

    private String name;

    Type(String name) {
        this.name = name == null ? "NONE" : name;
    }

    public String toString() {
        return name;
    }
}

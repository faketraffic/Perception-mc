package xyz.tharmsy.perception.module;

public enum Category {
    COMBAT("Combat"),
    MOVEMENT("Movement"),
    RENDER("Render"),
    PLAYER("Player"),
    SCRIPT("Script");

    public String name;

    Category(String name) {
        this.name = name;
    }
}

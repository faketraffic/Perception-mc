package xyz.tharmsy.perception.module;

import xyz.tharmsy.perception.Perception;

public class Module {
    private String name;
    private String description;
    private int keybind;
    private boolean toggled;

    public Module(String name, String description, int keybind) {
        this.name = name;
        this.description = description;
        this.keybind = keybind;
    }

    public void onEnable() {
        Perception.getInstance().getEventManager().register(this);
    }

    public void onDisable() {
        Perception.getInstance().getEventManager().unregister(this);
    }

    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            onEnable();
        } else {
            onDisable();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getKeybind() {
        return keybind;
    }

    public void setKeybind(int keybind) {
        this.keybind = keybind;
    }

    public boolean isToggled() {
        return toggled;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}

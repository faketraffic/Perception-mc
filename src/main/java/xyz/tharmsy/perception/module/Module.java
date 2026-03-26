package xyz.tharmsy.perception.module;

import xyz.tharmsy.perception.Perception;
import xyz.tharmsy.perception.settings.Setting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Module {
    private String name;
    private String description;
    private int keybind;
    private boolean toggled;
    private Category category;

    private final List<Setting> settings = new ArrayList<>();

    public Module(String name, String description, int keybind) {
        this(name, description, keybind, Category.RENDER);
    }

    public Module(String name, String description, int keybind, Category category) {
        this.name = name;
        this.description = description;
        this.keybind = keybind;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void addSettings(Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }

    public List<Setting> getSettings() {
        return settings;
    }
}

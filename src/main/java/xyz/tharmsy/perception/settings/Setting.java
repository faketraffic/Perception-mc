package xyz.tharmsy.perception.settings;

public class Setting {
    public String name;
    protected boolean hidden;

    public Setting(String name) {
        this.name = name;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
}

package xyz.tharmsy.perception.module;

import xyz.tharmsy.perception.event.EventTarget;
import xyz.tharmsy.perception.event.impl.EventKey;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private final List<Module> modules = new ArrayList<>();

    public ModuleManager() {
        modules.add(new xyz.tharmsy.perception.module.impl.Sprint());
        modules.add(new xyz.tharmsy.perception.module.impl.ClickGUIModule());
    }

    public List<Module> getModules() {
        return modules;
    }

    @EventTarget
    public void onKey(EventKey event) {
        for (Module m : modules) {
            if (m.getKeybind() == event.getKey()) {
                m.toggle();
            }
        }
    }
}

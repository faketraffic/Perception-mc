package xyz.tharmsy.perception;

import xyz.tharmsy.perception.command.CommandManager;
import xyz.tharmsy.perception.event.EventManager;
import xyz.tharmsy.perception.module.ModuleManager;
import xyz.tharmsy.perception.ui.hud.HUD;

public class Perception {
    public static final String NAME = "Perception";
    public static final String VERSION = "0.1";

    private static Perception instance = new Perception();

    private EventManager eventManager;
    private ModuleManager moduleManager;
    private CommandManager commandManager;

    public static Perception getInstance() {
        return instance;
    }

    public void init() {
        // System.out.println("Starting " + NAME + " v" + VERSION);
        this.eventManager = new EventManager();
        this.moduleManager = new ModuleManager();
        this.commandManager = new CommandManager();
        xyz.tharmsy.perception.utils.render.font.FontUtil.bootstrap();

        this.eventManager.register(this.moduleManager);
        this.eventManager.register(this.commandManager);
        new HUD();
    }

    public void shutdown() {
        System.out.println("Shutting down " + NAME);
        // clean.up() todo shit idk
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}

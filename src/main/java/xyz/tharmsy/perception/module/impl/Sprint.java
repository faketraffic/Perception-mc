package xyz.tharmsy.perception.module.impl;

import org.lwjgl.input.Keyboard;
import xyz.tharmsy.perception.event.EventTarget;
import xyz.tharmsy.perception.event.impl.EventUpdate;
import xyz.tharmsy.perception.module.Module;
import net.minecraft.client.Minecraft;

import xyz.tharmsy.perception.settings.impl.BooleanSetting;
import xyz.tharmsy.perception.settings.impl.ModeSetting;

public class Sprint extends Module {
    private final BooleanSetting omniSprint = new BooleanSetting("OmniSprint", false);
    private final ModeSetting mode = new ModeSetting("Mode", "Legit", "Legit", "Rage", "Spoof");

    public Sprint() {
        super("Sprint", "Automatically sprints for you.", Keyboard.KEY_V);
        addSettings(mode, omniSprint);
    }

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.moveForward > 0
                && !Minecraft.getMinecraft().thePlayer.isCollidedHorizontally) {
            Minecraft.getMinecraft().thePlayer.setSprinting(true);
        }
    }
}

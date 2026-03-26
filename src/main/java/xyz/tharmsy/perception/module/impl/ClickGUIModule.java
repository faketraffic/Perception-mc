package xyz.tharmsy.perception.module.impl;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import xyz.tharmsy.perception.module.Category;
import xyz.tharmsy.perception.module.Module;
import xyz.tharmsy.perception.ui.clickgui.ClickGUI;

public class ClickGUIModule extends Module {
    public ClickGUI clickGUI;

    public ClickGUIModule() {
        super("ClickGUI", "Displays this menu.", Keyboard.KEY_RSHIFT, Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (clickGUI == null) {
            clickGUI = new ClickGUI();
        }
        Minecraft.getMinecraft().displayGuiScreen(clickGUI);
        this.toggle();
    }
}

package xyz.tharmsy.perception.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import xyz.tharmsy.perception.Perception;
import xyz.tharmsy.perception.event.EventTarget;
import xyz.tharmsy.perception.event.impl.EventRender2D;
import xyz.tharmsy.perception.utils.render.RenderUtils;

public class HUD {

    public HUD() {
        Perception.getInstance().getEventManager().register(this);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
        String text = Perception.NAME + " v" + Perception.VERSION;

        RenderUtils.drawRect(2, 2, fr.getStringWidth(text) + 4, fr.FONT_HEIGHT + 4, 0x90000000);
        fr.drawStringWithShadow(text, 4, 4, -1);
    }
}

package xyz.tharmsy.perception.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import xyz.tharmsy.perception.Perception;
import xyz.tharmsy.perception.event.EventTarget;
import xyz.tharmsy.perception.event.impl.EventRender2D;
import xyz.tharmsy.perception.utils.render.RenderUtils;
import xyz.tharmsy.perception.utils.render.font.FontUtil;

public class HUD {

    public HUD() {
        Perception.getInstance().getEventManager().register(this);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        String text = Perception.NAME + " v" + Perception.VERSION;
        int width = FontUtil.font.getStringWidth(text);
        int height = FontUtil.font.getHeight();

        RenderUtils.drawRect(2, 2, width + 4, height + 4, 0x90000000);
        FontUtil.font.drawStringWithShadow(text, 4, 4, -1);
    }
}

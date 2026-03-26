package xyz.tharmsy.perception.ui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import xyz.tharmsy.perception.Perception;
import xyz.tharmsy.perception.event.EventTarget;
import xyz.tharmsy.perception.event.impl.EventRender2D;
import xyz.tharmsy.perception.utils.render.RenderUtils;
import xyz.tharmsy.perception.utils.render.font.FontUtil;
import xyz.tharmsy.perception.module.Module;

import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;

public class HUD {

    public HUD() {
        Perception.getInstance().getEventManager().register(this);
    }

    @EventTarget
    public void onRender2D(EventRender2D event) {
        String nameStart = "Perce";
        String nameEnd = "ption";
        String extra = " | " + Minecraft.getMinecraft().getSession().getUsername() + " | " + Minecraft.getDebugFPS()
                + " FPS";

        int nameStartWidth = FontUtil.font.getStringWidth(nameStart);
        int nameEndWidth = FontUtil.font.getStringWidth(nameEnd);
        int extraWidth = FontUtil.font.getStringWidth(extra);

        int totalWidth = nameStartWidth + nameEndWidth + extraWidth;
        int height = FontUtil.font.getHeight();

        float x = 4;
        float y = 4;
        float width = totalWidth + 10;
        float h = height + 10;

        RenderUtils.drawRect(x, y, width, h, new Color(10, 10, 10, 255).getRGB()); // Outer black
        RenderUtils.drawRect(x + 1, y + 1, width - 2, h - 2, new Color(45, 45, 45, 255).getRGB()); // Grey
        RenderUtils.drawRect(x + 2, y + 2, width - 4, h - 4, new Color(10, 10, 10, 255).getRGB()); // Inner black
        RenderUtils.drawRect(x + 3, y + 3, width - 6, h - 6, new Color(15, 15, 15, 255).getRGB()); // Background
        double segment = (width - 6) / 4.0;
        RenderUtils.drawGradientSideways(x + 3, y + 3, x + 3 + segment, y + 4, 0xFF1E88E5, 0xFF8E24AA);
        RenderUtils.drawGradientSideways(x + 3 + segment, y + 3, x + 3 + segment * 2, y + 4, 0xFF8E24AA, 0xFFE53935);
        RenderUtils.drawGradientSideways(x + 3 + segment * 2, y + 3, x + 3 + segment * 3, y + 4, 0xFFE53935,
                0xFFFDD835);
        RenderUtils.drawGradientSideways(x + 3 + segment * 3, y + 3, x + 3 + width - 6, y + 4, 0xFFFDD835, 0xFF43A047);

        float textY = y + 7;
        float textX = x + 5;

        FontUtil.font.drawStringWithShadow(nameStart, textX, textY, -1); // White
        textX += nameStartWidth;
        FontUtil.font.drawStringWithShadow(nameEnd, textX, textY, new Color(160, 200, 30).getRGB()); // Classic Skeet
                                                                                                     // Green
        textX += nameEndWidth;
        FontUtil.font.drawStringWithShadow(extra, textX, textY, -1); // White

        net.minecraft.client.gui.ScaledResolution sr = new net.minecraft.client.gui.ScaledResolution(
                Minecraft.getMinecraft());

        List<Module> activeModules = Perception.getInstance().getModuleManager().getModules()
                .stream()
                .filter(Module::isToggled)
                .sorted((m1, m2) -> FontUtil.font.getStringWidth(m2.getName())
                        - FontUtil.font.getStringWidth(m1.getName()))
                .collect(Collectors.toList());

        float listY = 2;
        for (Module module : activeModules) {
            String name = module.getName();
            int modWidth = FontUtil.font.getStringWidth(name);
            int modHeight = FontUtil.font.getHeight() + 2;

            float listX = sr.getScaledWidth() - modWidth - 6;

            RenderUtils.drawRect(listX, listY, modWidth + 6, modHeight, new Color(20, 20, 20, 180).getRGB());
            RenderUtils.drawRect(sr.getScaledWidth() - 2, listY, 2, modHeight, new Color(160, 200, 30).getRGB());
            FontUtil.font.drawStringWithShadow(name, listX + 2, listY + 1f, -1);

            listY += modHeight;
        }
    }
}

package xyz.tharmsy.perception.ui.clickgui;

import xyz.tharmsy.perception.Perception;
import xyz.tharmsy.perception.module.Category;
import xyz.tharmsy.perception.module.Module;
import xyz.tharmsy.perception.utils.render.RenderUtils;
import xyz.tharmsy.perception.utils.render.font.FontUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Panel {
    public Category category;
    public float x, y, width, height;
    public boolean extended = true;
    public boolean dragging = false;
    private float dragX, dragY;

    private List<Button> buttons = new ArrayList<>();

    public Panel(Category category, float x, float y, float width, float height) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        for (Module m : Perception.getInstance().getModuleManager().getModules()) {
            if (m.getCategory() == category) {
                buttons.add(new Button(m, this));
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        // Draw Header
        RenderUtils.drawRect(x, y, width, height, new Color(15, 15, 15, 255).getRGB());
        RenderUtils.drawGradientSideways(x, y + height - 1, x + width, y + height, 0xFF2A8EE1, 0xFF43A047);
        FontUtil.font.drawStringWithShadow(category.name, x + 4, y + 4, -1);

        if (extended) {
            float bY = height;
            for (Button button : buttons) {
                button.offsetY = bY;
                button.drawScreen(mouseX, mouseY, partialTicks);
                bY += button.getComponentHeight();
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            if (mouseButton == 0) {
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            } else if (mouseButton == 1) {
                extended = !extended;
            }
        }

        if (extended) {
            for (Button button : buttons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            dragging = false;
        }
    }

    private boolean isHovered(int mouseX, int mouseY) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}

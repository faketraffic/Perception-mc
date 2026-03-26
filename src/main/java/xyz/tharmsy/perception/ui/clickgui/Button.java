package xyz.tharmsy.perception.ui.clickgui;

import xyz.tharmsy.perception.module.Module;
import xyz.tharmsy.perception.settings.Setting;
import xyz.tharmsy.perception.settings.impl.BooleanSetting;
import xyz.tharmsy.perception.settings.impl.ModeSetting;
import xyz.tharmsy.perception.settings.impl.NumberSetting;
import xyz.tharmsy.perception.utils.render.RenderUtils;
import xyz.tharmsy.perception.utils.render.font.FontUtil;

import java.awt.*;

public class Button {
    public Module module;
    public Panel parent;
    public float offsetY;
    public boolean expanded = false;

    public Button(Module module, Panel parent) {
        this.module = module;
        this.parent = parent;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        float x = parent.x;
        float y = parent.y + offsetY;
        float width = parent.width;
        float height = 18;

        int color = module.isToggled() ? new Color(160, 200, 30, 200).getRGB() : new Color(30, 30, 30, 200).getRGB();
        if (isHovered(mouseX, mouseY, x, y, width, height)) {
            color = module.isToggled() ? new Color(175, 210, 45, 255).getRGB() : new Color(40, 40, 40, 255).getRGB();
        }

        RenderUtils.drawRect(x, y, width, height, color);
        // Cast to (int) for perfectly sharp fonts on unscaled monitors
        int stringY = (int) (y + height / 2 - FontUtil.font.getHeight() / 2f);
        FontUtil.font.drawString(module.getName(), (int) (x + 4), stringY, -1);

        if (expanded) {
            float setY = y + height;
            for (Setting setting : module.getSettings()) {
                RenderUtils.drawRect(x, setY, width, 16, new Color(20, 20, 20, 250).getRGB());
                FontUtil.fontSmall.drawString(setting.name, (int) (x + 6), (int) (setY + 4), -1);

                if (setting instanceof BooleanSetting) {
                    BooleanSetting bs = (BooleanSetting) setting;
                    FontUtil.fontSmall.drawString(bs.isEnabled() ? "[x]" : "[ ]", (int) (x + width - 18),
                            (int) (setY + 4), bs.isEnabled() ? new Color(160, 200, 30).getRGB() : -1);
                } else if (setting instanceof ModeSetting) {
                    ModeSetting ms = (ModeSetting) setting;
                    FontUtil.fontSmall.drawString(ms.getMode(),
                            (int) (x + width - FontUtil.fontSmall.getStringWidth(ms.getMode()) - 5), (int) (setY + 4),
                            -1);
                } else if (setting instanceof NumberSetting) {
                    NumberSetting ns = (NumberSetting) setting;
                    String val = String.valueOf(ns.getValue());
                    FontUtil.fontSmall.drawString(val, (int) (x + width - FontUtil.fontSmall.getStringWidth(val) - 5),
                            (int) (setY + 4), -1);
                }

                setY += 16;
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        float x = parent.x;
        float y = parent.y + offsetY;
        float width = parent.width;

        if (isHovered(mouseX, mouseY, x, y, width, 18)) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1) {
                expanded = !expanded;
            }
        }

        if (expanded) {
            float setY = y + 18;
            for (Setting setting : module.getSettings()) {
                if (isHovered(mouseX, mouseY, x, setY, width, 16)) {
                    if (mouseButton == 0) {
                        if (setting instanceof BooleanSetting) {
                            ((BooleanSetting) setting).toggle();
                        } else if (setting instanceof ModeSetting) {
                            ((ModeSetting) setting).cycle();
                        }
                    }
                }
                setY += 16;
            }
        }
    }

    public float getComponentHeight() {
        if (!expanded)
            return 18;
        return 18 + (module.getSettings().size() * 16);
    }

    private boolean isHovered(int mouseX, int mouseY, float x, float y, float width, float height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }
}

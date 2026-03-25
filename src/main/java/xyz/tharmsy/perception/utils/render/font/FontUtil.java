package xyz.tharmsy.perception.utils.render.font;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.awt.*;
import java.io.InputStream;

public class FontUtil {

    public static CustomFontRenderer font;
    public static CustomFontRenderer fontSmall;
    public static CustomFontRenderer fontLarge;

    public static void bootstrap() {
        font = new CustomFontRenderer(new Font("Tahoma", Font.PLAIN, 19), true, true);
        fontSmall = new CustomFontRenderer(new Font("Tahoma", Font.PLAIN, 15), true, true);
        fontLarge = new CustomFontRenderer(new Font("Tahoma", Font.PLAIN, 24), true, true);
    }

    public static Font getFontFromTTF(ResourceLocation loc, float size, int fontType) {
        try {
            InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
            Font font = Font.createFont(fontType, is);
            font = font.deriveFont(fontType, size);
            return font;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading custom font from resources. Using system font instead.");
            return new Font("default", fontType, (int) size);
        }
    }
}

package xyz.tharmsy.perception.utils.render.font;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class CustomFontRenderer extends CustomFont {

    protected CharacterData[] boldChars = new CharacterData[256];
    protected CharacterData[] italicChars = new CharacterData[256];
    protected CharacterData[] boldItalicChars = new CharacterData[256];

    private final int[] colorCode = new int[32];

    public CustomFontRenderer(Font font, boolean antiAlias, boolean fractionalMetrics) {
        super(font, antiAlias, fractionalMetrics);
        setupMinecraftColorcodes();
        setupBoldItalicIDs();
    }

    public float drawStringWithShadow(String text, double x, double y, int color) {
        float shadowWidth = drawString(text, x + 1D, y + 1D, color, true);
        return Math.max(shadowWidth, drawString(text, x, y, color, false));
    }

    public float drawString(String text, float x, float y, int color) {
        return drawString(text, x, y, color, false);
    }

    public float drawStringWithShadow(String text, float x, float y, int color) {
        float shadowWidth = drawString(text, x + 1.0F, y + 1.0F, color, true);
        return Math.max(shadowWidth, drawString(text, x, y, color, false));
    }

    public float drawCenterStringWithShadow(String text, float x, float y, int color) {
        return drawStringWithShadow(text, x - getStringWidth(text) / 2f, y, color);
    }

    public float drawCenterString(String text, float x, float y, int color) {
        return drawString(text, x - getStringWidth(text) / 2f, y, color);
    }

    public float drawString(String text, double x, double y, int color, boolean shadow) {
        x -= 1;
        y -= 2;

        if (text == null) {
            return 0.0F;
        }

        if (color == 553648127) {
            color = 16777215;
        }

        if ((color & 0xFC000000) == 0) {
            color |= 0xFF000000;
        }

        if (shadow) {
            color = (color & 0xFCFCFC) >> 2 | color & 0xFF000000;
        }

        CharacterData[] characterData = this.charData;

        float alpha = (color >> 24 & 0xFF) / 255.0F;
        boolean bold = false;
        boolean italic = false;
        boolean strikethrough = false;
        boolean underline = false;

        x *= 2.0D;
        y *= 2.0D;

        GL11.glPushMatrix();
        GlStateManager.scale(0.5D, 0.5D, 0.5D);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F,
                alpha);

        int size = text.length();
        GlStateManager.enableTexture2D();
        GlStateManager.bindTexture(tex.getGlTextureId());

        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex.getGlTextureId());

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);

            if (character == '§' && i < size) {
                int colorIndex = 21;

                try {
                    colorIndex = "0123456789abcdefklmnor".indexOf(text.charAt(i + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (colorIndex < 16) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.bindTexture(tex.getGlTextureId());
                    characterData = this.charData;

                    if (colorIndex < 0 || colorIndex > 15) {
                        colorIndex = 15;
                    }

                    if (shadow) {
                        colorIndex += 16;
                    }

                    int colorCode = this.colorCode[colorIndex];
                    GlStateManager.color((colorCode >> 16 & 0xFF) / 255.0F, (colorCode >> 8 & 0xFF) / 255.0F,
                            (colorCode & 0xFF) / 255.0F, alpha);
                } else if (colorIndex == 17) {
                    bold = true;
                    if (italic) {
                        GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                        characterData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(texBold.getGlTextureId());
                        characterData = this.boldChars;
                    }
                } else if (colorIndex == 18) {
                    strikethrough = true;
                } else if (colorIndex == 19) {
                    underline = true;
                } else if (colorIndex == 20) {
                    italic = true;
                    if (bold) {
                        GlStateManager.bindTexture(texItalicBold.getGlTextureId());
                        characterData = this.boldItalicChars;
                    } else {
                        GlStateManager.bindTexture(texItalic.getGlTextureId());
                        characterData = this.italicChars;
                    }
                } else if (colorIndex == 21) {
                    bold = false;
                    italic = false;
                    underline = false;
                    strikethrough = false;
                    GlStateManager.color((color >> 16 & 0xFF) / 255.0F, (color >> 8 & 0xFF) / 255.0F,
                            (color & 0xFF) / 255.0F, alpha);
                    GlStateManager.bindTexture(tex.getGlTextureId());
                    characterData = this.charData;
                }
                i++;
            } else if (character < characterData.length) {
                GL11.glBegin(GL11.GL_QUADS);
                drawChar(characterData, character, (float) x, (float) y);
                GL11.glEnd();

                if (strikethrough) {
                    drawLine(x, y + (characterData[character].height / 2f), x + characterData[character].width - 8.0D,
                            y + (characterData[character].height / 2f), 1.0F);
                }
                if (underline) {
                    drawLine(x, y + characterData[character].height - 2.0D, x + characterData[character].width - 8.0D,
                            y + characterData[character].height - 2.0D, 1.0F);
                }
                x += characterData[character].width - 8 + this.charOffset;
            }
        }

        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glPopMatrix();

        return (float) x / 2.0F;
    }

    @Override
    public int getStringWidth(String text) {
        if (text == null) {
            return 0;
        }

        int width = 0;
        CharacterData[] characterData = this.charData;
        int size = text.length();

        for (int i = 0; i < size; i++) {
            char character = text.charAt(i);

            if (character == '§') {
                i++;
            } else if (character < characterData.length) {
                width += characterData[character].width - 8 + this.charOffset;
            }
        }

        return width / 2;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        setupBoldItalicIDs();
    }

    @Override
    public void setAntiAlias(boolean antiAlias) {
        super.setAntiAlias(antiAlias);
        setupBoldItalicIDs();
    }

    @Override
    public void setFractionalMetrics(boolean fractionalMetrics) {
        super.setFractionalMetrics(fractionalMetrics);
        setupBoldItalicIDs();
    }

    protected DynamicTexture texBold;
    protected DynamicTexture texItalic;
    protected DynamicTexture texItalicBold;

    private void setupBoldItalicIDs() {
        texBold = setupTexture(this.font.deriveFont(Font.BOLD), this.antiAlias, this.fractionalMetrics, this.boldChars);
        texItalic = setupTexture(this.font.deriveFont(Font.ITALIC), this.antiAlias, this.fractionalMetrics,
                this.italicChars);
        texItalicBold = setupTexture(this.font.deriveFont(Font.BOLD | Font.ITALIC), this.antiAlias,
                this.fractionalMetrics, this.boldItalicChars);
    }

    private void drawLine(double x, double y, double x1, double y1, float width) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glLineWidth(width);
        GL11.glBegin(GL11.GL_LINES);
        GL11.glVertex2d(x, y);
        GL11.glVertex2d(x1, y1);
        GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    private void setupMinecraftColorcodes() {
        for (int index = 0; index < 32; index++) {
            int noClue = (index >> 3 & 0x1) * 85;
            int red = (index >> 2 & 0x1) * 170 + noClue;
            int green = (index >> 1 & 0x1) * 170 + noClue;
            int blue = (index & 0x1) * 170 + noClue;

            if (index == 6) {
                red += 85;
            }

            if (index >= 16) {
                red /= 4;
                green /= 4;
                blue /= 4;
            }

            this.colorCode[index] = ((red & 0xFF) << 16 | (green & 0xFF) << 8 | blue & 0xFF);
        }
    }
}

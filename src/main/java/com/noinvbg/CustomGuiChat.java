package com.noinvbg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class CustomGuiChat extends GuiNewChat {

    private final Minecraft mc;

    public CustomGuiChat(Minecraft mc) {
        super(mc);
        this.mc = mc;
    }

    @Override
    public void func_146230_a(int updateCounter) {
        if (this.mc.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN) {
            int lineCount = this.func_146232_i();
            boolean isChatOpen = false;
            int j = 0;
            @SuppressWarnings("unchecked")
            List<ChatLine> drawnChatLines = this.field_146253_i;
            int totalLines = drawnChatLines.size();
            float opacity = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (totalLines > 0) {
                if (this.func_146241_e()) {
                    isChatOpen = true;
                }

                float scale = this.func_146244_h();
                int width = MathHelper.ceiling_float_int((float) this.func_146228_f() / scale);
                GL11.glPushMatrix();
                GL11.glTranslatef(2.0F, 20.0F, 0.0F);
                GL11.glScalef(scale, scale, 1.0F);

                for (int i = 0; i + this.scrollPos < drawnChatLines.size() && i < lineCount; ++i) {
                    ChatLine line = drawnChatLines.get(i + this.scrollPos);

                    if (line != null) {
                        int age = updateCounter - line.getUpdatedCounter();

                        if (age < 200 || isChatOpen) {
                            double alpha = (double) age / 200.0D;
                            alpha = 1.0D - alpha;
                            alpha *= 10.0D;
                            alpha = MathHelper.clamp_double(alpha, 0.0D, 1.0D);
                            alpha *= alpha;
                            int alphaInt = (int) (255.0D * alpha);

                            if (isChatOpen) {
                                alphaInt = 255;
                            }

                            alphaInt = (int) ((float) alphaInt * opacity);
                            ++j;

                            if (alphaInt > 3) {
                                int x = 0;
                                int y = -i * 9;

                                // Ak je zapnutý toggle, NEKRESLÍME pozadie
                                if (!Main.noChatBackground) {
                                    drawRect(x, y - 9, x + width + 4, y, alphaInt / 2 << 24);
                                }

                                String text = line.func_151461_a().getFormattedText();
                                GL11.glEnable(GL11.GL_BLEND);
                                this.mc.fontRenderer.drawStringWithShadow(text, x, y - 8, 16777215 + (alphaInt << 24));
                                GL11.glDisable(GL11.GL_ALPHA_TEST);
                            }
                        }
                    }
                }

                if (isChatOpen) {
                    int fontHeight = this.mc.fontRenderer.FONT_HEIGHT;
                    GL11.glTranslatef(-3.0F, 0.0F, 0.0F);
                    int totalHeight = totalLines * fontHeight + totalLines;
                    int visibleHeight = j * fontHeight + j;
                    int scrollbarY = this.scrollPos * visibleHeight / totalLines;
                    int scrollbarHeight = visibleHeight * visibleHeight / totalHeight;

                    if (totalHeight != visibleHeight) {
                        int alpha = scrollbarY > 0 ? 170 : 96;
                        int color = this.isScrolled ? 13382451 : 3355562;

                        if (!Main.noChatBackground) {
                            drawRect(0, -scrollbarY, 2, -scrollbarY - scrollbarHeight, color + (alpha << 24));
                            drawRect(2, -scrollbarY, 1, -scrollbarY - scrollbarHeight, 13421772 + (alpha << 24));
                        }
                    }
                }

                GL11.glPopMatrix();
            }
        }
    }
}

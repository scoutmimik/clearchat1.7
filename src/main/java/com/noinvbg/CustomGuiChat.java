package com.noinvbg;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;

public class CustomGuiChat extends GuiNewChat {

    public CustomGuiChat(Minecraft mc) {
        super(mc);
    }

    @Override
    public void drawRect(int left, int top, int right, int bottom, int color) {
        if (Main.noChatBackground) {
            return;
        }
        super.drawRect(left, top, right, bottom, color);
    }
}

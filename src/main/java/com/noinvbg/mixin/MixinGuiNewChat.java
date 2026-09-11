package com.noinvbg.mixin;

import com.noinvbg.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.client.gui.GuiNewChat.class)
public abstract class MixinGuiNewChat {

    @Redirect(
        method = "func_146230_a",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;func_73733_a(IIIII)V"
        )
    )
    private void onDrawChatBackground(int startX, int startY, int endX, int endY, int color) {
        if (!Main.noChatBackground) {
            net.minecraft.client.gui.Gui.drawRect(startX, startY, endX, endY, color);
        }
    }
}

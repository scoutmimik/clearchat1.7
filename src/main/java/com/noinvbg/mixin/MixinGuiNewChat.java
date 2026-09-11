package com.noinvbg.mixin;

import com.noinvbg.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(net.minecraft.client.gui.GuiNewChat.class)
public abstract class MixinGuiNewChat {

    @Redirect(
        method = "drawChat",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiNewChat;drawRect(IIIII)V"
        )
    )
    private void onDrawChatBackground(int startX, int startY, int endX, int endY, int color) {
        if (!Main.noInvBackground) {
            net.minecraft.client.gui.GuiNewChat.drawRect(startX, startY, endX, endY, color);
        }
        // Ak je zapnuté, drawRect sa preskočí (pozadie správy sa nakreslí s nulovou viditeľnosťou / nevykreslí sa)
    }
}

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
            target = "Lnet/minecraft/client/gui/GuiNewChat;func_73733_a(IIIII)V"
        )
    )
    private void onDrawChatBackground(int startX, int startY, int endX, int endY, int color) {
        if (!Main.noChatBackground) {
            // Zavolá pôvodnú metódu drawRect cez jej SRG ekvivalent func_73733_a
            ((net.minecraft.client.gui.GuiNewChat) (Object) this).func_73733_a(startX, startY, endX, endY, color);
        }
    }
}

package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {

    @Inject(
        method = "func_146230_a",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;func_73734_a(IIIII)V"
        ),
        cancellable = true
    )
    private void cancelChatBackground(int updateCounter, CallbackInfo ci) {
        if (Main.noChatBackground) {
            ci.cancel();
        }
    }
}

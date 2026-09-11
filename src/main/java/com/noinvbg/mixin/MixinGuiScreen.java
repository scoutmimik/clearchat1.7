package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
    public void onDrawBackground(int tint, CallbackInfo ci) {
        if (Main.noInvBackground) {
            ci.cancel();
        }
    }
}

package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {

    @Inject(method = "drawWorldBackground", at = @At("HEAD"), cancellable = true)
    public void onDrawWorldBackground(int tint, CallbackInfo ci) {
        if (Main.noInvBackground) {
            ci.cancel();
        }
    }
}

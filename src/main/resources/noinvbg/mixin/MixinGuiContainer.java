package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class MixinGuiContainer {

    @Inject(method = "drawDefaultBackground", at = @At("HEAD"), cancellable = true)
    private void onDrawDefaultBackground(CallbackInfo ci) {
        if (Main.noInvBackground) {
            ci.cancel();
        }
    }
}

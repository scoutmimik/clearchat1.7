package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer {
    @Shadow public abstract void drawDefaultBackground();

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void onDrawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        if (Main.noInvBackground) {
            // Ak je zapnuté skrytie pozadia, obíde vykreslenie
            // (prípadne môžeme zrušiť iba volanie drawDefaultBackground)
        }
    }
}

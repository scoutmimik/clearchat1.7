package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class MixinGuiContainer extends GuiScreen {

    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void onDrawScreenHead(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
        // Tu môžeme zachytiť začiatok
    }

    // Cielime priamo na metódu drawDefaultBackground, ktorú GuiContainer volá vo svojom drawScreen
    @Inject(method = "drawDefaultBackground", at = @At("HEAD"), cancellable = true)
    public void onDrawDefaultBackground(CallbackInfo ci) {
        if (Main.noInvBackground) {
            ci.cancel(); // Úplne zruší vykreslenie tmavej/rozmazanej tmavej vrstvy pozadia inventára
        }
    }
}

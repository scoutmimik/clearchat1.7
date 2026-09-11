package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Inject(method = "func_146270_b", at = @At("HEAD"), cancellable = true)
    public void onDrawDefaultBackground(CallbackInfo ci) {
        // Pozadie zrušíme iba vtedy, ak ide o kontajner (inventár, truhla atď.)
        if (Main.noInvBackground && ((Object) this) instanceof GuiContainer) {
            ci.cancel();
        }
    }
}

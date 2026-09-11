package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public abstract class MixinGuiScreen {

    @Inject(method = "func_146270_b", at = @At("HEAD"), cancellable = true)
    public void onDrawDefaultBackground(CallbackInfo ci) {
        // Vypne pozadie všade, okrem obrazovky so zoznamom modov (Mod Menu)
        if (Main.noInvBackground && !(((Object) this) instanceof cpw.mods.fml.client.GuiModList)) {
            ci.cancel();
        }
    }
}

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
        if (Main.noInvBackground) {
            GuiScreen screen = (GuiScreen) (Object) this;
            String className = screen.getClass().getName();

            boolean isOptionScreen = screen instanceof net.minecraft.client.gui.GuiOptions ||
                                     screen instanceof net.minecraft.client.gui.GuiVideoSettings ||
                                     screen instanceof net.minecraft.client.gui.GuiScreenOptionsSounds ||
                                     screen instanceof cpw.mods.fml.client.GuiModList ||
                                     className.contains("GuiChatSettings") ||
                                     className.contains("GuiDetailSettings") ||
                                     className.contains("GuiQualitySettings") ||
                                     className.contains("GuiPerformanceSettings") ||
                                     className.contains("GuiAnimationSettings") ||
                                     className.contains("GuiOtherSettings");

            if (!isOptionScreen) {
                ci.cancel();
            }
        }
    }
}

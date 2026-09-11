package com.noinvbg.mixin;

import com.noinvbg.Main;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(GuiNewChat.class)
public class MixinGuiNewChat {

    @ModifyArg(
        method = "func_146230_a",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/Gui;func_73734_a(IIIII)V"
        ),
        index = 4
    )
    private int modifyChatBackgroundColor(int color) {
        return Main.noChatBackground ? 0 : color;
    }
}

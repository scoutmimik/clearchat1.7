package com.clearchat;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class ChatBackgroundRemover {

    @SubscribeEvent
    public void onRenderChatBackground(RenderGameOverlayEvent.Pre event) {
        // V 1.7.10 vieme zachytiť chat overlay. 
        // Ak sa renderuje pozadie chatu, môžeme ho ovplyvniť.
        if (event.type == RenderGameOverlayEvent.ElementType.CHAT) {
            // V Minecraft kliente sa chatové pozadie kreslí ako súčasť GuiNewChat.
            // Úplne najjednoduchší spôsob bez ASM v 1.7.10, ak nechceš komplikácie,
            // je vynulovať opacitu chatu v nastaveniach Minecraftu priamo počas ticku,
            // alebo použiť tento event na zrušenie pozadia, pričom text zostane.
        }
    }
}

package com.noinvbg;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent;

public class NoBackgroundHandler {

    // Prepínač pre nastavenie (true = skryť stmavené pozadie)
    public static boolean enabled = true;

    @SubscribeEvent
    public void onBackgroundDraw(GuiScreenEvent.BackgroundDrawnEvent.Pre event) {
        // Skontrolujeme, či je funkcia zapnutá a či ide o GUI inventára/truhly
        if (enabled && event.gui instanceof GuiContainer) {
            // Zrušíme event, čím zabránime vykresleniu tmavého gradientu
            event.setCanceled(true);
        }
    }
}

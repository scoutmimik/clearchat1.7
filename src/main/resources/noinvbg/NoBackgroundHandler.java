package com.noinvbg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Main.MODID, name = Main.NAME, version = Main.VERSION)
public class Main {
    public static final String MODID = "nametag";
    public static final String NAME = "Nametag Mod";
    public static final String VERSION = "1.0";

    // Prepínač pre pozadie inventára (true = bez tmavého pozadia)
    public static boolean noInvBackground = true;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // Registrácia eventu pre skrývanie pozadia inventára
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        // Registrácia príkazov
        event.registerServerCommand(new CommandToggleNoInvBG());
    }

    @SubscribeEvent
    public void onBackgroundDraw(GuiScreenEvent.BackgroundDrawnEvent.Pre event) {
        // Ak je funkcia zapnutá a hráčka/hráč otvoril GuiContainer (inventár, truhla, crafting...)
        if (noInvBackground && event.gui instanceof GuiContainer) {
            // Zrušíme vykreslenie tmavého pozadia
            event.setCanceled(true);
        }
    }

    // Príkaz na prepínanie funkcie v hre (/noinvbg)
    public static class CommandToggleNoInvBG extends CommandBase {
        @Override
        public String getCommandName() {
            return "noinvbg";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/noinvbg";
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0; // Príkaz môže použiť každý hráč bez OP práv
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            noInvBackground = !noInvBackground;
            String status = noInvBackground ? EnumChatFormatting.GREEN + "ZAPNUTÉ" : EnumChatFormatting.RED + "VYPNUTÉ";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[Nametag] " + EnumChatFormatting.WHITE + "No Inventory Background je teraz " + status));
        }
    }
}

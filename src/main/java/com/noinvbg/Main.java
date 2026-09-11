package com.noinvbg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(
   modid = "noinvbg",
   version = "1.0",
   acceptedMinecraftVersions = "[1.7.10]"
)
public class Main {

    public static boolean noInvBackground = true;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        ClientCommandHandler.instance.registerCommand(new CommandToggleNoInvBG());
    }

    @SubscribeEvent
    public void onDrawScreen(GuiScreenEvent.DrawScreenEvent.Pre event) {
        if (noInvBackground && event != null && event.gui instanceof GuiContainer) {
            event.setCanceled(true);
        }
    }

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
            return 0;
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            noInvBackground = !noInvBackground;
            String status = noInvBackground ? EnumChatFormatting.GREEN + "ZAPNUTÉ" : EnumChatFormatting.RED + "VYPNUTÉ";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[NoInvBG] " + EnumChatFormatting.WHITE + "Pozadie je teraz " + status));
        }
    }
}

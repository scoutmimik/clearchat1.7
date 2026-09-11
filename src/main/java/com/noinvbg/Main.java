package com.noinvbg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

@Mod(
   modid = "noinvbg",
   version = "1.0",
   acceptedMinecraftVersions = "[1.7.10]"
)
public class Main {

    public static boolean noInvBackground = true;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandToggleNoInvBG());
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
            String status = noInvBackground ? EnumChatFormatting.WHITE + "ON" : EnumChatFormatting.WHITE + "OFF";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[NoInvBG] " + status));
        }
    }
}

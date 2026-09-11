package com.noinvbg;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(
   modid = "noinvbg",
   version = "1.0",
   acceptedMinecraftVersions = "[1.7.10]"
)
public class Main {

    public static boolean noInvBackground = true;
    public static boolean noChatBackground = true;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new CommandToggleNoInvBG());
        ClientCommandHandler.instance.registerCommand(new CommandToggleNoChatBG());
        
        // Registrácia eventu pre chat
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderChat(RenderGameOverlayEvent.Pre event) {
        if (event.type == RenderGameOverlayEvent.ElementType.CHAT && noChatBackground) {
            // Bezpečné zrušenie vykresľovania chatu bez priameho Mixinu na GuiNewChat
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
            String status = noInvBackground ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[NoInvBG] Inventory Background: " + status));
        }
    }

    public static class CommandToggleNoChatBG extends CommandBase {
        @Override
        public String getCommandName() {
            return "nochatbg";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/nochatbg";
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            noChatBackground = !noChatBackground;
            String status = noChatBackground ? EnumChatFormatting.GREEN + "ON" : EnumChatFormatting.RED + "OFF";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[NoChatBG] Chat Background: " + status));
        }
    }
}

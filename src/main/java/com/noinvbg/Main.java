package com.noinvbg;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.ClientCommandHandler;

import java.lang.reflect.Field;

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
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Minecraft mc = Minecraft.getMinecraft();
            
            // Opravená podmienka z 'persistantChatGUI' na 'getChatGUI()'
            if (mc != null && mc.ingameGUI != null && !(mc.ingameGUI.getChatGUI() instanceof CustomGuiChat)) {
                try {
                    // Dynamické hľadanie poľa podľa typu, ignoruje MCP/SRG názvy
                    for (Field field : GuiIngame.class.getDeclaredFields()) {
                        if (field.getType() == GuiNewChat.class) {
                            field.setAccessible(true);
                            field.set(mc.ingameGUI, new CustomGuiChat(mc));
                            break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class CommandToggleNoInvBG extends CommandBase {
        @Override
        public String getCommandName() {
            return "invbg";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/invbg";
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            noInvBackground = !noInvBackground;
            String status = noInvBackground ? EnumChatFormatting.RED + "OFF" : EnumChatFormatting.GREEN + "ON";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[NoInvBG] Inventory Background: " + status));
        }
    }

    public static class CommandToggleNoChatBG extends CommandBase {
        @Override
        public String getCommandName() {
            return "chatbg";
        }

        @Override
        public String getCommandUsage(ICommandSender sender) {
            return "/chatbg";
        }

        @Override
        public int getRequiredPermissionLevel() {
            return 0;
        }

        @Override
        public void processCommand(ICommandSender sender, String[] args) {
            noChatBackground = !noChatBackground;
            String status = noChatBackground ? EnumChatFormatting.RED + "OFF" : EnumChatFormatting.GREEN + "ON";
            sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "[NoChatBG] Chat Background: " + status));
        }
    }
}

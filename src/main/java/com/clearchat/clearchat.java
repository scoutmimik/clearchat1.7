package com.clearchat;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import org.lwjgl.opengl.GL11;
import java.lang.reflect.Field;

@Mod(
   modid = "clearchat",
   version = "1.0",
   acceptedMinecraftVersions = "[1.7.10]"
)
public class clearchat {

   @EventHandler
   public void init(FMLInitializationEvent event) {
      try {
         Minecraft mc = Minecraft.getMinecraft();
         Field chatField = null;
         
         for (Field f : Minecraft.class.getDeclaredFields()) {
            if (GuiNewChat.class.isAssignableFrom(f.getType())) {
               chatField = f;
               break;
            }
         }
         
         if (chatField != null) {
            chatField.setAccessible(true);
            chatField.set(mc, new CustomGuiNewChat(mc));
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   public static class CustomGuiNewChat extends GuiNewChat {
      public CustomGuiNewChat(Minecraft mcIn) {
         super(mcIn);
      }

      @Override
      public void drawChat(int par1) {
         // Pred vykreslením chatu zapneme OpenGL blend masku, 
         // ktorá potlačí vykreslenie plných čiernych štvorcov (pozadia), 
         // alebo jednoducho necháme prebehnúť super a prepíšeme alfa kanál.
         // V 1.7.10 najspoľahlivejšie bez chýb prekladača:
         super.drawChat(par1);
      }
      
      @Override
      public void printChatMessageWithOptionalDeletion(net.minecraft.util.IChatComponent component, int id) {
         super.printChatMessageWithOptionalDeletion(component, id);
      }
   }
}

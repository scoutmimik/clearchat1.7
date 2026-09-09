package com.clearchat;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import java.lang.reflect.Field;

@Mod(
   modid = "clearchat",
   version = "1.0",
   acceptedMinecraftVersions = "[1.7.10]",
   clientSideOnly = true
)
public class clearchat {

   @EventHandler
   public void init(FMLInitializationEvent event) {
      try {
         Minecraft mc = Minecraft.getMinecraft();
         Field chatField = null;
         
         // Nájdenie pole pre chat cez reflexiu (funguje aj po obfuskácii)
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
      protected void drawRect(int p_73734_1_, int p_73734_2_, int p_73734_3_, int p_73734_4_, int p_73734_5_) {
         // Úplne zablokuje vykresľovanie obdĺžnika (pozadia) pod chatom, 
         // pričom samotný text a ostatné prvky zostanú netknuté.
      }
   }
}

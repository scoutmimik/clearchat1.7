package com.clearchat;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.util.MathHelper;
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
      private final Minecraft mc;

      public CustomGuiNewChat(Minecraft mcIn) {
         super(mcIn);
         this.mc = mcIn;
      }

      @Override
      public void drawChat(int par1) {
         if (this.mc.gameSettings.chatVisibility != net.minecraft.entity.player.EntityPlayer.EnumChatVisibility.HIDDEN) {
            int i = this.getLineCount();
            boolean flag = false;
            int j = 0;
            int k = this.field_146253_i.size();
            float f = this.mc.gameSettings.chatOpacity * 0.9F + 0.1F;

            if (k > 0) {
               if (this.getChatOpen()) {
                  flag = true;
               }

               float f1 = this.getChatScale();
               int l = MathHelper.ceiling_float_int((float)this.getChatWidth() / f1);
               GL11.glPushMatrix();
               GL11.glTranslatef(2.0F, 20.0F, 0.0F);
               GL11.glScalef(f1, f1, 1.0F);

               for (int i1 = 0; i1 + this.scrollPos < this.field_146253_i.size() && i1 < i; ++i1) {
                  ChatLine chatline = (ChatLine)this.field_146253_i.get(i1 + this.scrollPos);

                  if (chatline != null) {
                     int j1 = par1 - chatline.getUpdatedCounter();

                     if (j1 < 200 || flag) {
                        double d0 = (double)j1 / 200.0D;
                        d0 = 1.0D - d0;
                        d0 *= 10.0D;
                        if (d0 < 0.0D) d0 = 0.0D;
                        if (d0 > 1.0D) d0 = 1.0D;
                        d0 *= d0;
                        int k1 = (int)(255.0D * d0);

                        if (flag) {
                           k1 = 255;
                        }

                        k1 = (int)((float)k1 * f);
                        ++j;

                        if (k1 > 3) {
                           int l1 = 0;
                           int i2 = -i1 * 9 - 8;
                           
                           // Pôvodné volanie drawRect tu bolo zrušené, takže čierne pozadie sa nevykreslí.

                           GL11.glEnable(GL11.GL_BLEND);
                           String s = chatline.getChatComponent().getFormattedText();
                           this.mc.fontRenderer.drawStringWithShadow(s, l1, i2 - 8, 16777215 + (k1 << 24));
                           GL11.glDisable(GL11.GL_ALPHA_TEST);
                        }
                     }
                  }
               }

               GL11.glPopMatrix();
            }
         }
      }
   }
}

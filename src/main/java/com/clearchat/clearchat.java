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
import java.util.List;

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
      private static Field chatLinesField;
      private static Field scrollPosField;

      static {
         try {
            chatLinesField = GuiNewChat.class.getDeclaredField("field_146253_i");
            chatLinesField.setAccessible(true);
         } catch (Exception e) {
            try {
               chatLinesField = GuiNewChat.class.getDeclaredField("chatLines");
               chatLinesField.setAccessible(true);
            } catch (Exception ex) {
               ex.printStackTrace();
            }
         }

         try {
            scrollPosField = GuiNewChat.class.getDeclaredField("scrollPos");
            scrollPosField.setAccessible(true);
         } catch (Exception e) {
            try {
               scrollPosField = GuiNewChat.class.getDeclaredField("field_146252_h");
               scrollPosField.setAccessible(true);
            } catch (Exception ignored) {}
         }
      }

      public CustomGuiNewChat(Minecraft mcIn) {
         super(mcIn);
      }

      @Override
      public void drawChat(int par1) {
         Minecraft mcInstance = Minecraft.getMinecraft();
         if (mcInstance.gameSettings.chatVisibility != net.minecraft.entity.player.EntityPlayer.EnumChatVisibility.HIDDEN) {
            int i = this.getLineCount();
            boolean flag = false;
            int j = 0;
            
            List chatLines = getChatLinesSafely();
            int scroll = getScrollPosSafely();
            
            if (chatLines != null) {
               int k = chatLines.size();
               float f = mcInstance.gameSettings.chatOpacity * 0.9F + 0.1F;

               if (k > 0) {
                  if (this.getChatOpen()) {
                     flag = true;
                  }

                  float f1 = this.getChatScale();
                  int l = MathHelper.ceiling_float_int((float)this.getChatWidth() / f1);
                  GL11.glPushMatrix();
                  GL11.glTranslatef(2.0F, 20.0F, 0.0F);
                  GL11.glScalef(f1, f1, 1.0F);

                  for (int i1 = 0; i1 + scroll < chatLines.size() && i1 < i; ++i1) {
                     ChatLine chatline = (ChatLine)chatLines.get(i1 + scroll);

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
                              
                              // Pozadie chatu (drawRect) je vynechané, kreslí sa len text.

                              GL11.glEnable(GL11.GL_BLEND);
                              String s = chatline.getChatComponent().getFormattedText();
                              mcInstance.fontRendererObj.drawStringWithShadow(s, l1, i2 - 8, 16777215 + (k1 << 24));
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

      private List getChatLinesSafely() {
         try {
            if (chatLinesField != null) {
               return (List) chatLinesField.get(this);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
         return null;
      }

      private int getScrollPosSafely() {
         try {
            if (scrollPosField != null) {
               return scrollPosField.getInt(this);
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
         return 0;
      }
   }
}

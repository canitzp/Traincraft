package de.canitzp.traincraft.blocks.distillery;

import com.google.common.collect.Lists;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.inventory.Container;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author canitzp
 */
public class GuiDistillery extends GuiContainer {

    private final TileEntityDistillery tileEntity;
    private final ResourceLocation TEXTURE = new ResourceLocation(Traincraft.MODID, "textures/gui/gui_distillery.png");

    public GuiDistillery(Container container, TileEntityDistillery tileEntity) {
        super(container);
        this.tileEntity = tileEntity;
        this.ySize = 166;
        this.xSize = 176;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if(this.tileEntity.isBurning()) {
            int i1 = this.tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        int i1 = this.tileEntity.getCookProgressScaled(22);
        this.drawTexturedModalRect(guiLeft + 87, guiTop + 36, 184, 15, i1, 41);
        handleGuiTank(tileEntity.tank, guiLeft + 143, guiTop + 58, 22, 54, 176, 166, 22, 54, mouseX, mouseY, "traincraft:textures/gui/gui_distillery.png", new ArrayList<String>());

    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        /*
        if(x <= guiLeft + 164 && y <= guiTop + 58 && x >= guiLeft + 144 && y >= guiTop + 5){
            String amount = tileEntity.tank.getFluidAmount() + "mB / " + tileEntity.tank.getCapacity() + "mB";
            String liquid = StatCollector.translateToLocal(tileEntity.tank.getFluid().getLocalizedName());
            this.drawHoveringText(Lists.newArrayList(amount, liquid), x, y, fontRendererObj);

        }
        */

        ArrayList list = new ArrayList();
        list.add("test");
        handleGuiTank(tileEntity.tank, guiLeft + 143, guiTop + 58, 22, 54, guiLeft + 143, guiTop + 58, 22, 54, x, y, "traincraft:textures/gui/gui_distillery.png", list);

    }


    public static void handleGuiTank(FluidTank tank, int x, int y, int w, int h, int oX, int oY, int oW, int oH, int mX, int mY, String originalTexture, ArrayList<String> tooltip)
    {
        if(tooltip==null)
        {
            if(tank.getFluid()!=null && tank.getFluid().getFluid()!=null)
            {
                int fluidHeight = (int)(h*(tank.getFluid().amount/(float)tank.getCapacity()));
                drawRepeatedFluidIcon(tank.getFluid().getFluid(), x,y+h-fluidHeight, w, fluidHeight);
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(originalTexture));
            }
            int xOff = (w-oW)/2;
            int yOff = (h-oH)/2;
            drawTexturedRect(x+xOff,y+yOff,oW,oH, 256f, oX,oX+oW,oY,oY+oH);
        }
        else
        {
            if(mX>=x&&mX<x+w && mY>=y&&mY<y+h)
            {
                if(tank.getFluid()!=null && tank.getFluid().getFluid()!=null)
                    tooltip.add(tank.getFluid().getLocalizedName());
                else
                    tooltip.add("Empty");
                tooltip.add(tank.getFluidAmount()+"/"+tank.getCapacity()+"mB");
            }
        }
    }

    public static void drawRepeatedFluidIcon(Fluid fluid, float x, float y, float w, float h)
    {
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(TextureMap.locationBlocksTexture.toString()));
        IIcon icon = fluid.getIcon();
        if(icon != null)
        {
            int iW = icon.getIconWidth();
            int iH = icon.getIconHeight();
            if(iW>0 && iH>0)
                drawRepeatedIcon(x,y,w,h, iW, iH, icon.getMinU(),icon.getMaxU(), icon.getMinV(),icon.getMaxV());
        }
    }

    public static void drawRepeatedIcon(float x, float y, float w, float h, int iconWidth, int iconHeight, float uMin, float uMax, float vMin, float vMax)
    {
        int iterMaxW = (int)(w/iconWidth);
        int iterMaxH = (int)(h/iconHeight);
        float leftoverW = w%iconWidth;
        float leftoverH = h%iconHeight;
        float leftoverWf = leftoverW/(float)iconWidth;
        float leftoverHf = leftoverH/(float)iconHeight;
        float iconUDif = uMax-uMin;
        float iconVDif = vMax-vMin;
        for(int ww=0; ww<iterMaxW; ww++)
        {
            for(int hh=0; hh<iterMaxH; hh++)
                drawTexturedRect(x+ww*iconWidth, y+hh*iconHeight, iconWidth,iconHeight, uMin,uMax,vMin,vMax);
            drawTexturedRect(x+ww*iconWidth, y+iterMaxH*iconHeight, iconWidth,leftoverH, uMin,uMax,vMin,(vMin+iconVDif*leftoverHf));
        }
        if(leftoverW>0)
        {
            for(int hh=0; hh<iterMaxH; hh++)
                drawTexturedRect(x+iterMaxW*iconWidth, y+hh*iconHeight, leftoverW,iconHeight, uMin,(uMin+iconUDif*leftoverWf),vMin,vMax);
            drawTexturedRect(x+iterMaxW*iconWidth, y+iterMaxH*iconHeight, leftoverW,leftoverH, uMin,(uMin+iconUDif*leftoverWf),vMin,(vMin+iconVDif*leftoverHf));
        }
    }

    public static void drawTexturedRect(float x, float y, float w, float h, double... uv)
    {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y+h, 0, uv[0], uv[3]);
        tessellator.addVertexWithUV(x+w, y+h, 0, uv[1], uv[3]);
        tessellator.addVertexWithUV(x+w, y, 0, uv[1], uv[2]);
        tessellator.addVertexWithUV(x, y, 0, uv[0], uv[2]);
        tessellator.draw();
    }
}

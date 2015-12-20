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
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

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
        Fluid fluid = FluidRegistry.getFluid(this.tileEntity.fluidID);
        this.drawFluid(fluid, guiLeft + 144, guiTop + 6, 20, 52);
        if(this.tileEntity.isBurning()) {
            int i1 = this.tileEntity.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(guiLeft + 56, guiTop + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }
        if(this.tileEntity.isBurning()){
            int i1 = this.tileEntity.getCookProgressScaled(22);
            this.drawTexturedModalRect(guiLeft + 87, guiTop + 36, 184, 15, 22 - i1, 41);
        }
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        if(x <= guiLeft + 164 && y <= guiTop + 58 && x >= guiLeft + 144 && y >= guiTop + 5){
            String amount = tileEntity.amount + "mB / " + tileEntity.capacity + "mB";
            String liquid = "No Fluid";
            Fluid fluid = FluidRegistry.getFluid(this.tileEntity.fluidID);
            if(fluid != null){
                liquid = StatCollector.translateToLocal(new FluidStack(fluid, tileEntity.amount).getLocalizedName());
            }
            this.drawHoveringText(Lists.newArrayList(amount, liquid), x, y, fontRendererObj);
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.drawTexturedModalRect(this.guiLeft + 144, this.guiTop + 6, 176, 56, 20, 52);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void drawFluid(Fluid fluid, int x, int y, int w, int h){
        if(fluid != null && tileEntity.amount > 0){
            IIcon iIcon = fluid.getIcon();
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(TextureMap.locationBlocksTexture.toString()));
            int i = tileEntity.amount * h / tileEntity.capacity;
            this.drawTexturedModelRectFromIcon(x, y + h - i, iIcon, w, i);
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURE);
        }
    }
}

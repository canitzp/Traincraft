package de.canitzp.traincraft.blocks.distillery;

import com.google.common.collect.Lists;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

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
    }

    @Override
    public void drawScreen(int x, int y, float f) {
        super.drawScreen(x, y, f);

        if(x <= guiLeft + 164 && y <= guiTop + 58 && x >= guiLeft + 144 && y >= guiTop + 5){
            this.drawHoveringText(Collections.singletonList(Integer.toString(this.tileEntity.tank.getFluidAmount())), x, y);
        }

    }
}

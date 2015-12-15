package de.canitzp.traincraft.blocks.distillery;

import de.canitzp.traincraft.Traincraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author canitzp
 */
public class GuiDistillery extends GuiContainer {

    private final TileEntity tileEntity;
    private final ResourceLocation TEXTURE = new ResourceLocation(Traincraft.MODID, "textures/gui/gui_distillery.png");

    public GuiDistillery(Container container, TileEntity tileEntity) {
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

    }
}

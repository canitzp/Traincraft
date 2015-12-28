package de.canitzp.traincraft.blocks.rails;


import de.canitzp.traincraft.tile.IRenderTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

/**
 * @author canitzp
 */
public class RenderTrackStraight extends TileEntitySpecialRenderer{

    protected IModelCustom model;
    protected ResourceLocation texture;

    public RenderTrackStraight(ResourceLocation modelLocation, ResourceLocation textureLocation) {
        model = AdvancedModelLoader.loadModel(modelLocation);
        texture = textureLocation;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        x += ((IRenderTileEntity)tileEntity).getX();
        y += ((IRenderTileEntity)tileEntity).getY();
        z += ((IRenderTileEntity)tileEntity).getZ();
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        switch(tileEntity.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord)){
            case 0:
                GL11.glRotated(180, 0, 1, 0);
                break;
            case 1:
                GL11.glRotated(90, 0, 1, 0);
                break;
            case 2:
                break;
            case 3:
                GL11.glRotated(-90, 0, 1, 0);
                break;
            default:
                break;
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        model.renderAll();
        GL11.glPopMatrix();
    }
}

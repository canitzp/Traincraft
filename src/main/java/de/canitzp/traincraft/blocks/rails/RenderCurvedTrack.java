package de.canitzp.traincraft.blocks.rails;

import de.canitzp.traincraft.tile.IRenderCurvedTileEntity;
import de.canitzp.traincraft.tile.TileEntityCurvedTrackBase;
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
public class RenderCurvedTrack extends TileEntitySpecialRenderer {

    protected IModelCustom model;
    protected ResourceLocation texture;

    public RenderCurvedTrack(ResourceLocation modelLocation, ResourceLocation textureLocation) {
        model = AdvancedModelLoader.loadModel(modelLocation);
        texture = textureLocation;
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
        TileEntityCurvedTrackBase trackBase = (TileEntityCurvedTrackBase) tileEntity;
        int meta = trackBase.getWorldObj().getBlockMetadata(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
        if(((TileEntityCurvedTrackBase)tileEntity).trackType == TileEntityCurvedTrackBase.TrackType.MAIN){
            IRenderCurvedTileEntity rot = ((IRenderCurvedTileEntity)tileEntity);
            GL11.glPushMatrix();
            switch(meta){
                case 0:
                    GL11.glTranslated(x + rot.getX180(), y + rot.getY180(), z + rot.getZ180());
                    GL11.glRotated(180, 0, 1, 0);
                    if(!trackBase.isLeft){
                        GL11.glRotated(90, 0, 1, 0);
                        GL11.glTranslated( 0, 0, 6);
                    }
                    break;
                case 1:
                    GL11.glTranslated(x + rot.getX90(), y + rot.getY90(), z + rot.getZ90());
                    GL11.glRotated(90, 0, 1, 0);
                    break;
                case 2:
                    GL11.glTranslated(x + rot.getX0(), y + rot.getY0(), z + rot.getZ0());
                    break;
                case 3:
                    GL11.glTranslated(x + rot.getX270(), y + rot.getY270(), z + rot.getZ270());
                    GL11.glRotated(-90, 0, 1, 0);
                    break;
                default:
                    GL11.glTranslated(x, y, z);
                    break;
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            model.renderAll();
            GL11.glPopMatrix();
        }
        if(((TileEntityCurvedTrackBase)tileEntity).trackType == TileEntityCurvedTrackBase.TrackType.SECOND){
            IRenderCurvedTileEntity rot = ((IRenderCurvedTileEntity)tileEntity);
            GL11.glPushMatrix();
            switch(meta){
                case 0:
                    GL11.glTranslated(x + rot.getX180() - 2, y + rot.getY180(), z + rot.getZ180() - 2);
                    GL11.glRotated(180, 0, 1, 0);
                    if(!trackBase.isLeft){
                        GL11.glRotated(90, 0, 1, 0);
                        //GL11.glTranslated( 0, 0, 6);
                    }
                    break;
                case 1:
                    GL11.glTranslated(x + rot.getX90() + 2, y + rot.getY90(), z + rot.getZ90() - 2);
                    GL11.glRotated(90, 0, 1, 0);
                    break;
                case 2:
                    GL11.glTranslated(x + rot.getX0() + 2, y + rot.getY0(), z + rot.getZ0() + 2);
                    GL11.glRotated(0, 0, 1, 0);
                    break;
                case 3:
                    GL11.glTranslated(x + rot.getX270() - 2, y + rot.getY270(), z + rot.getZ270() + 2);
                    GL11.glRotated(-90, 0, 1, 0);
                    break;
                default:
                    GL11.glTranslated(x, y, z);
                    break;
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
            model.renderAll();
            GL11.glPopMatrix();
        }
    }

}

package de.canitzp.traincraft.blocks.rails.models;

import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.blocks.rails.Tracks;
import de.canitzp.traincraft.tile.TileEntityRail;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author canitzp
 */
public class ModelLongStraight extends ModelBase {

    public void render(TileEntityRail tileEntityRail, double x, double y, double z) {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)x + 0.5F, (float)y, (float)z + 0.5F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Traincraft.MODID, "models/tracks/trackLongStraight.png"));
        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        Tracks.LONG_STRAIGHT.getOBJ();
        GL11.glPopMatrix();
    }

}

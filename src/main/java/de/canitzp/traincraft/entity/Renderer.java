package de.canitzp.traincraft.entity;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * @author canitzp
 */
public class Renderer extends Render {

    private ModelTram model = new ModelTram();
    private ResourceLocation texture = new ResourceLocation("traincraft:textures/entity/trains/train.png");

    @Override
    public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        model.render(entity, 0, 0, 0, 0, 0, 1);

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity entity) {
        return texture;
    }
}

package de.canitzp.traincraft.entity;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

/**
 * @author canitzp
 */
public class CustomTexturedQuad {

    public PositionTextureVertex[] vertexPositions;
    public int nVertices;
    private boolean invertNormal;
    private int texWidth;
    private int texHeight;

    public CustomTexturedQuad(PositionTextureVertex[] apositiontexturevertex) {
        this.nVertices = 0;
        this.invertNormal = false;
        this.vertexPositions = apositiontexturevertex;
        this.nVertices = apositiontexturevertex.length;
    }

    public CustomTexturedQuad(PositionTextureVertex[] apositiontexturevertex, int i, int j, int k, int l) {
        this(apositiontexturevertex);
        float f = 0.0015625F;
        float f1 = 0.003125F;
        apositiontexturevertex[0] = apositiontexturevertex[0].setTexturePosition((float)k / 64.0F - f, (float)j / 32.0F + f1);
        apositiontexturevertex[1] = apositiontexturevertex[1].setTexturePosition((float)i / 64.0F + f, (float)j / 32.0F + f1);
        apositiontexturevertex[2] = apositiontexturevertex[2].setTexturePosition((float)i / 64.0F + f, (float)l / 32.0F - f1);
        apositiontexturevertex[3] = apositiontexturevertex[3].setTexturePosition((float)k / 64.0F - f, (float)l / 32.0F - f1);
    }

    public CustomTexturedQuad(PositionTextureVertex[] apositiontexturevertex, int i, int j, int k, int l, int texWidth, int texHeight) {
        this(apositiontexturevertex);
        float f = 0.0015625F;
        float f1 = 0.003125F;
        float w = (float)texWidth;
        float h = (float)texHeight;
        apositiontexturevertex[0] = apositiontexturevertex[0].setTexturePosition((float)k / w - f, (float)j / h + f1);
        apositiontexturevertex[1] = apositiontexturevertex[1].setTexturePosition((float)i / w + f, (float)j / h + f1);
        apositiontexturevertex[2] = apositiontexturevertex[2].setTexturePosition((float)i / w + f, (float)l / h - f1);
        apositiontexturevertex[3] = apositiontexturevertex[3].setTexturePosition((float)k / w - f, (float)l / h - f1);
    }

    public void flipFace() {
        PositionTextureVertex[] apositiontexturevertex = new PositionTextureVertex[this.vertexPositions.length];

        for(int i = 0; i < this.vertexPositions.length; ++i) {
            apositiontexturevertex[i] = this.vertexPositions[this.vertexPositions.length - i - 1];
        }

        this.vertexPositions = apositiontexturevertex;
    }

    public void draw(Tessellator tessellator, float f) {
        Vec3 vec3d = this.vertexPositions[1].vector3D.crossProduct(this.vertexPositions[0].vector3D);
        Vec3 vec3d1 = this.vertexPositions[1].vector3D.crossProduct(this.vertexPositions[2].vector3D);
        Vec3 vec3d2 = vec3d1.crossProduct(vec3d).normalize();
        tessellator.disableColor();
        if(this.invertNormal) {
            tessellator.addTranslation(-((float)vec3d2.xCoord), -((float)vec3d2.yCoord), -((float)vec3d2.zCoord));
        } else {
            tessellator.addTranslation((float)vec3d2.xCoord, (float)vec3d2.yCoord, (float)vec3d2.zCoord);
        }

        for(int i = 0; i < 4; ++i) {
            PositionTextureVertex positiontexturevertex = this.vertexPositions[i];
            tessellator.addVertexWithUV((double)((float)positiontexturevertex.vector3D.xCoord * f), (double)((float)positiontexturevertex.vector3D.yCoord * f), (double)((float)positiontexturevertex.vector3D.zCoord * f), (double)positiontexturevertex.texturePositionX, (double)positiontexturevertex.texturePositionY);
        }

        tessellator.draw();
    }
}

package de.canitzp.traincraft.entity;

import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

/**
 * @author canitzp
 */
public class CustomModelRenderer {
    private PositionTextureVertex[] corners;
    private CustomTexturedQuad[] faces;
    private int textureOffsetX;
    private int textureOffsetY;
    public float offsetX;
    public float offsetY;
    public float offsetZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    private boolean compiled = false;
    private int displayList = 0;
    public boolean mirror = false;
    public boolean showModel = true;
    public boolean field_1402_i = false;
    private int texWidth;
    private int texHeight;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;

    public CustomModelRenderer(int i, int j, int w, int h) {
        this.textureOffsetX = i;
        this.textureOffsetY = j;
        this.texWidth = w;
        this.texHeight = h;
    }

    public void addBox(float f, float f1, float f2, int i, int j, int k) {
        this.addBox(f, f1, f2, i, j, k, 0.0F);
    }

    public void addBox(float f, float f1, float f2, int i, int j, int k, float f3) {
        this.corners = new PositionTextureVertex[8];
        this.faces = new CustomTexturedQuad[6];
        float f4 = f + (float)i;
        float f5 = f1 + (float)j;
        float f6 = f2 + (float)k;
        f -= f3;
        f1 -= f3;
        f2 -= f3;
        f4 += f3;
        f5 += f3;
        f6 += f3;
        if(this.mirror) {
            float positiontexturevertex = f4;
            f4 = f;
            f = positiontexturevertex;
        }

        PositionTextureVertex var20 = new PositionTextureVertex(f, f1, f2, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f4, f1, f2, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(f4, f5, f2, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(f, f5, f2, 8.0F, 0.0F);
        PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, f1, f6, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f4, f1, f6, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(f4, f5, f6, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(f, f5, f6, 8.0F, 0.0F);
        this.corners[0] = var20;
        this.corners[1] = positiontexturevertex1;
        this.corners[2] = positiontexturevertex2;
        this.corners[3] = positiontexturevertex3;
        this.corners[4] = positiontexturevertex4;
        this.corners[5] = positiontexturevertex5;
        this.corners[6] = positiontexturevertex6;
        this.corners[7] = positiontexturevertex7;
        this.faces[0] = new CustomTexturedQuad(new PositionTextureVertex[]{positiontexturevertex5, positiontexturevertex1, positiontexturevertex2, positiontexturevertex6}, this.textureOffsetX + k + i, this.textureOffsetY + k, this.textureOffsetX + k + i + k, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
        this.faces[1] = new CustomTexturedQuad(new PositionTextureVertex[]{var20, positiontexturevertex4, positiontexturevertex7, positiontexturevertex3}, this.textureOffsetX + 0, this.textureOffsetY + k, this.textureOffsetX + k, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
        this.faces[2] = new CustomTexturedQuad(new PositionTextureVertex[]{positiontexturevertex5, positiontexturevertex4, var20, positiontexturevertex1}, this.textureOffsetX + k, this.textureOffsetY + 0, this.textureOffsetX + k + i, this.textureOffsetY + k, this.texWidth, this.texHeight);
        this.faces[3] = new CustomTexturedQuad(new PositionTextureVertex[]{positiontexturevertex2, positiontexturevertex3, positiontexturevertex7, positiontexturevertex6}, this.textureOffsetX + k + i, this.textureOffsetY + 0, this.textureOffsetX + k + i + i, this.textureOffsetY + k, this.texWidth, this.texHeight);
        this.faces[4] = new CustomTexturedQuad(new PositionTextureVertex[]{positiontexturevertex1, var20, positiontexturevertex3, positiontexturevertex2}, this.textureOffsetX + k, this.textureOffsetY + k, this.textureOffsetX + k + i, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
        this.faces[5] = new CustomTexturedQuad(new PositionTextureVertex[]{positiontexturevertex4, positiontexturevertex5, positiontexturevertex6, positiontexturevertex7}, this.textureOffsetX + k + i + k, this.textureOffsetY + k, this.textureOffsetX + k + i + k + i, this.textureOffsetY + k + j, this.texWidth, this.texHeight);
        if(this.mirror) {
            for(int l = 0; l < this.faces.length; ++l) {
                this.faces[l].flipFace();
            }
        }

    }

    public void setPosition(float f, float f1, float f2) {
        this.offsetX = f;
        this.offsetY = f1;
        this.offsetZ = f2;
    }

    public void render(float f) {
        if(!this.field_1402_i) {
            if(this.showModel) {
                if(!this.compiled) {
                    this.compileDisplayList(f);
                }

                if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if(this.offsetX == 0.0F && this.offsetY == 0.0F && this.offsetZ == 0.0F) {
                        GL11.glCallList(this.displayList);
                    } else {
                        GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
                        GL11.glCallList(this.displayList);
                        GL11.glTranslatef(-this.offsetX * f, -this.offsetY * f, -this.offsetZ * f);
                    }
                } else {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
                    if(this.rotateAngleZ != 0.0F) {
                        GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
                    }

                    if(this.rotateAngleY != 0.0F) {
                        GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
                    }

                    if(this.rotateAngleX != 0.0F) {
                        GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }

                    GL11.glCallList(this.displayList);
                    GL11.glPopMatrix();
                }

            }
        }
    }

    public void postRender(float f) {
        if(!this.field_1402_i) {
            if(this.showModel) {
                if(!this.compiled) {
                    this.compileDisplayList(f);
                }

                if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
                    if(this.offsetX != 0.0F || this.offsetY != 0.0F || this.offsetZ != 0.0F) {
                        GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
                    }
                } else {
                    GL11.glTranslatef(this.offsetX * f, this.offsetY * f, this.offsetZ * f);
                    if(this.rotateAngleZ != 0.0F) {
                        GL11.glRotatef(this.rotateAngleZ * 57.29578F, 0.0F, 0.0F, 1.0F);
                    }

                    if(this.rotateAngleY != 0.0F) {
                        GL11.glRotatef(this.rotateAngleY * 57.29578F, 0.0F, 1.0F, 0.0F);
                    }

                    if(this.rotateAngleX != 0.0F) {
                        GL11.glRotatef(this.rotateAngleX * 57.29578F, 1.0F, 0.0F, 0.0F);
                    }
                }

            }
        }
    }

    private void compileDisplayList(float f) {
        this.displayList = GLAllocation.generateDisplayLists(1);
        GL11.glNewList(this.displayList, 4864);
        Tessellator tessellator = Tessellator.instance;

        for(int i = 0; i < this.faces.length; ++i) {
            this.faces[i].draw(tessellator, f);
        }

        GL11.glEndList();
        this.compiled = true;
    }

    public void setRotationPoint(float par1, float par2, float par3) {
        this.rotationPointX = par1;
        this.rotationPointY = par2;
        this.rotationPointZ = par3;
    }
}
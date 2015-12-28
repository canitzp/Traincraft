package de.canitzp.traincraft.tile;

import net.minecraft.tileentity.TileEntity;

/**
 * @author canitzp
 */
public class TileEntityTrackBase extends TileEntity implements IRenderTileEntity {

    private double x, y, z;

    public TileEntityTrackBase(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public double getZ() {
        return this.z;
    }

}

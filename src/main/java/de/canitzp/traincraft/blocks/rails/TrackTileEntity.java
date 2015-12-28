package de.canitzp.traincraft.blocks.rails;

import de.canitzp.traincraft.tile.tracks.TileEntityShortStraight;
import de.canitzp.traincraft.tile.tracks.TileEntityMediumCurve;
import net.minecraft.tileentity.TileEntity;

/**
 * @author canitzp
 */
public enum TrackTileEntity {

    MEDIUM_CURVE(TileEntityMediumCurve.class, "mediumCurve", Tracks.MEDIUM_CURVE),
    SHORT_STRAIGHT(TileEntityShortStraight.class, "shortStraight", Tracks.SHORT_STRAIGHT);

    public String name;
    public Class<? extends TileEntity> tileEntityClass;
    public Tracks tracks;
    TrackTileEntity(Class<? extends TileEntity> tileEntityClass, String name, Tracks tracks) {
        this.tileEntityClass = tileEntityClass;
        this.name = name;
        this.tracks = tracks;
    }

}

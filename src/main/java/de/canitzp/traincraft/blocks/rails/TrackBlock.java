package de.canitzp.traincraft.blocks.rails;

import de.canitzp.traincraft.proxy.ClientProxy;
import de.canitzp.traincraft.tile.TileEntityCurvedTrackBase;

/**
 * @author canitzp
 */
public enum TrackBlock {

    MEDIUM_CURVE(new BlockCurvedTrack(Tracks.MEDIUM_CURVE, TrackTileEntity.MEDIUM_CURVE.tileEntityClass, TileEntityCurvedTrackBase.TrackType.MAIN), ClientProxy.mediumCurve),
    SHORT_STRAIGHT(new BlockRail(Tracks.SHORT_STRAIGHT, TrackTileEntity.SHORT_STRAIGHT.tileEntityClass), ClientProxy.shortStraight);

    public int renderId;
    public BlockRail track;
    TrackBlock(BlockRail track, int renderId){
        this.track = track;
        this.renderId = renderId;
    }

    public static void preInitBlocks(){
        for(TrackBlock track : TrackBlock.values()){
            track.track.register(track.renderId);
        }
    }


}

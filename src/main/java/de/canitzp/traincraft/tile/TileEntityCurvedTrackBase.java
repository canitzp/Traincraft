package de.canitzp.traincraft.tile;

import de.canitzp.traincraft.blocks.rails.BlockCurvedTrack;
import net.minecraft.tileentity.TileEntity;

/**
 * @author canitzp
 */
public class TileEntityCurvedTrackBase extends TileEntity implements IRenderCurvedTileEntity {

    public TrackType trackType;
    public boolean isLeft;
    public BlockCurvedTrack mainBlock, ghostBlock1, ghostBlock2, ghostBlock3, secondBlock;

    private TranslationPack rot0, rot90, rot180, rot270;
    public TileEntityCurvedTrackBase(TrackType trackType, TranslationPack rot0, TranslationPack rot90, TranslationPack rot180, TranslationPack rot270) {
        this.rot0 = rot0;
        this.rot90 = rot90;
        this.rot180 = rot180;
        this.rot270 = rot270;
        this.trackType = trackType;
    }

    @Override
    public double getX0() {
        return rot0.getX();
    }

    @Override
    public double getY0() {
        return rot0.getY();
    }

    @Override
    public double getZ0() {
        return rot0.getZ();
    }

    @Override
    public double getX90() {
        return rot90.getX();
    }

    @Override
    public double getY90() {
        return rot90.getY();
    }

    @Override
    public double getZ90() {
        return rot90.getZ();
    }

    @Override
    public double getX180() {
        return rot180.getX();
    }

    @Override
    public double getY180() {
        return rot180.getY();
    }

    @Override
    public double getZ180() {
        return rot180.getZ();
    }

    @Override
    public double getX270() {
        return rot270.getX();
    }

    @Override
    public double getY270() {
        return rot270.getY();
    }

    @Override
    public double getZ270() {
        return rot270.getZ();
    }


    public enum TrackType{
        MAIN("Main"),
        SECOND("Second"),
        GHOST("Ghost");
        public String name;
        TrackType(String name){
            this.name = name;
        }
    }

}

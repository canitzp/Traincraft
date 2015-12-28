package de.canitzp.traincraft.tile.tracks;

import de.canitzp.traincraft.tile.TileEntityCurvedTrackBase;
import de.canitzp.traincraft.tile.TranslationPack;

/**
 * @author canitzp
 */
public class TileEntityMediumCurve extends TileEntityCurvedTrackBase {

    public TileEntityMediumCurve(TrackType trackType) {
        super(trackType, new TranslationPack(-2.5, 0, 1.5), new TranslationPack(1.5, 0, 3.5), new TranslationPack(3.5, 0, -0.5), new TranslationPack(-0.5, 0, -2.5));
    }

}

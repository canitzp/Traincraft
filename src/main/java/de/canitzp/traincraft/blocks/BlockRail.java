package de.canitzp.traincraft.blocks;

import de.canitzp.traincraft.blocks.rails.Tracks;
import de.canitzp.traincraft.tile.TileEntityRail;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockRail extends BlockRotatableRail {

    public BlockRail(Tracks.Length length, Tracks.Curve curve) {
        super(Material.iron, TileEntityRail.class, "rail_" + length.name + "_" + curve.name);
        this.setCreativeTab(null);
    }

    public int getRenderType(){
        return -1;
    }

    public boolean isFullCube() {
        return false;
    }

}

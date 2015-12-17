package de.canitzp.traincraft.blocks.rails;

import de.canitzp.traincraft.blocks.rails.models.ModelLongStraight;
import de.canitzp.traincraft.tile.TileEntityRail;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author canitzp
 */
public class RailRender extends TileEntitySpecialRenderer{

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
        new ModelLongStraight().render((TileEntityRail) te, x, y, z);
    }

}

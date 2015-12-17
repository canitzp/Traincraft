package de.canitzp.traincraft;

import de.canitzp.traincraft.blocks.distillery.TileEntityDistillery;
import de.canitzp.traincraft.tile.TileEntityRail;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author canitzp
 */
public class TileEntityRegistry {

    public static void preInit(){
        GameRegistry.registerTileEntity(TileEntityDistillery.class, "distillery");
        GameRegistry.registerTileEntity(TileEntityRail.class, "rail");
    }

}

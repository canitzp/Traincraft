package de.canitzp.traincraft;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.blocks.distillery.TileEntityDistillery;

/**
 * @author canitzp
 */
public class TileEntityRegistry {

    public static void preInit(){
        GameRegistry.registerTileEntity(TileEntityDistillery.class, "distillery");
        //GameRegistry.registerTileEntity(TileEntityRail.class, "rail");
    }

}

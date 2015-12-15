package de.canitzp.traincraft;

import de.canitzp.traincraft.blocks.distillery.TileEntityDistillery;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author canitzp
 */
public class TileEntityRegistry {

    public static void preInit(){
        GameRegistry.registerTileEntity(TileEntityDistillery.class, "distillery");
    }

}

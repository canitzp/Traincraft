package de.canitzp.traincraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.blocks.distillery.TileEntityDistillery;
import de.canitzp.traincraft.blocks.rails.TrackTileEntity;

/**
 * @author canitzp
 */
public abstract class CommonProxy {

    public void registerRenderIds(){

    }

    public void registerRenderer(){

    }

    public void registerTileEntitys(){
        GameRegistry.registerTileEntity(TileEntityDistillery.class, "distillery");

        for(TrackTileEntity trackTileEntity : TrackTileEntity.values()){
            GameRegistry.registerTileEntity(trackTileEntity.tileEntityClass, trackTileEntity.name);
        }

    }
}

package de.canitzp.traincraft.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import de.canitzp.traincraft.blocks.rails.RenderTrackStraight;
import de.canitzp.traincraft.blocks.rails.RenderCurvedTrack;
import de.canitzp.traincraft.blocks.rails.TrackTileEntity;
import de.canitzp.traincraft.entity.EntityTrain;
import de.canitzp.traincraft.entity.Renderer;

/**
 * @author canitzp
 */
public class ClientProxy extends CommonProxy {

    public static int shortStraight, mediumCurve;

    @Override
    public void registerRenderIds(){
        shortStraight = RenderingRegistry.getNextAvailableRenderId();
        mediumCurve = RenderingRegistry.getNextAvailableRenderId();
    }

    @Override
    public void registerRenderer(){
        for(TrackTileEntity trackTileEntity : TrackTileEntity.values()){
            if(trackTileEntity.tracks.curve.name.equals("straight")){
                ClientRegistry.bindTileEntitySpecialRenderer(trackTileEntity.tileEntityClass, new RenderTrackStraight(trackTileEntity.tracks.getModel(), trackTileEntity.tracks.getTexture()));
            } else if(trackTileEntity.tracks.curve.name.equals("curve")){
                ClientRegistry.bindTileEntitySpecialRenderer(trackTileEntity.tileEntityClass, new RenderCurvedTrack(trackTileEntity.tracks.getModel(), trackTileEntity.tracks.getTexture()));
            }

        }
        RenderingRegistry.registerEntityRenderingHandler(EntityTrain.class, new Renderer());
    }


}

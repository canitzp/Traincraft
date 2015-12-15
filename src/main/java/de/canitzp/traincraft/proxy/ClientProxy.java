package de.canitzp.traincraft.proxy;

import de.canitzp.traincraft.BlockRegistry;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;

/**
 * @author canitzp
 * @since 13/12/2015
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers(){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(BlockRegistry.distillery), 0, new ModelResourceLocation(Traincraft.MODID + ":distillery", "inventory"));
    }

}

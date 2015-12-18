package de.canitzp.traincraft.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * @author canitzp
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers(){
        //registerBlock(BlockRegistry.oilSand, "oilSand");
        //registerBlock(BlockRegistry.distillery, "distillery");

        //registerItem(ItemRegistry.fuelCanister, "fuelCanister");
        //registerItem(ItemRegistry.fuelCanisterEmpty, "fuelCanisterEmpty");
        //registerItem(ItemRegistry.plastic, "plastic");


        //OBJLoader.instance.addDomain(Traincraft.MODID);
        //ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRail.class, new RailRender());
    }

    private void registerBlock(Block block, String blockName){
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Traincraft.MODID + ":" + blockName, "inventory"));
    }
    private void registerItem(Item item, String blockName){
        //Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Traincraft.MODID + ":" + blockName, "inventory"));
    }



}

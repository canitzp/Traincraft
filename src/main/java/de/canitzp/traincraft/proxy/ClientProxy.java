package de.canitzp.traincraft.proxy;

import de.canitzp.traincraft.BlockRegistry;
import de.canitzp.traincraft.ItemRegistry;
import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.blocks.rails.RailRender;
import de.canitzp.traincraft.tile.TileEntityRail;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * @author canitzp
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void registerRenderers(){
        registerBlock(BlockRegistry.oilSand, "oilSand");
        registerBlock(BlockRegistry.distillery, "distillery");

        registerItem(ItemRegistry.fuelCanister, "fuelCanister");
        registerItem(ItemRegistry.fuelCanisterEmpty, "fuelCanisterEmpty");
        registerItem(ItemRegistry.plastic, "plastic");


        OBJLoader.instance.addDomain(Traincraft.MODID);
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityRail.class, new RailRender());
    }

    private void registerBlock(Block block, String blockName){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(block), 0, new ModelResourceLocation(Traincraft.MODID + ":" + blockName, "inventory"));
    }
    private void registerItem(Item item, String blockName){
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0, new ModelResourceLocation(Traincraft.MODID + ":" + blockName, "inventory"));
    }



}

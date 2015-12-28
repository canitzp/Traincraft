package de.canitzp.traincraft.blocks;

import de.canitzp.traincraft.blocks.distillery.BlockDistillery;
import de.canitzp.traincraft.blocks.rails.TrackBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockRegistry {

    public static Block oilSand, distillery;

    public static void preInit(){
        TrackBlock.preInitBlocks();
        oilSand = new BlockOilSand(Material.sand, "oilSand");
        distillery = new BlockDistillery();
    }

}

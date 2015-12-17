package de.canitzp.traincraft;

import de.canitzp.traincraft.blocks.BlockOilSand;
import de.canitzp.traincraft.blocks.distillery.BlockDistillery;
import de.canitzp.traincraft.blocks.rails.Tracks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockRegistry {

    public static Block oilSand, distillery;

    public static void preInit(){
        Tracks.preInitBlocks();
        oilSand = new BlockOilSand(Material.sand, "oilSand");
        distillery = new BlockDistillery();
        System.out.println(Tracks.LONG_STRAIGHT.rail.toString());
    }

}

package de.canitzp.traincraft;

import de.canitzp.traincraft.blocks.distillery.BlockDistillery;
import de.canitzp.traincraft.blocks.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockRegistry {

    public static Block oilSand, distillery;

    public static void preInit(){
        oilSand = new BlockOre(Material.sand, "oilSand");
        distillery = new BlockDistillery();
    }

}

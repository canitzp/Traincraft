package de.canitzp.traincraft.blocks;

import de.canitzp.traincraft.Traincraft;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockOilSand extends BlockOre{

    public BlockOilSand(Material material, String name) {
        super(material, name);
        this.setBlockTextureName(Traincraft.MODID + ":" + name);
    }

}

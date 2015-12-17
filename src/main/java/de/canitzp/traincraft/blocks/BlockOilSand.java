package de.canitzp.traincraft.blocks;

import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockOilSand extends BlockOre implements IBurnTime{

    public BlockOilSand(Material material, String name) {
        super(material, name);
    }

    @Override
    public int getBurnTime() {
        return 500;
    }
}

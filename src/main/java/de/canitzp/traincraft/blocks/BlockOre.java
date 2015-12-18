package de.canitzp.traincraft.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;

/**
 * @author canitzp
 */
public class BlockOre extends BlockFalling {

    public BlockOre(Material material, String name) {
        super(material);
        setBlockName(Traincraft.MODID + "." + name);
        setCreativeTab(Traincraft.traincraftTab);
        GameRegistry.registerBlock(this, name);
    }

}

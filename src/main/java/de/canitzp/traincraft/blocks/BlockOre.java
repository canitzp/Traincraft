package de.canitzp.traincraft.blocks;

import de.canitzp.traincraft.Traincraft;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author canitzp
 * @since 13/12/2015
 */
public class BlockOre extends BlockFalling{

    public BlockOre(Material material, String name) {
        super(material);
        setUnlocalizedName(Traincraft.MODID + "." + name);
        setCreativeTab(Traincraft.traincraftTab);
        GameRegistry.registerBlock(this, name);
    }

}

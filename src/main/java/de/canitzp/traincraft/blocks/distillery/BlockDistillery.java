package de.canitzp.traincraft.blocks.distillery;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.GuiHandler;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class BlockDistillery extends BlockContainer {

    public BlockDistillery() {
        super(Material.iron);
        this.setBlockName(Traincraft.MODID + "." + "distillery");
        this.setBlockTextureName(Traincraft.MODID + ":" + "distillery");
        this.setCreativeTab(Traincraft.traincraftTab);
        GameRegistry.registerBlock(this, "distillery");
    }

    public int getRenderType()
    {
        return 0;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote){
            player.openGui(Traincraft.traincraft, GuiHandler.ID_DISTILLERY, world, x, y, z);
            return true;
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityDistillery();
    }
}

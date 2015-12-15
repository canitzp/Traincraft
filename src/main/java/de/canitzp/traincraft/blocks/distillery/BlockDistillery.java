package de.canitzp.traincraft.blocks.distillery;

import de.canitzp.traincraft.GuiHandler;
import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.blocks.BlockRotatable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class BlockDistillery extends BlockRotatable {

    public BlockDistillery() {
        super(Material.iron, TileEntityDistillery.class, "distillery");
    }

    public int getRenderType()
    {
        return 3;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!world.isRemote){
            player.openGui(Traincraft.traincraft, GuiHandler.ID_DISTILLERY, world, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }
}

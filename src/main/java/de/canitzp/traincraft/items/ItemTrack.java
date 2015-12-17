package de.canitzp.traincraft.items;

import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.blocks.BlockRotatableRail;
import de.canitzp.traincraft.blocks.rails.Tracks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.BlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author canitzp
 */
public class ItemTrack extends Item{

    private final Tracks track;

    public ItemTrack(Tracks track) {
        setUnlocalizedName(Traincraft.MODID + ".trackPlacer_" + track.length.name + "_" + track.curve.name);
        this.setCreativeTab(Traincraft.traincraftTab);
        this.track = track;
    }

    public void register(){
        GameRegistry.registerItem(this, "trackPlacer_" + track.length.name + "_" + track.curve.name);
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            if(this.isStraightFree(pos, worldIn, this.track.length.length)){
                this.setStraightBlock(pos, worldIn, this.track.length.length, this.track.rail);
            }
        }
        return false;
    }

    public boolean isStraightFree(BlockPos pos, World world, int length){
        boolean[] booleen = new boolean[length];
        for(int i = 0; i != length; ++i){
            BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY() + 1, pos.getZ());
            BlockPos pos2 = new BlockPos(pos.getX() + i, pos.getY(), pos.getZ());
            booleen[i] = world.isAirBlock(pos1) && !world.isAirBlock(pos2) && !(world.getBlockState(pos2).getBlock() instanceof BlockLiquid || world.getBlockState(pos2).getBlock() instanceof IFluidBlock);
        }
        for(boolean b : booleen){
            if(!b) return false;
        }
        return true;
    }

    public void setStraightBlock(BlockPos pos, World world, int length, Block block){
        for(int i = 0; i != length; ++i){
            BlockPos pos1 = new BlockPos(pos.getX() + i, pos.getY() + 1, pos.getZ());
            world.markBlockForUpdate(pos1);
        }
    }

}

package de.canitzp.traincraft.blocks.rails;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.tile.TileEntityCurvedTrackBase;
import de.canitzp.traincraft.tile.TileEntityTrackBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;

/**
 * @author canitzp
 */
public class BlockCurvedTrack extends BlockRail {

    private static int[][] medium0 = {{2, 0, 2}, {1, 0, 2}, {1, 0, 1}, {0, 0, 1}, {0, 0, 0}};
    private static int[][] medium1 = {{-2, 0, 2}, {-2, 0, 1}, {-1, 0, 1}, {-1, 0, 0}, {0, 0, 0}};
    private static int[][] medium2 = {{-2, 0, -2}, {-1, 0, -2}, {-1, 0, -1}, {0, 0, -1}, {0, 0, 0}};
    private static int[][] medium3 = {{2, 0, -2}, {1, 0, -1}, {2, 0, -1}, {1, 0, 0}, {0, 0, 0}};

    public TileEntityCurvedTrackBase.TrackType trackType;
    public BlockCurvedTrack(Tracks tracks, Class<? extends TileEntity> tileEntity, TileEntityCurvedTrackBase.TrackType trackType) {
        super(tracks, tileEntity);
        this.trackType = trackType;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z) {
        if(this.trackType == TileEntityCurvedTrackBase.TrackType.MAIN){

            switch (MathHelper.floor_double((double)(Minecraft.getMinecraft().thePlayer.rotationYaw*4.0F/360.0F)+0.5D) & 3){
                case 0:{
                    return CurvePlacingMethods.hasBlocksInPlacesGiven(world, x, y, z, medium0);
                }
                case 1:{
                    return CurvePlacingMethods.hasBlocksInPlacesGiven(world, x, y, z, medium1);
                }
                case 2:{
                    return CurvePlacingMethods.hasBlocksInPlacesGiven(world, x, y, z, medium2);
                }
                case 3:{
                    return CurvePlacingMethods.hasBlocksInPlacesGiven(world, x, y, z, medium3);
                }
            }
        }
        return super.canPlaceBlockAt(world, x, y, z);
    }

    @Override

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        if(this.trackType == TileEntityCurvedTrackBase.TrackType.MAIN) {
            TileEntityCurvedTrackBase tileEntity = (TileEntityCurvedTrackBase) world.getTileEntity(x, y, z);
            tileEntity.isLeft = !tileEntity.isLeft;
            return true;
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, livingBase, stack); //Has to be called before this Method!
        if(this.trackType == TileEntityCurvedTrackBase.TrackType.MAIN){
            if (!world.isRemote) {
                int rotation = MathHelper.floor_double((double)(livingBase.rotationYaw*4.0F/360.0F)+0.5D) & 3;
                CurvePlacingMethods.setBlocksMediumCurved(world, x, y, z, rotation);
            }
        }
    }

    @Override
    public void register(int renderId){
        this.renderId = renderId;
        GameRegistry.registerBlock(this, this.tracks.name + this.trackType.name);
        GameRegistry.registerBlock(new BlockCurvedTrack(this.tracks, this.tileEntity, TileEntityCurvedTrackBase.TrackType.SECOND).setCreativeTab(null), this.tracks.name + TileEntityCurvedTrackBase.TrackType.SECOND.name);
        GameRegistry.registerBlock(new BlockCurvedTrack(this.tracks, this.tileEntity, TileEntityCurvedTrackBase.TrackType.GHOST).setCreativeTab(null), this.tracks.name + TileEntityCurvedTrackBase.TrackType.GHOST.name);
    }

    public String getSearchName(TileEntityCurvedTrackBase.TrackType trackType){
        return this.tracks.name + trackType.name;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        try {
            return this.tileEntity.getConstructor(TileEntityCurvedTrackBase.TrackType.class).newInstance(this.trackType);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

}

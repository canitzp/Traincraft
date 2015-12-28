package de.canitzp.traincraft.blocks.rails;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.tile.tracks.TileEntityShortStraight;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class BlockRail extends BlockContainer implements ITileEntityProvider {

    public Tracks tracks;
    public int renderId = -1, rotation;
    public Class<? extends TileEntity> tileEntity;

    public BlockRail(Tracks tracks, Class<? extends TileEntity> tileEntity) {
        super(Material.iron);
        this.tracks = tracks;
        this.tileEntity = tileEntity;
        this.setCreativeTab(Traincraft.traincraftTab);
        this.setBlockTextureName(Traincraft.MODID + ":tracks/" + tracks.name);
        this.setBlockName(Traincraft.MODID + ".block" + tracks.name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F/16.0F, 1.0F);
    }

    public void register(int renderId){
        this.renderId = renderId;
        GameRegistry.registerBlock(this, this.tracks.name);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;
        if(rotation == 0){
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        } else if(rotation == 1){
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        } else if(rotation == 2){
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        } else if(rotation == 3){
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        this.rotation = rotation;
        System.out.println(rotation);
    }

    public int getRenderType(){
        return this.renderId;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        try {
            return this.tileEntity.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return new TileEntityShortStraight();
    }
}

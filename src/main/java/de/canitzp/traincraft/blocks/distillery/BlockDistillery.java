package de.canitzp.traincraft.blocks.distillery;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.canitzp.traincraft.GuiHandler;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class BlockDistillery extends BlockContainer {

    @SideOnly(Side.CLIENT)
    private IIcon topIcon, frontIcon, bottomIcon, frontIconOn;

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
            if(player.isSneaking()){
                System.out.println(world.getBlockMetadata(x, y, z));
            } else player.openGui(Traincraft.traincraft, GuiHandler.ID_DISTILLERY, world, x, y, z);
            return true;

        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack){
        int rotation = MathHelper.floor_double((double)(player.rotationYaw*4.0F/360.0F)+0.5D) & 3;

        if(rotation == 0){
            world.setBlockMetadataWithNotify(x, y, z, 0, 2);
        }
        if(rotation == 1){
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }
        if(rotation == 2){
            world.setBlockMetadataWithNotify(x, y, z, 1, 2);
        }
        if(rotation == 3){
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side){
        int meta = world.getBlockMetadata(x, y, z);
        if(side == 1){
            return this.topIcon;
        } else if(side == 0){
            return this.bottomIcon;
        }
        if(side == meta+2 && meta <= 3){
            return this.frontIcon;
        }
        else if(side == meta-2 && meta > 3){
            return this.frontIconOn;
        }
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta){
        if(side == 0 || side == 1){
            return this.topIcon;
        }
        if(side == 3){
            return this.frontIcon;
        }
        return this.blockIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconReg){
        this.bottomIcon = iconReg.registerIcon(Traincraft.MODID + ":distilleryBottom");
        this.frontIcon = iconReg.registerIcon(Traincraft.MODID + ":distilleryFront");
        this.frontIconOn = iconReg.registerIcon(Traincraft.MODID + ":distilleryFrontOn");
        this.topIcon = iconReg.registerIcon(Traincraft.MODID + ":distilleryTop");
        this.blockIcon = iconReg.registerIcon(Traincraft.MODID + ":distillerySide");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileEntityDistillery();
    }
}

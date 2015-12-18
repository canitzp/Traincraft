package de.canitzp.traincraft;

import cpw.mods.fml.common.network.IGuiHandler;
import de.canitzp.traincraft.blocks.distillery.ContainerDistillery;
import de.canitzp.traincraft.blocks.distillery.GuiDistillery;
import de.canitzp.traincraft.blocks.distillery.TileEntityDistillery;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class GuiHandler implements IGuiHandler {

    public static final int ID_DISTILLERY = 0;

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        switch (ID){
            case ID_DISTILLERY:{
                return new ContainerDistillery((TileEntityDistillery) tileEntity, player);
            }
            default:{
                return null;
            }
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity entity = world.getTileEntity(x, y, z);
        switch (ID){
            case ID_DISTILLERY:{
                return new GuiDistillery(new ContainerDistillery((TileEntityDistillery) entity, player), (TileEntityDistillery) entity);
            }
            default:{
                return null;
            }
        }
    }


}

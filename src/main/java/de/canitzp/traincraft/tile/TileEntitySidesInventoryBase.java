package de.canitzp.traincraft.tile;

import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

/**
 * @author canitzp
 */
public class TileEntitySidesInventoryBase extends TileEntityInventoryBase implements ISidedInventory{

    public TileEntitySidesInventoryBase(int slot, String name) {
        super(slot, name);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int slotId, ItemStack stack, int side) {
        return false;
    }

    @Override
    public boolean canExtractItem(int slotId, ItemStack stack, int side) {
        return false;
    }
}

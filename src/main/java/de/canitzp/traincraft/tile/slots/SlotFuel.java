package de.canitzp.traincraft.tile.slots;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 * @author canitzp
 */
public class SlotFuel extends Slot {

    public SlotFuel(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return TileEntityFurnace.getItemBurnTime(stack) > 0;
    }

}

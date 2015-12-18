package de.canitzp.traincraft.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 * @author canitzp
 */
public class TileEntityInventoryBase extends TileEntity implements IInventory {

    private final String name;
    private ItemStack[] slots;

    public TileEntityInventoryBase(int slot, String name){
        this.slots = new ItemStack[slot];
        this.name = name;
    }

    @Override
    public int getSizeInventory() {
        return 64;
    }

    @Override
    public ItemStack getStackInSlot(int index) {
        if(index < this.getSizeInventory()){
            return slots[index];
        }
        return null;
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        if(slots[i] != null){
            ItemStack stackAt;
            if(slots[i].stackSize <= j){
                stackAt = slots[i];
                slots[i] = null;
                this.markDirty();
                return stackAt;
            }
            else{
                stackAt = slots[i].splitStack(j);
                if(slots[i].stackSize == 0){
                    slots[i] = null;
                }
                this.markDirty();
                return stackAt;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int index) {
        return getStackInSlot(index);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack) {
        if(i < slots.length) this.slots[i] = stack;
        this.markDirty();
    }

    @Override
    public String getInventoryName() {
        return this.name;
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }


    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

}

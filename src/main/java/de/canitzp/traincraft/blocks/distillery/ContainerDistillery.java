package de.canitzp.traincraft.blocks.distillery;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.canitzp.traincraft.tile.slots.SlotFuel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

/**
 * @author canitzp
 */
public class ContainerDistillery extends Container {

    private TileEntityDistillery distillery;
    private int cookTime, burnTime, itemBurnTime, amount, maxBurnTime, usedBurnTime, capacity;
    private int fluidId;

    public ContainerDistillery(TileEntityDistillery distillery, EntityPlayer player){
        this.distillery = distillery;
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlotToContainer(new Slot(player.inventory, k + j * 9 + 9, 8 + k * 18, 84 + j * 18));
            }
        }
        for(int j = 0; j < 9; ++j) {
            this.addSlotToContainer(new Slot(player.inventory, j, 8 + j * 18, 142));
        }
        addSlotToContainer(new Slot(distillery, 0, 56, 17));
        addSlotToContainer(new SlotFuel(distillery, 1, 56, 53));
        addSlotToContainer(new Slot(distillery, 2, 123, 8));
        addSlotToContainer(new SlotDistillery(distillery, 3, 123, 33));
        addSlotToContainer(new SlotDistillery(distillery, 4, 116, 60));

    }

    @Override
    public void addCraftingToCrafters(ICrafting listener){
        super.addCraftingToCrafters(listener);
        listener.sendProgressBarUpdate(this, 0, this.distillery.distilCookTime);
        listener.sendProgressBarUpdate(this, 1, this.distillery.distilBurnTime);
        listener.sendProgressBarUpdate(this, 2, this.distillery.currentItemBurnTime);
        listener.sendProgressBarUpdate(this, 3, this.distillery.amount);
        listener.sendProgressBarUpdate(this, 4, this.distillery.maxBurnTime);
        listener.sendProgressBarUpdate(this, 5, this.distillery.usedBurnTime);
        listener.sendProgressBarUpdate(this, 6, this.distillery.capacity);
        listener.sendProgressBarUpdate(this, 7, this.distillery.fluidID);
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;
            if (this.cookTime != this.distillery.distilCookTime) {
                icrafting.sendProgressBarUpdate(this, 0, this.distillery.distilCookTime);
            }
            if (this.burnTime != this.distillery.distilBurnTime) {
                icrafting.sendProgressBarUpdate(this, 1, this.distillery.distilBurnTime);
            }
            if (this.itemBurnTime != this.distillery.currentItemBurnTime) {
                icrafting.sendProgressBarUpdate(this, 2, this.distillery.currentItemBurnTime);
            }
            if (this.amount != this.distillery.amount) {
                icrafting.sendProgressBarUpdate(this, 3, this.distillery.amount);
            }
            if (this.maxBurnTime != this.distillery.maxBurnTime){
                icrafting.sendProgressBarUpdate(this, 4, this.distillery.maxBurnTime);
            }
            if (this.usedBurnTime != this.distillery.usedBurnTime){
                icrafting.sendProgressBarUpdate(this, 5, this.distillery.usedBurnTime);
            }
            if (this.capacity != this.distillery.capacity){
                icrafting.sendProgressBarUpdate(this, 6, this.distillery.capacity);
            }
            if (this.fluidId != this.distillery.fluidID){
                icrafting.sendProgressBarUpdate(this, 7, this.distillery.fluidID);
            }
        }
        this.cookTime = this.distillery.distilCookTime;
        this.burnTime = this.distillery.distilBurnTime;
        this.itemBurnTime = this.distillery.currentItemBurnTime;
        this.amount = this.distillery.amount;
        this.maxBurnTime = this.distillery.maxBurnTime;
        this.usedBurnTime = this.distillery.usedBurnTime;
        this.capacity = this.distillery.capacity;
        this.fluidId = this.distillery.fluidID;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        if(id == 0) {
            this.distillery.distilCookTime = data;
        }
        if(id == 1) {
            this.distillery.distilBurnTime = data;
        }
        if(id == 2) {
            this.distillery.currentItemBurnTime = data;
        }
        if(id == 3){
            this.distillery.amount = data;
        }
        if(id == 4) {
            this.distillery.maxBurnTime = data;
        }
        if(id == 5) {
            this.distillery.usedBurnTime = data;
        }
        if(id == 6) {
            this.distillery.capacity = data;
        }
        if(id == 7) {
            this.distillery.fluidID = data;
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(index);
        if(slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if(index < 4) {
                if(!this.mergeItemStack(itemstack1, 4, this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if(!this.mergeItemStack(itemstack1, 0, 3, false)) {
                return null;
            }

            if(itemstack1.stackSize == 0) {
                slot.putStack((ItemStack)null);
            } else {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

}

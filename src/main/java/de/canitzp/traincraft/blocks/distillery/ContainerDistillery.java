package de.canitzp.traincraft.blocks.distillery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author canitzp
 */
public class ContainerDistillery extends Container {

    private TileEntityDistillery distillery;
    private int cookTime;
    private int burnTime;
    private int itemBurnTime;
    public int tankAmount;

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
        addSlotToContainer(new Slot(distillery, 1, 56, 53));
        addSlotToContainer(new Slot(distillery, 2, 123, 8));
        addSlotToContainer(new SlotDistillery(distillery, 3, 123, 33));
        addSlotToContainer(new SlotDistillery(distillery, 4, 116, 60));

    }

    @Override
    public void onCraftGuiOpened(ICrafting listener){
        super.onCraftGuiOpened(listener);
        listener.sendProgressBarUpdate(this, 0, this.distillery.distilCookTime);
        listener.sendProgressBarUpdate(this, 1, this.distillery.distilBurnTime);
        listener.sendProgressBarUpdate(this, 2, this.distillery.currentItemBurnTime);
        if(!this.distillery.tank.isEmpty()){
            listener.sendProgressBarUpdate(this, 3, this.distillery.tank.getFluid().amount);
        }
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
        for (ICrafting crafter : this.crafters) {
            if (this.cookTime != this.distillery.distilCookTime) {
                crafter.sendProgressBarUpdate(this, 0, this.distillery.distilCookTime);
            }
            if (this.burnTime != this.distillery.distilBurnTime) {
                crafter.sendProgressBarUpdate(this, 1, this.distillery.distilBurnTime);
            }
            if (this.itemBurnTime != this.distillery.currentItemBurnTime) {
                crafter.sendProgressBarUpdate(this, 2, this.distillery.currentItemBurnTime);
            }
            if(!this.distillery.tank.isEmpty()){
                if(this.tankAmount != this.distillery.tank.getFluid().amount){
                    crafter.sendProgressBarUpdate(this, 3, this.distillery.tank.getFluid().amount);
                }
            }
        }
        this.cookTime = this.distillery.distilCookTime;
        this.burnTime = this.distillery.distilBurnTime;
        this.itemBurnTime = this.distillery.currentItemBurnTime;
        if(!this.distillery.tank.isEmpty()) {
            this.tankAmount = this.distillery.tank.getFluid().amount;
        }
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
            if(this.distillery.tank.getFluid() != null)
                this.distillery.tank.getFluid().amount = data;
        }
    }

    @Override
    public boolean getCanCraft(EntityPlayer player) {
        return this.distillery.isUseableByPlayer(player);
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

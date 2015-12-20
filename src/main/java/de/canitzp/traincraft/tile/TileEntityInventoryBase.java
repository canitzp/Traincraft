package de.canitzp.traincraft.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

/**
 * @author canitzp
 */
public class TileEntityInventoryBase extends TileEntity implements IInventory {

    private final String name;
    public ItemStack[] slots;

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

    @Override
    public void writeToNBT(NBTTagCompound nbt){
        super.writeToNBT(nbt);
        this.writeNBT(nbt);
        if(this.slots.length > 0){
            NBTTagList tagList = new NBTTagList();
            for(int currentIndex = 0; currentIndex < slots.length; currentIndex++){
                if(slots[currentIndex] != null){
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte("Slot", (byte)currentIndex);
                    slots[currentIndex].writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            nbt.setTag("Items", tagList);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt){
        super.readFromNBT(nbt);
        this.readNBT(nbt);
        if(this.slots.length > 0){
            NBTTagList tagList = nbt.getTagList("Items", 10);
            for(int i = 0; i < tagList.tagCount(); i++){
                NBTTagCompound tagCompound = tagList.getCompoundTagAt(i);
                byte slotIndex = tagCompound.getByte("Slot");
                if(slotIndex >= 0 && slotIndex < slots.length){
                    slots[slotIndex] = ItemStack.loadItemStackFromNBT(tagCompound);
                }
            }
        }
    }

    @Override
    public Packet getDescriptionPacket(){
        NBTTagCompound tag = new NBTTagCompound();
        this.writeNBT(tag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        this.readNBT(pkt.func_148857_g());
    }


    public void writeNBT(NBTTagCompound nbt){

    }
    public void readNBT(NBTTagCompound nbt){

    }
}

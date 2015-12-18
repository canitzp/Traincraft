package de.canitzp.traincraft.blocks.distillery;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.canitzp.traincraft.ItemRegistry;
import de.canitzp.traincraft.VanillaPacketSyncer;
import de.canitzp.traincraft.tile.TileEntityInventoryBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.io.IOException;
import java.util.Random;

/**
 * @author canitzp
 */
public class TileEntityDistillery extends TileEntityInventoryBase{

    public int distilCookTime, distilBurnTime, currentItemBurnTime, cookDuration = 400, amount;
    public StandardTank tank;
    private ItemStack currentItemStack;

    public TileEntityDistillery(){
        super(6, "");
        this.tank = new StandardTank(30000);
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int i) {
        return this.distilCookTime * i / this.cookDuration;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i) {
        if(this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = this.cookDuration;
        }
        return (this.distilCookTime * i / this.cookDuration);
    }

    public boolean isBurning() {
        return this.distilBurnTime > 0;
    }

    public int getTankAmount(){
        return this.tank.getFluidAmount();
    }

    @Override
    public Packet getDescriptionPacket() {
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 3, this.writable());
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt){
        this.readSync(pkt.func_148857_g());
    }

    private void readSync(NBTTagCompound nbtTagCompound) {
        this.tank.readFromNBT(nbtTagCompound);
    }

    @Override
    public void updateEntity() {
        if(!this.worldObj.isRemote){
            if(this.isBurning()) {

                --this.distilBurnTime;
                ++this.distilCookTime;
                if(this.distilCookTime == this.cookDuration) {
                    this.distilCookTime = 0;
                    this.smeltItem();
                }
            } else {
                this.distilCookTime = 0;
            }

            if(this.distilBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.distilBurnTime = this.getItemBurnTime(this.getStackInSlot(0));
                this.currentItemStack = this.getStackInSlot(0);
                if(this.getStackInSlot(0).stackSize > 1) {
                    --this.getStackInSlot(0).stackSize;
                } else this.setInventorySlotContents(0, null);
            }

            if(this.getStackInSlot(2) != null){
                if(this.getStackInSlot(2).getItem() == ItemRegistry.fuelCanisterEmpty){
                    if(this.tank.getFluidAmount() >= 1000){
                        ItemStack output = DistilleryRecipeManager.getFillingOutput(this.tank.getFluid());
                        if(output != null){
                            this.setStackInSlot(3, output.copy());
                            if(this.getStackInSlot(2).stackSize > 1) {
                                --this.getStackInSlot(2).stackSize;
                            } else this.setInventorySlotContents(2, null);
                            this.tank.drain(1000, true);
                        }
                    }
                }
            }
        }
    }

    private void smeltItem() {
        ItemStack input = this.currentItemStack;
        ItemStack output = DistilleryRecipeManager.getSmeltingResult(input);
        int plasticChance = DistilleryRecipeManager.getOutputChance(input);
        FluidStack resultLiquid = DistilleryRecipeManager.getFluid(input);
        if (resultLiquid != null) {
            if (resultLiquid.amount <= this.tank.getCapacity()) {
                this.tank.fill(resultLiquid.copy(), true);
            }
        }
        if(output != null){
            Random rnd = new Random(System.nanoTime());
            if(rnd.nextInt(100) < plasticChance){
                this.setStackInSlot(4, output.copy());
            }
        }

        this.distilCookTime = 0;
        this.distilBurnTime = 0;
        this.cookDuration = 400;
        this.currentItemBurnTime = 0;
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
    }

    private int getItemBurnTime(ItemStack stack) {
        if(stack != null){
            return DistilleryRecipeManager.getBurnTime(stack);
        }
        return 0;
    }

    private boolean canSmelt() {
        if(this.getStackInSlot(0) != null){
            if(DistilleryRecipeManager.getBurnTime(this.getStackInSlot(0)) != 0) {
                FluidStack fluidStack = DistilleryRecipeManager.getFluid(this.getStackInSlot(0));
                return fluidStack == null || this.tank.fill(fluidStack, false) >= fluidStack.amount;
            }
        }
        return false;
    }

    private boolean isItemFuel(ItemStack stack) {
        return GameRegistry.getFuelValue(stack) != 0;
    }

    private void setStackInSlot(int slotId, ItemStack stack){
        if(this.getStackInSlot(slotId) != null){
            if(this.getStackInSlot(slotId).copy().isItemEqual(stack)){
                this.setInventorySlotContents(slotId, new ItemStack(stack.getItem(), this.getStackInSlot(slotId).copy().stackSize + stack.stackSize));
            }
        } else {
            this.setInventorySlotContents(slotId, stack);
        }
    }

    public class StandardTank extends FluidTank {
        private int tankIndex;

        public StandardTank(int capacity) {
            super(capacity);
        }

        public void setTankIndex(int index) {
            this.tankIndex = index;
        }

        public int getTankIndex() {
            return this.tankIndex;
        }

        public boolean isEmpty() {
            return this.getFluid() == null || this.getFluid().amount <= 0;
        }
    }

    public NBTTagCompound writable(){
        NBTTagCompound nbt = new NBTTagCompound();
        this.tank.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        this.tank.writeToNBT(compound);
        compound.setInteger("CookTime", this.distilCookTime);
        compound.setInteger("BurnTime", this.distilBurnTime);
        compound.setInteger("ItemBurnTime", this.currentItemBurnTime);
        compound.setInteger("CookDuration", this.cookDuration);
        if(this.currentItemStack != null)
            this.currentItemStack.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.tank.readFromNBT(compound);
        this.distilCookTime = compound.getInteger("CookTime");
        this.distilBurnTime = compound.getInteger("BurnTime");
        this.currentItemBurnTime = compound.getInteger("ItemBurnTime");
        this.cookDuration = compound.getInteger("CookDuration");
        if(this.currentItemStack != null)
            this.currentItemStack.readFromNBT(compound);
    }
}

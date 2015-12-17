package de.canitzp.traincraft.blocks.distillery;

import de.canitzp.traincraft.ItemRegistry;
import de.canitzp.traincraft.VanillaPacketSyncer;
import de.canitzp.traincraft.blocks.IBurnTime;
import de.canitzp.traincraft.tile.TileEntityInventoryBase;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * @author canitzp
 */
public class TileEntityDistillery extends TileEntityInventoryBase implements IFluidHandler, ITickable{

    public int distilCookTime, distilBurnTime, currentItemBurnTime, cookDuration = 400;
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

    @Override
    public void update() {
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
        int plasticChance = DistilleryRecipeManager.getPlasticChance(input);
        FluidStack resultLiquid = DistilleryRecipeManager.getFluid(input);
        if (resultLiquid != null) {
            if (this.tank.getFluid() == null && resultLiquid.amount <= this.tank.getCapacity() ||(resultLiquid.equals(this.tank.getFluid()) && (resultLiquid.amount + this.tank.getFluid().amount) <= this.tank.getCapacity())) {
                this.tank.fill(resultLiquid.copy(), true);
            }
        }
        if(output != null){
            this.setStackInSlot(4, output.copy());
        }
        Random rnd = new Random(System.nanoTime());
        if(rnd.nextInt(100) < plasticChance){
            this.setStackInSlot(4, new ItemStack(ItemRegistry.plastic));
        }
        this.distilCookTime = 0;
        this.distilBurnTime = 0;
        this.cookDuration = 400;
        this.currentItemBurnTime = 0;
    }

    private int getItemBurnTime(ItemStack stack) {
        if(stack != null){
            if(stack.getItem() instanceof IBurnTime){
                return ((IBurnTime) stack.getItem()).getBurnTime();
            } else if(Block.getBlockFromItem(stack.getItem()) instanceof IBurnTime){
                return ((IBurnTime) Block.getBlockFromItem(stack.getItem())).getBurnTime();
            } else return GameRegistry.getFuelValue(stack);
        }
        return 0;
    }

    private boolean canSmelt() {
        if(this.getStackInSlot(0) != null){
            if(this.isItemFuel(this.getStackInSlot(0))) {
                FluidStack fluidStack = DistilleryRecipeManager.getFluid(this.getStackInSlot(0));
                return fluidStack == null || this.tank.fill(fluidStack, false) >= fluidStack.amount;
            }
        }
        return false;
    }

    private boolean isItemFuel(ItemStack output) {
        return Block.getBlockFromItem(output.getItem()) instanceof IBurnTime || output.getItem() instanceof IBurnTime || GameRegistry.getFuelValue(output) != 0;
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

    @Override
    public int fill(EnumFacing from, FluidStack resource, boolean doFill) {
        return this.tank.getCapacity();
    }

    @Override
    public FluidStack drain(EnumFacing from, FluidStack resource, boolean doDrain) {
        return resource != null && resource.isFluidEqual(this.tank.getFluid())?this.tank.drain(resource.amount, doDrain):null;
    }

    @Override
    public FluidStack drain(EnumFacing from, int maxDrain, boolean doDrain) {
        return this.tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(EnumFacing from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(EnumFacing from) {
        return new FluidTankInfo[]{this.tank.getInfo()};
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

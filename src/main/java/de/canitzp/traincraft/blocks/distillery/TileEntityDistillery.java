package de.canitzp.traincraft.blocks.distillery;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.canitzp.traincraft.ItemRegistry;
import de.canitzp.traincraft.VanillaPacketSyncer;
import de.canitzp.traincraft.tile.TileEntitySidesInventoryBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.fluids.*;

import java.util.Random;

/**
 * @author canitzp
 */
public class TileEntityDistillery extends TileEntitySidesInventoryBase{

    public int distilCookTime, distilBurnTime, currentItemBurnTime, cookDuration, usedBurnTime, maxBurnTime, amount, capacity = 30000;
    public StandardTank tank;
    private ItemStack currentItemStack;
    public final int INPUT = 0, BURNMATERIAL = 1, CANISTERINPUT = 2, CANISTEROUTPUT = 3, OUTPUT = 4;
    public int fluidID;

    public TileEntityDistillery(){
        super(6, "Distillery");
        this.tank = new StandardTank(this.capacity);
    }

    @SideOnly(Side.CLIENT)
    public int getCookProgressScaled(int i) {
        if(this.currentItemBurnTime == 0) return 0;
        return this.distilBurnTime * i / this.currentItemBurnTime;
    }

    @SideOnly(Side.CLIENT)
    public int getBurnTimeRemainingScaled(int i) {
        if(this.maxBurnTime == 0) {
            return 0;
        }
        return (this.usedBurnTime * i / this.maxBurnTime);
    }

    public boolean isBurning() {
        return this.distilBurnTime > 0;
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
                int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                if(meta >= 5) worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 2);
                this.distilCookTime = 0;
            }


            if(this.distilBurnTime == 0 && this.canSmelt()) {
                VanillaPacketSyncer.sendTileToNearbyPlayers(this);
                if(this.usedBurnTime < this.getItemBurnTime(this.getStackInSlot(INPUT))){
                    this.usedBurnTime += TileEntityFurnace.getItemBurnTime(this.getStackInSlot(BURNMATERIAL));
                    this.maxBurnTime = this.usedBurnTime;
                    this.reduceStackSize(BURNMATERIAL);
                }
                this.cookDuration = this.currentItemBurnTime = this.distilBurnTime = this.getItemBurnTime(this.getStackInSlot(INPUT));
                this.currentItemStack = this.getStackInSlot(INPUT);
                this.reduceStackSize(INPUT);
                VanillaPacketSyncer.sendTileToNearbyPlayers(this);
                worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, worldObj.getBlockMetadata(xCoord, yCoord, zCoord) + 4, 2);
            }

            if(this.getStackInSlot(CANISTERINPUT) != null){
                if(this.getStackInSlot(CANISTERINPUT).getItem() == ItemRegistry.fuelCanisterEmpty){
                    if(this.tank.getFluidAmount() >= 1000){
                        ItemStack output = DistilleryRecipeManager.getFillingOutput(this.tank.getFluid());
                        if(output != null){
                            this.setStackInSlot(CANISTEROUTPUT, output.copy());
                            if(this.getStackInSlot(CANISTERINPUT).stackSize > 1) {
                                --this.getStackInSlot(CANISTERINPUT).stackSize;
                            } else this.setInventorySlotContents(CANISTERINPUT, null);
                            this.drain(1000);
                        }
                    }
                }
                VanillaPacketSyncer.sendTileToNearbyPlayers(this);
            }
        }
    }

    private void drain(int amount){
        this.tank.drain(amount, true);
        this.amount = this.tank.getFluidAmount();
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
        this.markDirty();
    }
    private void fill(FluidStack fluidStack){
        this.tank.fill(fluidStack, true);
        this.amount = this.tank.getFluidAmount();
        this.fluidID = this.tank.getFluid().getFluidID();
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
        this.markDirty();
    }

    private void smeltItem() {
        ItemStack input = this.currentItemStack;
        ItemStack output = DistilleryRecipeManager.getSmeltingResult(input);
        int plasticChance = DistilleryRecipeManager.getOutputChance(input);
        FluidStack resultLiquid = DistilleryRecipeManager.getFluid(input);
        if (resultLiquid != null) {
            if (resultLiquid.amount <= this.tank.getCapacity()) {
                this.fill(resultLiquid.copy());
                VanillaPacketSyncer.sendTileToNearbyPlayers(this);
            }
        }
        if(output != null){
            Random rnd = new Random(System.nanoTime());
            if(rnd.nextInt(100) < plasticChance){
                this.setStackInSlot(4, output.copy());
            }
        }

        this.usedBurnTime -= this.currentItemBurnTime;
        this.distilCookTime = 0;
        this.distilBurnTime = 0;
        this.cookDuration = 0;
        this.currentItemBurnTime = 0;
        if(this.usedBurnTime <= 0){
            this.maxBurnTime = 0;
        }
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, worldObj.getBlockMetadata(xCoord, yCoord, zCoord)-4, 2);
        this.markDirty();
    }

    private int getItemBurnTime(ItemStack stack) {
        if(stack != null){
            VanillaPacketSyncer.sendTileToNearbyPlayers(this);
            return DistilleryRecipeManager.getBurnTime(stack);
        }
        return 0;
    }

    private boolean canSmelt() {
        if(this.getStackInSlot(INPUT) != null && (this.getStackInSlot(BURNMATERIAL) != null || this.usedBurnTime >= this.getItemBurnTime(this.getStackInSlot(INPUT)))){
            if(DistilleryRecipeManager.getBurnTime(this.getStackInSlot(INPUT)) != 0 && (TileEntityFurnace.getItemBurnTime(this.getStackInSlot(BURNMATERIAL)) > 0 || this.usedBurnTime >= this.getItemBurnTime(this.getStackInSlot(INPUT)))) {
                FluidStack fluidStack = DistilleryRecipeManager.getFluid(this.getStackInSlot(INPUT));
                VanillaPacketSyncer.sendTileToNearbyPlayers(this);
                return fluidStack == null || this.tank.fill(fluidStack, false) >= fluidStack.amount;
            }
        }
        return false;
    }

    private void setStackInSlot(int slotId, ItemStack stack){
        if(this.getStackInSlot(slotId) != null){
            if(this.getStackInSlot(slotId).copy().isItemEqual(stack)){
                this.setInventorySlotContents(slotId, new ItemStack(stack.getItem(), this.getStackInSlot(slotId).copy().stackSize + stack.stackSize));
            }
        } else {
            this.setInventorySlotContents(slotId, stack);
        }
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
    }

    private void reduceStackSize(int slotId){
        if(this.getStackInSlot(slotId) != null){
            if(this.getStackInSlot(slotId).stackSize > 1){
                this.getStackInSlot(slotId).stackSize--;
            } else {
                this.setInventorySlotContents(slotId, null);
            }
        }
        VanillaPacketSyncer.sendTileToNearbyPlayers(this);
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
    public void writeNBT(NBTTagCompound nbt){
        nbt.setInteger("maxBurnTime", this.maxBurnTime);
        nbt.setInteger("usedBurnTime", this.usedBurnTime);
        nbt.setInteger("CookTime", this.distilCookTime);
        nbt.setInteger("BurnTime", this.distilBurnTime);
        nbt.setInteger("ItemBurnTime", this.currentItemBurnTime);
        nbt.setInteger("CookDuration", this.cookDuration);
        nbt.setInteger("amount", this.amount);
        nbt.setInteger("capacity", this.capacity);
        nbt.setInteger("fluidId", this.fluidID);
        if(this.currentItemStack != null){
            NBTTagCompound nbt2 = new NBTTagCompound();
            this.currentItemStack.writeToNBT(nbt2);
            nbt.setTag("ItemStack", nbt2);
        }

    }

    @Override
    public void readNBT(NBTTagCompound nbt){
        this.maxBurnTime = nbt.getInteger("maxBurnTime");
        this.usedBurnTime = nbt.getInteger("usedBurnTime");
        this.distilCookTime = nbt.getInteger("CookTime");
        this.distilBurnTime = nbt.getInteger("BurnTime");
        this.currentItemBurnTime = nbt.getInteger("ItemBurnTime");
        this.cookDuration = nbt.getInteger("CookDuration");
        this.amount = nbt.getInteger("amount");
        this.capacity = nbt.getInteger("capacity");
        this.fluidID = nbt.getInteger("fluidId");
        NBTTagCompound nbt2 = nbt.getCompoundTag("ItemStack");
        if(nbt2 != null) this.currentItemStack = ItemStack.loadItemStackFromNBT(nbt2);
        Fluid fluid = FluidRegistry.getFluid(this.fluidID);
        if(fluid != null) this.tank.setFluid(new FluidStack(fluid, this.amount));
    }



    @Override
    public boolean canInsertItem(int slotId, ItemStack stack, int side) {
        /*if(side == ){

        }
        */
        return false;
    }

    @Override
    public boolean canExtractItem(int slotId, ItemStack stack, int side) {
        return false;
    }
}

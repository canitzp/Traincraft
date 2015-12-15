package de.canitzp.traincraft.blocks.distillery;

import de.canitzp.traincraft.tile.TileEntityInventoryBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.*;

/**
 * @author canitzp
 */
public class TileEntityDistillery extends TileEntityInventoryBase implements IFluidHandler{

    public int distilCookTime, distilBurnTime, currentItemBurnTime;
    public StandardTank tank;

    public TileEntityDistillery(){
        super(6, "");
        this.tank = new StandardTank(30000);
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
}

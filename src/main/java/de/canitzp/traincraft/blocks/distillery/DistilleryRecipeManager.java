package de.canitzp.traincraft.blocks.distillery;

import com.google.common.collect.Maps;
import com.sun.istack.internal.Nullable;
import de.canitzp.traincraft.blocks.BlockRegistry;
import de.canitzp.traincraft.items.ItemRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.util.Map;

/**
 * @author canitzp
 */
public class DistilleryRecipeManager {

    private static Map<String, ItemStack> outputList = Maps.newHashMap();
    private static Map<String, FluidStack> fluidList = Maps.newHashMap();
    private static Map<String, Float> experienceList = Maps.newHashMap();
    private static Map<String, Integer> outputChanceList = Maps.newHashMap();
    private static Map<String, Integer> burnTimeList = Maps.newHashMap();
    private static Map<String, ItemStack> outputListFilling = Maps.newHashMap();

    public static void postInit(){
        addSmeltingRecipe(new ItemStack(BlockRegistry.oilSand), new ItemStack(ItemRegistry.plastic), 200, new FluidStack(FluidRegistry.WATER, 1000), 0, 50);
        addFillingRecipe(new ItemStack(ItemRegistry.fuelCanister, 1), new FluidStack(FluidRegistry.WATER, 1000));
    }

    public static void addSmeltingRecipe(ItemStack input, ItemStack output, int burnTime, @Nullable FluidStack fluidStack, float exp, int outputChance) {
        outputList.put(input.getUnlocalizedName(), output);
        fluidList.put(input.getUnlocalizedName(), fluidStack);
        experienceList.put(input.getUnlocalizedName(), exp);
        outputChanceList.put(input.getUnlocalizedName(), outputChance);
        burnTimeList.put(input.getUnlocalizedName(), burnTime);
    }

    public static void addSmeltingRecipe(ItemStack input, ItemStack output, int burnTime, float exp, int plasticChance){
        addSmeltingRecipe(input, output, burnTime, null, exp, plasticChance);
    }

    public static void addSmeltingRecipe(ItemStack input, ItemStack output, float exp, int plasticChance){
        addSmeltingRecipe(input, output, 200, null, exp, plasticChance);
    }

    public static void addFillingRecipe(ItemStack output, FluidStack fluid){
        outputListFilling.put(fluid.getUnlocalizedName(), output);
    }

    public static float getExperience(ItemStack stack) {
        return experienceList.containsKey(stack.getUnlocalizedName()) ? experienceList.get(stack.getUnlocalizedName()) :0.0F;
    }

    public static int getOutputChance(ItemStack stack) {
        return outputChanceList.containsKey(stack.getUnlocalizedName()) ? outputChanceList.get(stack.getUnlocalizedName()) : 0;
    }

    public static int getBurnTime(ItemStack stack) {
        return burnTimeList.containsKey(stack.getUnlocalizedName()) ? burnTimeList.get(stack.getUnlocalizedName()) : 0;
    }

    public static ItemStack getSmeltingResult(ItemStack stack) {
        return outputList.get(stack.getUnlocalizedName());
    }

    public static FluidStack getFluid(ItemStack stack) {
        return fluidList.get(stack.getUnlocalizedName());
    }

    public static ItemStack getFillingOutput(FluidStack output){
        return outputListFilling.get(output.getUnlocalizedName());
    }


}

package de.canitzp.traincraft.blocks.distillery;

import com.google.common.collect.Maps;
import com.sun.istack.internal.Nullable;
import de.canitzp.traincraft.BlockRegistry;
import de.canitzp.traincraft.ItemRegistry;
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
    private static Map<String, Integer> plasticChanceList = Maps.newHashMap();
    private static Map<String, ItemStack> outputListFilling = Maps.newHashMap();

    public static void postInit(){
        addSmeltingRecipe(new ItemStack(BlockRegistry.oilSand), null, new FluidStack(FluidRegistry.WATER, 1000), 0, 50);
        addFillingRecipe(new ItemStack(ItemRegistry.fuelCanister, 1), new FluidStack(FluidRegistry.WATER, 1000));
    }

    public static void addSmeltingRecipe(ItemStack input, ItemStack output, @Nullable FluidStack fluidStack, float exp, int plasticChance) {
        outputList.put(input.getUnlocalizedName(), output);
        fluidList.put(input.getUnlocalizedName(), fluidStack);
        experienceList.put(input.getUnlocalizedName(), exp);
        plasticChanceList.put(input.getUnlocalizedName(), plasticChance);
    }

    public static void addSmeltingRecipe(ItemStack input, ItemStack output, float exp, int plasticChance){
        addSmeltingRecipe(input, output, null, exp, plasticChance);
    }

    public static void addFillingRecipe(ItemStack output, FluidStack fluid){
        outputListFilling.put(fluid.getUnlocalizedName(), output);
    }

    public static float getExperience(ItemStack stack) {
        return experienceList.containsKey(stack.getUnlocalizedName()) ? experienceList.get(stack.getUnlocalizedName()) :0.0F;
    }

    public static int getPlasticChance(ItemStack stack) {
        return plasticChanceList.containsKey(stack.getUnlocalizedName()) ? plasticChanceList.get(stack.getUnlocalizedName()) : 0;
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

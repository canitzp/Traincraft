package de.canitzp.traincraft;

import de.canitzp.traincraft.items.ItemFuel;
import net.minecraft.item.Item;

/**
 * @author canitzp
 */
public class ItemRegistry {

    public static Item itemFuelCanister, itemEmptyFuelCanister;

    public static void preInit(){
        itemFuelCanister = new ItemFuel("itemFuelCanister");
        itemEmptyFuelCanister = new ItemFuel("itemEmptyFuelCanister"){@Override public boolean isItemFuel() {return false;}};
    }

}

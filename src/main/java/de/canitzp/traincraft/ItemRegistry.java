package de.canitzp.traincraft;

import de.canitzp.traincraft.items.ItemBase;
import net.minecraft.item.Item;

/**
 * @author canitzp
 */
public class ItemRegistry {

    public static Item itemFuelCanister;

    public static void preInit(){
        itemFuelCanister = new ItemBase("itemFuelCanister");
    }

}

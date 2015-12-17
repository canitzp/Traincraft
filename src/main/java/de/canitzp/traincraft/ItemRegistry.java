package de.canitzp.traincraft;

import de.canitzp.traincraft.blocks.rails.Tracks;
import de.canitzp.traincraft.items.ItemBase;
import de.canitzp.traincraft.items.ItemFuel;
import net.minecraft.item.Item;

/**
 * @author canitzp
 */
public class ItemRegistry {

    public static Item fuelCanister, fuelCanisterEmpty, plastic;

    public static void preInit(){
        Tracks.preInitItems();
        fuelCanister = new ItemFuel("fuelCanister");
        fuelCanisterEmpty = new ItemFuel("fuelCanisterEmpty"){@Override public boolean isItemFuel() {return false;}};
        plastic = new ItemBase("plastic");
    }

}

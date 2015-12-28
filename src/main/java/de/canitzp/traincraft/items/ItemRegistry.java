package de.canitzp.traincraft.items;

import de.canitzp.traincraft.blocks.rails.Tracks;
import de.canitzp.traincraft.items.ItemBase;
import net.minecraft.item.Item;

/**
 * @author canitzp
 */
public class ItemRegistry {

    public static Item fuelCanister, fuelCanisterEmpty, plastic;

    public static void preInit(){
        fuelCanister = new ItemBase("fuelCanister");
        fuelCanisterEmpty = new ItemBase("fuelCanisterEmpty");
        plastic = new ItemBase("plastic");
    }

}

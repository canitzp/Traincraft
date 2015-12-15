package de.canitzp.traincraft.items;

import de.canitzp.traincraft.Traincraft;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * @author canitzp
 */
public class ItemBase extends Item {

    public ItemBase(String name){
        this.setUnlocalizedName(Traincraft.MODID + "." + name);
        this.setCreativeTab(Traincraft.traincraftTab);
        GameRegistry.registerItem(this, name);
    }

}

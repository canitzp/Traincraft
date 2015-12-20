package de.canitzp.traincraft.items;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.Traincraft;
import net.minecraft.item.Item;

/**
 * @author canitzp
 */
public class ItemBase extends Item {

    public ItemBase(String name){
        this.setUnlocalizedName(Traincraft.MODID + "." + name);
        this.setTextureName(Traincraft.MODID + ":" + name);
        this.setCreativeTab(Traincraft.traincraftTab);
        this.setMaxStackSize(64);
        GameRegistry.registerItem(this, name);
    }

}

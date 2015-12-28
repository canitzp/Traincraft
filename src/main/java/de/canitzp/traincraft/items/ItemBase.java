package de.canitzp.traincraft.items;

import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.Traincraft;
import de.canitzp.traincraft.entity.EntityTrain;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

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

package de.canitzp.traincraft.entity;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.world.World;

/**
 * @author canitzp
 */
public class EntityTrain extends EntityMinecart {

    public EntityTrain(World world) {
        super(world);
    }

    @Override
    public int getMinecartType() {
        return 0;
    }
}

package de.canitzp.traincraft.items;

/**
 * @author canitzp
 */
public class ItemFuel extends ItemBase implements IItemFuel {

    public ItemFuel(String name) {
        super(name);
    }

    @Override
    public boolean isItemFuel() {
        return true;
    }

    @Override
    public int getBurnTime() {
        return 500;
    }
}

package de.canitzp.traincraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import de.canitzp.traincraft.blocks.BlockRegistry;
import de.canitzp.traincraft.blocks.distillery.DistilleryRecipeManager;
import de.canitzp.traincraft.entity.EntityTrain;
import de.canitzp.traincraft.gen.WorldGen;
import de.canitzp.traincraft.items.ItemRegistry;
import de.canitzp.traincraft.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author canitzp
 */
@Mod(modid = Traincraft.MODID, name = Traincraft.NAME, version = Traincraft.VERSION)
public class Traincraft {

    //Initialising Variables:
    public static final String MODID = "traincraft";
    public static final String NAME = "Traincraft";
    public static final String VERSION = "5.0.0_001-1.7.10";
    public static final String CLIENTPROXY = "de.canitzp.traincraft.proxy.ClientProxy";
    public static final String SERVERPROXY = "de.canitzp.traincraft.proxy.CommonProxy";
    @Mod.Instance(Traincraft.MODID)
    public static Traincraft traincraft;
    @SidedProxy(clientSide = Traincraft.CLIENTPROXY, serverSide = Traincraft.SERVERPROXY)
    public static CommonProxy proxy;
    public static Logger logger = LogManager.getLogger(Traincraft.NAME);
    public static CreativeTabs traincraftTab;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger.info("Starting PreInitialization of Traincraft");
        traincraftTab = new CreativeTabs(NAME) {@Override public Item getTabIconItem() {return Items.item_frame;}};
        proxy.registerRenderIds();
        BlockRegistry.preInit();
        ItemRegistry.preInit();
        EntityRegistry.registerModEntity(EntityTrain.class, "entityTrain", 1, traincraft, 80, 30, true);
        GameRegistry.registerWorldGenerator(new WorldGen(), 10);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        logger.info("Starting Initialization of Traincraft");
        proxy.registerTileEntitys();
        NetworkRegistry.INSTANCE.registerGuiHandler(traincraft, new GuiHandler());
        proxy.registerRenderer();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        logger.info("Starting PostInitialization of Traincraft");
        DistilleryRecipeManager.postInit();
    }
}

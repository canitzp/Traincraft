package de.canitzp.traincraft;

import de.canitzp.traincraft.gen.WorldGen;
import de.canitzp.traincraft.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
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
    public static final String VERSION = "5.0.0_001";
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
        BlockRegistry.preInit();
        TileEntityRegistry.preInit();
        GameRegistry.registerWorldGenerator(new WorldGen(), 10);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        logger.info("Starting Initialization of Traincraft");
        NetworkRegistry.INSTANCE.registerGuiHandler(traincraft, new GuiHandler());
        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(BlockRegistry.oilSand), 0, new ModelResourceLocation(Traincraft.MODID + ":oilSand", "inventory"));

    }

}

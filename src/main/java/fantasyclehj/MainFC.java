package fantasyclehj;

import fantasyclehj.registry.core.AutoRegistry;
import fantasyclehj.init.FCBlocks;
import fantasyclehj.init.FCCommands;
import fantasyclehj.init.FCItems;
import fantasyclehj.init.FCRecipes;
import fantasyclehj.init.DimensionRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid = MainFC.MODID,
        name = MainFC.NAME,
        version = MainFC.VERSION,
        dependencies = ""

)
public class MainFC {
    public static final String MODID = "fantasyclehj";
    public static final String NAME = "fantasy";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        DimensionRegistry.register();
        AutoRegistry.scanAndCollect(FCBlocks.class, FCItems.class);

        //GameRegistry.registerWorldGenerator(new FCWorldGen(), 0); // 注册世界生成器
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FCRecipes.init();
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        FCCommands.register(event);
    }
}
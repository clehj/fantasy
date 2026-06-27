package fantasyclehj;

import fantasyclehj.Init.FCCommands;
import fantasyclehj.Init.ModRegistry.FCRecips;
import fantasyclehj.Init.ModRegistry.InitDimension;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
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
        InitDimension.registerDimensions();
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        FCRecips.Init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        FCCommands.registerCommands(event);
    }
}
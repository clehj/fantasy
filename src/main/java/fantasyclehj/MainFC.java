package fantasyclehj;

import fantasyclehj.event.SpawnerInteractionHandler;
import fantasyclehj.init.*;
import fantasyclehj.registry.core.AutoRegistry;
import fantasyclehj.renderer.TileExpSpawnerRenderer;
import fantasyclehj.renderer.TileItemSpawnerRenderer;
import fantasyclehj.tile.TileExpSpawner;
import fantasyclehj.tile.TileItemSpawner;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = MainFC.MODID, name = MainFC.NAME, version = MainFC.VERSION)
public class MainFC {
    public static final String MODID = "fantasyclehj";
    public static final String NAME = "fantasy";
    public static final String VERSION = "1.0.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        DimensionRegistry.register();
        AutoRegistry.scanAndCollect(FCBlocks.class, FCItems.class);
        FCTileEntities.register();
        FCPackets.register();
        MinecraftForge.EVENT_BUS.register(new SpawnerInteractionHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FCRecipes.init();
        registerRenderers();
        FCPotions.registerBrewingRecipes();
    }

    @SideOnly(Side.CLIENT)
    private void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileItemSpawner.class,
                TileItemSpawnerRenderer.INSTANCE);
        ClientRegistry.bindTileEntitySpecialRenderer(TileExpSpawner.class,
                TileExpSpawnerRenderer.INSTANCE);
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        FCCommands.register(event);
    }
}
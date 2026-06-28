package fantasyclehj.init;

import fantasyclehj.MainFC;
import fantasyclehj.tile.TileExpSpawner;
import fantasyclehj.tile.TileItemSpawner;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class FCTileEntities {

    public static void register() {
        GameRegistry.registerTileEntity(TileItemSpawner.class,
                new ResourceLocation(MainFC.MODID, "item_spawner"));
        GameRegistry.registerTileEntity(TileExpSpawner.class,
                new ResourceLocation(MainFC.MODID, "exp_spawner"));
    }
}
package fantasyclehj.init;

import fantasyclehj.dimension.FantasyClehjDO;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class DimensionRegistry {
    public static DimensionType FANTASY_DIMENSION;

    public static void register() {
        FANTASY_DIMENSION = DimensionType.register("Fantasy", "_fantasy", 10, FantasyClehjDO.class, false);
        DimensionManager.registerDimension(10, FANTASY_DIMENSION);
    }
}
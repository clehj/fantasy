package fantasyclehj.Init.ModRegistry;

import fantasyclehj.Worlds.Dimension.FantasyClehjDO;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class InitDimension {
    // 保存维度类型实例，供其他地方使用
    public static DimensionType FANTASY_DIMENSION;

    public static void registerDimensions() {
        // 注册维度类型（只注册一次）
        FANTASY_DIMENSION = DimensionType.register("Fantasy", "_fantasy", 10, FantasyClehjDO.class, false);
        // 注册维度
        DimensionManager.registerDimension(10, FANTASY_DIMENSION);
    }
}
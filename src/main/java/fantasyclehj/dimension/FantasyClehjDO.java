package fantasyclehj.dimension;

import fantasyclehj.init.DimensionRegistry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class FantasyClehjDO extends WorldProvider {

    @Override
    public DimensionType getDimensionType() {
        // 直接返回已注册的实例，不再重复注册
        return DimensionRegistry.FANTASY_DIMENSION;
    }

    @Override
    public IChunkGenerator createChunkGenerator() {
        return new FCDOChunkGenerator(world);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean isSurfaceWorld() {
        return true;
    }
}
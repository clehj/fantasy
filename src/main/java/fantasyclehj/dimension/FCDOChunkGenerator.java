package fantasyclehj.dimension;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;


import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class FCDOChunkGenerator implements IChunkGenerator {
    // 对象池重用ChunkPrimer
    private static final Queue<ChunkPrimer> PRIMER_POOL = new ArrayBlockingQueue<>(50);

    // 预定义方块状态
    private static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
    private static final IBlockState QUARTZ_ORE = Blocks.QUARTZ_ORE.getDefaultState();
    private static final IBlockState AIR = Blocks.AIR.getDefaultState();

    private final World world;
    private int generatedChunks = 0;

    public FCDOChunkGenerator(World world) {
        this.world = world;

        // 预热对象池
        for (int i = 0; i < 20; i++) {
            PRIMER_POOL.offer(new ChunkPrimer());
        }
    }

    @Override
    public Chunk generateChunk(int chunkX, int chunkZ) {
        long startTime = System.nanoTime();

        // 从对象池获取或创建ChunkPrimer
        ChunkPrimer primer = PRIMER_POOL.poll();
        if (primer == null) {
            primer = new ChunkPrimer();
        }

        // 生成基础地形
        generateBaseTerrain(primer);

        // 创建区块对象
        Chunk chunk = new Chunk(world, primer, chunkX, chunkZ);

        // 必要的光照初始化
        chunk.generateSkylightMap();

        // 将primer返回对象池
        PRIMER_POOL.offer(primer);

        // 性能监控
        generatedChunks++;
        if (generatedChunks % 100 == 0) {
            long duration = System.nanoTime() - startTime;
            System.out.printf("Generated %d chunks. Last chunk time: %.2fms%n",
                    generatedChunks, duration / 1_000_000.0);
        }

        return chunk;
    }

    private void generateBaseTerrain(ChunkPrimer primer) {
        // 优化1: 使用分层填充代替三层循环
        fillLayer(0, 63, NETHERRACK, primer);  // 0-63层: 地狱岩
        fillLayer(64, 64, QUARTZ_ORE, primer); // 64层: 石英矿

        // 65-255层: 空气 (ChunkPrimer默认就是空气)
    }

    private void fillLayer(int startY, int endY, IBlockState state, ChunkPrimer primer) {
        for (int y = startY; y <= endY; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    primer.setBlockState(x, y, z, state);
                }
            }
        }
    }

    @Override
    public void populate(int x, int z) {
        // 延迟生成安全平台
        if (x == 0 && z == 0) {
            world.getMinecraftServer().addScheduledTask(() -> {
                generateSafePlatform();
            });
        }
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    private void generateSafePlatform() {
        BlockPos center = new BlockPos(8, 65, 8); // 区块中心位置
        int radius = 4;

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dz = -radius; dz <= radius; dz++) {
                BlockPos pos = center.add(dx, 0, dz);
                world.setBlockState(pos, Blocks.QUARTZ_BLOCK.getDefaultState());
            }
        }
    }



    // ... 其他方法保持不变 ...
}
package fantasyclehj.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PortalBlock extends Block {

    private static final int DIMENSION_ID = 10;
    private static final int OVERWORLD_ID = 0;

    public PortalBlock(Material materialIn) {
        super(materialIn);
        this.setTickRandomly(true);
    }

    //@Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) {
        if (!world.isRemote && entity instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) entity;

            int currentDim = player.dimension;
            int targetDim = (currentDim == DIMENSION_ID) ? OVERWORLD_ID : DIMENSION_ID;

            System.out.println("[Portal] 当前维度: " + currentDim + " → 目标维度: " + targetDim);

            WorldServer targetWorld = player.getServer().getWorld(targetDim);
            if (targetWorld != null) {
                player.changeDimension(targetDim, new CustomTeleporter(targetWorld, targetDim));
            } else {
                System.out.println("[Portal] 错误：目标维度 " + targetDim + " 不存在或未加载！");
            }
        }
    }

    private static class CustomTeleporter extends Teleporter {
        private final int targetDim;

        public CustomTeleporter(WorldServer worldIn, int targetDim) {
            super(worldIn);
            this.targetDim = targetDim;
        }

        @Override
        public void placeInPortal(Entity entity, float rotationYaw) {
            BlockPos targetPos;

            if (targetDim == DIMENSION_ID) {
                targetPos = new BlockPos(8, 65, 8);
            } else {
                // 主世界：世界出生点
                targetPos = world.getSpawnPoint();
            }

            // 第一次强制设置位置
            entity.setPositionAndUpdate(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);

            // 延迟1tick再强制设置一次，覆盖Minecraft的默认行为（防止床干扰）
            world.getMinecraftServer().addScheduledTask(() -> {
                entity.setPositionAndUpdate(targetPos.getX() + 0.5, targetPos.getY(), targetPos.getZ() + 0.5);
            });
        }
    }
}
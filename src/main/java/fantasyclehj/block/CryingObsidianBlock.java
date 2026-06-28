package fantasyclehj.block;

import fantasyclehj.init.FCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;


public class CryingObsidianBlock extends Block {



    public CryingObsidianBlock(Material materialIn) {

        super(materialIn);
    }
    //方块掉落ModBlocks.crying_obsidian
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(FCBlocks.crying_obsidian);
    }
    //方块在地图上显示的颜色return MapColor.BLACK
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.BLACK;
    }

    //方块与活塞交互
    //@Override
    public EnumPushReaction getMobilityFlag(IBlockState state) {
        // 返回 BLOCK，表示该方块不能被活塞推动
        // 返回 NORMAL，方块可以被活塞正常推动。
        // 返回 DESTROY，方块会被活塞破坏并掉落物品。
        // 返回 PUSH_ONLY，方块可以被活塞推动，但不会被破坏。

        return EnumPushReaction.BLOCK;
    }
    //方块的破坏对象
    @Override
    public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
        // 只有玩家可以破坏方块
        return entity instanceof EntityPlayer;
    }

    //传送门检测
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(world, pos, state, placer, stack);
        checkAndCreatePortal(world, pos);
    }


    protected void checkAndCreatePortal(World world, BlockPos pos) {
        // 检测传送门结构的逻辑
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                BlockPos checkPos = pos.add(x, 0, z);
                if (!world.getBlockState(checkPos).getBlock().equals(this)) {
                    return; // 如果有任意一个方块不符合要求，直接返回
                }
            }
        }

        // 如果检测通过，创建传送门
        createPortal(world, pos);
    }

    private void createPortal(World world, BlockPos pos) {
        // 在中心位置生成传送门方块
        BlockPos portalPos = pos.add(0, 1, 0); // 中心位置的上方
        world.setBlockState(portalPos,
                FCBlocks.portal.getDefaultState());

        world.notifyBlockUpdate(portalPos,
                world.getBlockState(portalPos),
                FCBlocks.portal.getDefaultState(),
                3);
        System.out.println("Portal created at: " + portalPos); // 添加日志输出
    }

}



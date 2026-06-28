package fantasyclehj.block;

import fantasyclehj.tile.TileItemSpawner;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ItemSpawnerBlock extends BlockMobSpawner implements ITileEntityProvider {

    public ItemSpawnerBlock() {
        this.setHardness(20.0F);
        this.setResistance(6000000.0F);
        this.setLightLevel(0);
        // CreativeTab 由 @AutoRegister 统一管理
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileItemSpawner();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
                                    EntityPlayer player, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }

        ItemStack item = player.getHeldItem(EnumHand.MAIN_HAND);
        if (!item.isEmpty()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileItemSpawner) {
                TileItemSpawner spawner = (TileItemSpawner) tile;
                ItemStack copy = item.copy();
                copy.setCount(1);
                spawner.setItem(copy);
                // 同步到客户端
                spawner.syncToClient();
                return true;
            }
        }
        return true;
    }
}
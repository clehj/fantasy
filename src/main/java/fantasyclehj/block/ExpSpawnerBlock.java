package fantasyclehj.block;

import fantasyclehj.tile.TileExpSpawner;
import net.minecraft.block.BlockMobSpawner;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class ExpSpawnerBlock extends BlockMobSpawner implements ITileEntityProvider {

    public ExpSpawnerBlock() {
        this.setHardness(20.0F);
        this.setResistance(6000000.0F);
        this.setLightLevel(0);
        // CreativeTab 由 @AutoRegister 统一管理
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileExpSpawner();
    }
}
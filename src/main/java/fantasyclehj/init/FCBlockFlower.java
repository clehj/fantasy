package fantasyclehj.init;

import net.minecraft.block.BlockBush;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class FCBlockFlower extends BlockBush {
    public static final int DEFAULT_META = 0;

    public FCBlockFlower() {
        this.setCreativeTab(CreativeTabs.DECORATIONS); // 设置到装饰物分类
    }

    @Override
    public int damageDropped(IBlockState state) {
        return DEFAULT_META; // 返回固定的元数据
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        items.add(new ItemStack(this, 1, DEFAULT_META)); // 添加固定类型的 ItemStack
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState(); // 返回默认状态
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return DEFAULT_META; // 返回固定的元数据
    }
}
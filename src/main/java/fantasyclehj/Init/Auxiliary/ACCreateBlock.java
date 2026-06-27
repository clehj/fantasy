package fantasyclehj.Init.Auxiliary;

import fantasyclehj.MainFC;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class ACCreateBlock {
    // 辅助方法，用于创建方块并设置其属性
    public static Block createBlock(
            Block block,
            String name,
            CreativeTabs tab,
            float hardness,
            float resistance,
            String toolType,
            int toolLevel,
            int LigthLevel
    ) {
        block.setRegistryName(new ResourceLocation(MainFC.MODID, name));
        block.setTranslationKey(name);
        block.setCreativeTab(tab);
        block.setHardness(hardness);
        block.setResistance(resistance);
        block.setHarvestLevel(toolType,toolLevel);
        block.setLightLevel(LigthLevel);

        return block;
    }

}
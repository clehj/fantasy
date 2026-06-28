package fantasyclehj.registry.helper;

import fantasyclehj.MainFC;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class BlockBuilder {
    public static Block create(
            Block block,
            String name,
            CreativeTabs tab,
            float hardness,
            float resistance,
            String toolType,
            int toolLevel,
            int lightLevel
    ) {
        block.setRegistryName(new ResourceLocation(MainFC.MODID, name));
        block.setTranslationKey(name);
        block.setCreativeTab(tab);
        block.setHardness(hardness);
        block.setResistance(resistance);
        if (toolType != null) {
            block.setHarvestLevel(toolType, toolLevel);
        }
        block.setLightLevel(lightLevel);
        return block;
    }

    public static Block create(Block block, String name, CreativeTabs tab, float hardness, float resistance) {
        return create(block, name, tab, hardness, resistance, null, 0, 0);
    }
}
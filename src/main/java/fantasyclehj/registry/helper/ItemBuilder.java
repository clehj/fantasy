package fantasyclehj.registry.helper;

import fantasyclehj.MainFC;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

public class ItemBuilder {

    /**
     * 创建并配置一个新的物品实例。
     *
     * @param name  物品名称（用于未本地化名称，与注册名相同）
     * @param creativeTab      物品所属的创造模式标签页
     * @return 配置好的物品实例
     */
    public static Item createItem(String name, CreativeTabs creativeTab) {
        Item item = new Item();
        item.setRegistryName(new ResourceLocation(MainFC.MODID, name));
        item.setTranslationKey(name);
        item.setCreativeTab(creativeTab);
        return item;
    }

    /**
     * 创建并配置一个新的 ItemBlock 实例。
     *
     * @param block            对应的方块
     * @param registryName     ItemBlock 的注册名（通常与方块的注册名相同）
     * @param creativeTab      ItemBlock 所属的创造模式标签页
     * @return 配置好的 ItemBlock 实例
     */
    public static ItemBlock createItemBlock(Block block, String registryName, CreativeTabs creativeTab) {
        ItemBlock itemBlock = new ItemBlock(block);
        itemBlock.setRegistryName(new ResourceLocation(MainFC.MODID, registryName));
        itemBlock.setTranslationKey(registryName);
        itemBlock.setCreativeTab(creativeTab);
        return itemBlock;
    }
}
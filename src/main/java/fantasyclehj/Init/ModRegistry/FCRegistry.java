package fantasyclehj.Init.ModRegistry;

import fantasyclehj.Init.FCBlocks;
import fantasyclehj.Init.FCItems;
import fantasyclehj.Init.FCItemsBlockModels;
import fantasyclehj.MainFC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = MainFC.MODID)
public class FCRegistry {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        // 先注册所有方块
        event.getRegistry().registerAll(
                FCBlocks.crying_obsidian,
                FCBlocks.blue_rose,
                FCBlocks.portal
        );
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        // 注册所有普通物品
        event.getRegistry().registerAll(
                FCItems.obsidian_stick,
                FCItems.obsidianHandle_dsd,
                FCItems.obsidianHandle_dae,
                FCItems.obsidianHandle_dpe,
                FCItems.obsidianHandle_dsl,
                FCItems.obsidianHandle_dhe
        );

        // 注册所有 ItemBlock（方块对应的物品）
        // 注意：ItemBlock 的注册必须在对应的 Block 注册之后
        // 因为 @SubscribeEvent 的执行顺序不确定，所以把所有 Item 放一起注册最安全
        event.getRegistry().registerAll(
                FCItemsBlockModels.crying_obsidian_item,
                FCItemsBlockModels.blue_rose_item
        );




    }
}
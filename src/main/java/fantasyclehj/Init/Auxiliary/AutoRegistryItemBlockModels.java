package fantasyclehj.Init.Auxiliary;

import fantasyclehj.Init.FCItems;
import fantasyclehj.Init.FCItemsBlockModels;
import fantasyclehj.MainFC;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MainFC.MODID)
public class AutoRegistryItemBlockModels {
    @SubscribeEvent
    public static void onModelReg(ModelRegistryEvent event) {
        Class<?>[] classesToProcess = {
                FCItemsBlockModels.class,
                FCItems.class
        };
        // 获取 ModItemsBlockModels 类中的所有静态字段
        for (Class<?> clazz : classesToProcess) {
            for (Field field : clazz.getDeclaredFields()) {
                try {
                    // 检查字段是否是 Item 类型
                    if (Item.class.isAssignableFrom(field.getType())) {
                        // 获取字段的值
                        Item item = (Item) field.get(null);
                        if (item != null) {
                            // 注册模型
                            ModelLoader.setCustomModelResourceLocation(
                                    item,
                                    0,
                                    new ModelResourceLocation(item.getRegistryName(), "inventory")
                            );
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}